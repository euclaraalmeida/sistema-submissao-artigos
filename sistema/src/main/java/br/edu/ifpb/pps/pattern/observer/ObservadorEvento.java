package br.edu.ifpb.pps.pattern.observer;

public interface ObservadorEvento {
    boolean aceita(EventoSistema evento);

    void atualizar(EventoSistema evento);
}
