package br.edu.ifpb.pps.view;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Autoria;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.SubmissaoService;
import br.edu.ifpb.pps.service.UsuarioService;

public class MenuAutor {
    private final ConsoleUI ui;
    private final SubmissaoService submissaoService;
    private final UsuarioService usuarioService;

    public MenuAutor(ConsoleUI ui, SubmissaoService submissaoService, UsuarioService usuarioService) {
        this.ui = ui;
        this.submissaoService = submissaoService;
        this.usuarioService = usuarioService;
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
        List<Usuario> coautores = escolherCoautores(usuario);

        Artigo artigo = submissaoService.submeterArtigo(
                usuario,
                evento,
                titulo,
                resumo,
                areasSelecionadas,
                coautores
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
                            + " | Autores: " + formatarAutores(artigo)
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

    private List<Usuario> escolherCoautores(Usuario autorPrincipal) {
        List<Usuario> coautores = new ArrayList<>();

        boolean escolhendo = true;

        while (escolhendo) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Coautores:");
            ui.mostrarMensagem("Informe o e-mail de um usuario cadastrado ou deixe em branco para finalizar.");

            String email = ui.lerTexto("Email do coautor");

            if (email == null || email.trim().isEmpty()) {
                escolhendo = false;
                continue;
            }

            Usuario coautor;

            try {
                coautor = usuarioService.buscarPorEmail(email);
            } catch (IllegalArgumentException e) {
                ui.mostrarErro(e.getMessage());
                continue;
            }

            if (coautor == null) {
                ui.mostrarErro("Usuario nao encontrado.");
            } else if (coautor == autorPrincipal || coautores.contains(coautor)) {
                ui.mostrarErro("Coautor ja informado para este artigo.");
            } else {
                coautores.add(coautor);
                ui.mostrarMensagem("Coautor adicionado: " + coautor.getNome());
            }
        }

        return coautores;
    }

    private String formatarAutores(Artigo artigo) {
        StringBuilder texto = new StringBuilder();
        List<Autoria> autores = artigo.getAutores();

        for (int i = 0; i < autores.size(); i++) {
            Usuario usuario = autores.get(i).getUser();

            if (i > 0) {
                texto.append(", ");
            }

            texto.append(usuario == null ? "sem usuario" : usuario.getNome());
        }

        if (texto.length() == 0) {
            return "sem autores";
        }

        return texto.toString();
    }
}
