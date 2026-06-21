package br.edu.ifpb.pps.domain.model;

import java.util.List;

public class MembroComite {

    public MembroComite(Pesquisador pesquisador, Evento evento, List<AreaTematica> especialidades) {
        this.pesquisador = pesquisador;
        this.evento = evento;
        this.especialidades = especialidades;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public Evento getEvento() {
        return evento;
    }

    public List<AreaTematica> getEspecialidades() {
        return especialidades;
    }

}
