package br.edu.ifpb.pps.service;

import br.edu.ifpb.pps.domain.model.Evento;

public class EventoAtual {
    private Evento eventoAtual;
    // duvida aonde vamos usar isso aqui, mas vamos
    private final ValidacaoGenericaService validacaoService;

    public EventoAtual(ValidacaoGenericaService validacaoService) {
        this.validacaoService = validacaoService;
    }

    public Evento getEventoAtual() {
        if (eventoAtual == null) {
            throw new IllegalStateException("Nenhum evento foi iniciado.");
        }

        return eventoAtual;
    }

    public void definirEventoAtual(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento atual");

        this.eventoAtual = evento;
    }

    public void limparEventoAtual() {
        this.eventoAtual = null;
    }

    public boolean possuiEventoAtual() {
        return eventoAtual != null;
    }
}
