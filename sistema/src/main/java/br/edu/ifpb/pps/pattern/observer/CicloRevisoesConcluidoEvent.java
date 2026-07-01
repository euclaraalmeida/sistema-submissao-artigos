package br.edu.ifpb.pps.pattern.observer;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Evento;

public class CicloRevisoesConcluidoEvent implements EventoSistema {
    private final Evento evento;
    private final List<Artigo> artigosFinalizados;

    public CicloRevisoesConcluidoEvent(Evento evento, List<Artigo> artigosFinalizados) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento e obrigatorio.");
        }

        this.evento = evento;
        this.artigosFinalizados = new ArrayList<>(artigosFinalizados);
    }

    public Evento getEvento() {
        return evento;
    }

    public List<Artigo> getArtigosFinalizados() {
        return new ArrayList<>(artigosFinalizados);
    }
}
