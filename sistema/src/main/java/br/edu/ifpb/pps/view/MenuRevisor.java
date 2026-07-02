package br.edu.ifpb.pps.view;

import java.util.List;

import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.enums.Veredito;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.RevisaoService;

public class MenuRevisor {
    private final ConsoleUI ui;
    private final RevisaoService revisaoService;

    public MenuRevisor(ConsoleUI ui, RevisaoService revisaoService) {
        this.ui = ui;
        this.revisaoService = revisaoService;
    }

    public void iniciar(Usuario usuario, Evento evento) {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Area do Revisor/Membro do Comite");
            ui.mostrarMensagem("1 - Listar revisoes pendentes");
            ui.mostrarMensagem("2 - Registrar parecer");
            ui.mostrarMensagem("0 - Voltar");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        listarRevisoesPendentes(usuario, evento);
                        break;
                    case "2":
                        registrarParecer(usuario, evento);
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

    private void listarRevisoesPendentes(Usuario usuario, Evento evento) {
        ui.mostrarTitulo("Revisoes Pendentes");

        List<AtribuicaoRevisao> revisoes = revisaoService.listarRevisoesPendentes(usuario, evento);

        if (revisoes.isEmpty()) {
            ui.mostrarMensagem("Voce nao possui revisoes pendentes neste evento.");
            ui.pausar();
            return;
        }

        for (int i = 0; i < revisoes.size(); i++) {
            mostrarResumoRevisao(i + 1, revisoes.get(i));
        }

        ui.pausar();
    }

    private void registrarParecer(Usuario usuario, Evento evento) {
        ui.mostrarTitulo("Registrar Parecer");

        List<AtribuicaoRevisao> revisoes = revisaoService.listarRevisoesPendentes(usuario, evento);

        if (revisoes.isEmpty()) {
            ui.mostrarMensagem("Voce nao possui revisoes pendentes neste evento.");
            ui.pausar();
            return;
        }

        for (int i = 0; i < revisoes.size(); i++) {
            mostrarResumoRevisao(i + 1, revisoes.get(i));
        }

        int opcao = ui.lerInteiro("Escolha a revisao");

        if (opcao < 1 || opcao > revisoes.size()) {
            ui.mostrarErro("Revisao invalida.");
            ui.pausar();
            return;
        }

        AtribuicaoRevisao revisao = revisoes.get(opcao - 1);
        String pontosPositivos = ui.lerTextoObrigatorio("Pontos positivos");
        String pontosNegativos = ui.lerTextoObrigatorio("Pontos negativos");
        Veredito veredito = escolherVeredito();

        Parecer parecer = revisaoService.registrarParecer(
                usuario,
                revisao,
                pontosPositivos,
                pontosNegativos,
                veredito
        );

        ui.mostrarMensagem("Parecer registrado com sucesso.");
        ui.mostrarMensagem("Veredito: " + parecer.getVeredito());
        ui.pausar();
    }

    private Veredito escolherVeredito() {
        while (true) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Veredito:");
            ui.mostrarMensagem("1 - REJEITADO");
            ui.mostrarMensagem("2 - FRACAMENTE_REJEITADO");
            ui.mostrarMensagem("3 - FRACAMENTE_ACEITO");
            ui.mostrarMensagem("4 - ACEITO");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    return Veredito.REJEITADO;
                case "2":
                    return Veredito.FRACAMENTE_REJEITADO;
                case "3":
                    return Veredito.FRACAMENTE_ACEITO;
                case "4":
                    return Veredito.ACEITO;
                default:
                    ui.mostrarErro("Opcao invalida.");
            }
        }
    }

    private void mostrarResumoRevisao(int numero, AtribuicaoRevisao revisao) {
        Artigo artigo = revisao.getArtigo();

        if (artigo == null) {
            ui.mostrarMensagem(numero + " - Revisao sem artigo vinculado");
            return;
        }

        ui.mostrarMensagem(
                numero + " - Artigo #" + artigo.getId()
                        + " | " + artigo.getTitulo()
                        + " | Areas: " + formatarAreas(artigo)
        );
    }

    private String formatarAreas(Artigo artigo) {
        StringBuilder texto = new StringBuilder();
        List<AreaTematica> areas = artigo.getAreasTematicas();

        for (int i = 0; i < areas.size(); i++) {
            if (i > 0) {
                texto.append(", ");
            }

            texto.append(areas.get(i).getNome());
        }

        if (texto.length() == 0) {
            return "sem area";
        }

        return texto.toString();
    }
}
