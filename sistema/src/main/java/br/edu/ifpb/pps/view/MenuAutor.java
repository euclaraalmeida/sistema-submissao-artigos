package br.edu.ifpb.pps.view;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.SubmissaoService;

public class MenuAutor {
    private final ConsoleUI ui;
    private final SubmissaoService submissaoService;

    public MenuAutor(ConsoleUI ui, SubmissaoService submissaoService) {
        this.ui = ui;
        this.submissaoService = submissaoService;
    }

    public void iniciar(Usuario usuario, Evento evento) {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Area do Autor/Pesquisador");
            ui.mostrarMensagem("1 - Submeter artigo");
            ui.mostrarMensagem("2 - Listar meus artigos");
            ui.mostrarMensagem("0 - Voltar");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        submeterArtigo(usuario, evento);
                        break;
                    case "2":
                        listarMeusArtigos(usuario, evento);
                        break;
                    case "0":
                        rodando = false;
                        break;
                    default:
                        ui.mostrarErro("Opcao invalida.");
                        ui.pausar();
                }
            } catch (Exception e) {
                ui.mostrarErro(e.getMessage());
                ui.pausar();
            }
        }
    }

    private void submeterArtigo(Usuario usuario, Evento evento) {
        ui.mostrarTitulo("Submissao de Artigo");

        String titulo = ui.lerTextoObrigatorio("Titulo");
        String resumo = ui.lerTextoObrigatorio("Resumo");

        List<AreaTematica> areasSelecionadas = escolherAreasTematicas(evento);

        Artigo artigo = submissaoService.submeterArtigo(
                usuario,
                evento,
                titulo,
                resumo,
                areasSelecionadas
        );

        ui.mostrarMensagem("Artigo submetido com sucesso: " + artigo.getTitulo());
        ui.pausar();
    }

    private void listarMeusArtigos(Usuario usuario, Evento evento) {
        ui.mostrarTitulo("Meus Artigos");

        List<Artigo> artigos = submissaoService.listarArtigosDoAutor(usuario, evento);

        if (artigos.isEmpty()) {
            ui.mostrarMensagem("Voce ainda nao submeteu artigos neste evento.");
            ui.pausar();
            return;
        }

        for (int i = 0; i < artigos.size(); i++) {
            Artigo artigo = artigos.get(i);

            ui.mostrarMensagem(
                    (i + 1) + " - " + artigo.getTitulo()
                            + " | Estado: " + artigo.getEstado().getNome()
                            + " | Resultado: " + artigo.getResultadoDecisao()
            );
        }

        ui.pausar();
    }

    private List<AreaTematica> escolherAreasTematicas(Evento evento) {
        List<AreaTematica> areas = evento.getAreasTematicas();

        if (areas.isEmpty()) {
            throw new IllegalStateException("Nenhuma area tematica cadastrada para o evento.");
        }

        List<AreaTematica> selecionadas = new ArrayList<>();

        boolean escolhendo = true;

        while (escolhendo) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Areas tematicas disponiveis:");

            for (int i = 0; i < areas.size(); i++) {
                ui.mostrarMensagem((i + 1) + " - " + areas.get(i).getNome());
            }

            ui.mostrarMensagem("0 - Finalizar escolha");

            int opcao = ui.lerInteiro("Escolha uma area");

            if (opcao == 0) {
                escolhendo = false;
            } else if (opcao < 0 || opcao > areas.size()) {
                ui.mostrarErro("Area invalida.");
            } else {
                AreaTematica area = areas.get(opcao - 1);

                if (!selecionadas.contains(area)) {
                    selecionadas.add(area);
                    ui.mostrarMensagem("Area adicionada: " + area.getNome());
                } else {
                    ui.mostrarErro("Area ja selecionada.");
                }
            }
        }

        if (selecionadas.isEmpty()) {
            throw new IllegalArgumentException("Selecione pelo menos uma area tematica.");
        }

        return selecionadas;
    }
}