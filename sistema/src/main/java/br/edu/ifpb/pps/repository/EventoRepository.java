package br.edu.ifpb.pps.repository;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Evento;

public class EventoRepository {
    private final BancoDeDados banco;

    public EventoRepository(BancoDeDados banco) {
        this.banco = banco;
    }

    public void salvar(Evento evento) {
        banco.getEventos().add(evento);
    }

    public List<Evento> listarTodos() {
        return banco.getEventos();
    }

    public Evento buscarUltimo() {
        List<Evento> eventos = banco.getEventos();

        if (eventos.isEmpty()) {
            return null;
        }

        return eventos.get(eventos.size() - 1);
    }

    public void limpar() {
        banco.getEventos().clear();
    }
}