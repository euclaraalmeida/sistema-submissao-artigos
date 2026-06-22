package br.edu.ifpb.pps.domain.model;

import br.edu.ifpb.pps.domain.enums.StatusArtigo;

import java.util.List;

public class Artigo {

    private int id;
    private String titulo;
    private String resumo;
    private Evento evento;
    private CategoriaSubmissao categoria;
    private List<AreaTematica> areasTematicas;
    private List<Autoria> autorias;
    private StatusArtigo status;

    public Artigo(int id, String titulo, String resumo, Evento evento,
                  CategoriaSubmissao categoria,
                  List<AreaTematica> areasTematicas,
                  List<Autoria> autorias) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.evento = evento;
        this.categoria = categoria;
        this.areasTematicas = areasTematicas;
        this.autorias = autorias;
        this.status = StatusArtigo.SUBMETIDO;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public Evento getEvento() {
        return evento;
    }

    public CategoriaSubmissao getCategoria() {
        return categoria;
    }

    public List<AreaTematica> getAreasTematicas() {
        return areasTematicas;
    }

    public List<Autoria> getAutorias() {
        return autorias;
    }

    public StatusArtigo getStatus() {
        return status;
    }

    public void definirStatus(StatusArtigo status) {
        this.status = status;
    }
}