package br.edu.ifpb.pps.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MembroComite {
    private Long id;
    private Usuario usuario;
    private List<AreaTematica> especialidades = new ArrayList<>();

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

    public List<AreaTematica> getEspecialidades() {
        return new ArrayList<>(especialidades);
    }

    public void adicionarEspecialidade(AreaTematica areaTematica) {
        if (areaTematica == null) {
            throw new IllegalArgumentException("Area tematica e obrigatoria.");
        }

        especialidades.add(areaTematica);
    }
}