package br.edu.ifpb.pps.view;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.service.ConsultaArtigoService;
import br.edu.ifpb.pps.service.dto.FiltroConsultaArtigo;

public class MenuConsultaArtigo {
    private final ConsoleUI ui;
    private final ConsultaArtigoService consultaArtigoService;

    public MenuConsultaArtigo(ConsoleUI ui, ConsultaArtigoService consultaArtigoService ) {
        this.ui = ui;
        this.consultaArtigoService = consultaArtigoService;
    }

    public void iniciar(Evento evento) {
        ui.mostrarTitulo("Consulta Avancada de Artigos");

        FiltroConsultaArtigo filtro = new FiltroConsultaArtigo();
        boolean escolhendo = true;

        while (escolhendo) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Filtros:");
            ui.mostrarMensagem("1 - Filtrar por area tematica");
            ui.mostrarMensagem("2 - Filtrar por estado");
            ui.mostrarMensagem("3 - Filtrar por resultado");
            ui.mostrarMensagem("4 - Filtrar por avaliador");

            ui.mostrarMensagem("0 - Buscar");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    filtro.setArea(escolherUmaAreaTematica(evento));
                    break;
                case "2":
                    filtro.setEstado(escolherEstadoArtigo());
                    break;
                case "3":
                    filtro.setResultado(escolherResultadoDecisao());
                    break;
                case "4":
                    filtro.setAvaliador(escolherAvaliador(evento));
                break;
                case "0":
                    escolhendo = false;
                    break;
                default:
                    ui.mostrarErro("Opcao invalida.");
            }
        }

        List<Artigo> artigos = consultaArtigoService.consultar(evento, filtro);
        exibirResultado(artigos,filtro);

        ui.pausar();
    }

    private AreaTematica escolherUmaAreaTematica(Evento evento) {
        List<AreaTematica> areas = evento.getAreasTematicas();

        if (areas.isEmpty()) {
            throw new IllegalStateException("Nenhuma area tematica cadastrada para o evento.");
        }

        while (true) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Areas tematicas:");

            for (int i = 0; i < areas.size(); i++) {
                ui.mostrarMensagem((i + 1) + " - " + areas.get(i).getNome());
            }

            int opcao = ui.lerInteiro("Escolha uma area");

            if (opcao >= 1 && opcao <= areas.size()) {
                return areas.get(opcao - 1);
            }

            ui.mostrarErro("Area invalida.");
        }
    }

    private String escolherEstadoArtigo() {
        while (true) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Estado do artigo:");
            ui.mostrarMensagem("1 - Submetido");
            ui.mostrarMensagem("2 - Em revisao");
            ui.mostrarMensagem("3 - Finalizado");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    return "SUBMETIDO";
                case "2":
                    return "EM_REVISÃO";
                case "3":
                    return "FINALIZADO";
                default:
                    ui.mostrarErro("Opcao invalida.");
            }
        }
    }

    private ResultadoDecisao escolherResultadoDecisao() {
        while (true) {
            ui.mostrarMensagem("");
            ui.mostrarMensagem("Resultado:");
            ui.mostrarMensagem("1 - ACEITO");
            ui.mostrarMensagem("2 - REJEITADO");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    return ResultadoDecisao.ACEITO;
                case "2":
                    return ResultadoDecisao.REJEITADO;
                default:
                    ui.mostrarErro("Opcao invalida.");
            }
        }
    }

   private void exibirResultado(List<Artigo> artigos, FiltroConsultaArtigo filtro) {
    ui.mostrarMensagem("");
    ui.mostrarMensagem("Resultado da consulta:");

    if (artigos.isEmpty()) {
        ui.mostrarMensagem("Nenhum artigo encontrado com os filtros aplicados.");
        return;
    }

    for (Artigo artigo : artigos) {
        StringBuilder linha = new StringBuilder();

        linha.append("Titulo: ").append(artigo.getTitulo());

        if (filtro.temArea()) {
            linha.append(" | Area: ").append(formatarAreas(artigo));
        }

        if (filtro.temEstado()) {
            linha.append(" | Estado: ").append(artigo.getEstado().getNome());
        }

        if (filtro.temResultado()) {
            linha.append(" | Resultado: ").append(formatarResultado(artigo));
        }

        if (filtro.temAvaliador()) {
            linha.append(" | Avaliador: ")
                    .append(filtro.getAvaliador().getUsuario().getNome());
        }

        ui.mostrarMensagem(linha.toString());
    }
}

   private MembroComite escolherAvaliador(Evento evento) {
    List<MembroComite> avaliadores = consultaArtigoService.listarAvaliadores(evento);

    if (avaliadores.isEmpty()) {
        throw new IllegalStateException("Nenhum avaliador cadastrado no comite.");
    }

    while (true) {
        ui.mostrarMensagem("");
        ui.mostrarMensagem("Avaliadores:");

        for (int i = 0; i < avaliadores.size(); i++) {
            MembroComite avaliador = avaliadores.get(i);

            ui.mostrarMensagem(
                    (i + 1) + " - " + avaliador.getUsuario().getNome()
                            + " | " + avaliador.getUsuario().getEmail()
            );
        }

        int opcao = ui.lerInteiro("Escolha um avaliador");

        if (opcao >= 1 && opcao <= avaliadores.size()) {
            return avaliadores.get(opcao - 1);
        }

        ui.mostrarErro("Avaliador invalido.");
    }
}
private String formatarResultado(Artigo artigo) {
    if (artigo.getResultadoDecisao() == null) {
        return "Sem resultado final";
    }

    return artigo.getResultadoDecisao().toString();
}
private String formatarAreas(Artigo artigo) {
    List<AreaTematica> areas = artigo.getAreasTematicas();

    if (areas.isEmpty()) {
        return "sem area";
    }

    StringBuilder texto = new StringBuilder();

    for (int i = 0; i < areas.size(); i++) {
        if (i > 0) {
            texto.append(", ");
        }

        texto.append(areas.get(i).getNome());
    }

    return texto.toString();
}
}
