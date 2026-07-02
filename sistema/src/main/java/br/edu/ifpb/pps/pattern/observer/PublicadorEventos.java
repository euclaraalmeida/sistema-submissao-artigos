package br.edu.ifpb.pps.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class PublicadorEventos {
    private final List<ObservadorEvento> observadores = new ArrayList<>();

    public void adicionarObservador(ObservadorEvento observador) {
        if (observador == null) {
            throw new IllegalArgumentException("Observador e obrigatorio.");
        }

        observadores.add(observador);
    }

    public void publicar(EventoSistema evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento do sistema e obrigatorio.");
        }

        for (ObservadorEvento observador : observadores) {
            if (observador.aceita(evento)) {
                observador.atualizar(evento);
            }
        }
    }
}
