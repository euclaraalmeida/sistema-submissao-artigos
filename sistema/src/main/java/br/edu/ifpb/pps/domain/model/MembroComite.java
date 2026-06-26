package br.edu.ifpb.pps.domain.model;

import java.util.List;

public class MembroComite {
    private Long id;
    private Usuario usuario;
    private Evento evento;
    private List<AreaTematica> especialidades;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<AreaTematica> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<AreaTematica> especialidades) {
        this.especialidades = especialidades;
    }
}
