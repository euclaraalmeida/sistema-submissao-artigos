package br.edu.ifpb.pps.domain.model;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.enums.TipoAutoria;
import br.edu.ifpb.pps.pattern.State.ArtigoSubmetido;
import br.edu.ifpb.pps.pattern.State.EstadoArtigo;

public class Artigo {
    private long id;
    private List<Autoria> autores = new ArrayList<>();
    private String titulo;
    private List<AreaTematica> areasTematicas = new ArrayList<>();
    private String resumo;
    private Evento evento;
    private ResultadoDecisao resultadoDecisao;
    private EstadoArtigo estado = new ArtigoSubmetido();

    public void distribuirParaRevisao() {
        estado.distribuirParaRevisao(this);
    }

    public void finalizar(ResultadoDecisao resultado) {
        estado.finalizar(this, resultado);
    }

    public void adicionarAutor(Autoria autoria) {
        if (autoria == null) {
            throw new IllegalArgumentException("Autoria e obrigatoria.");
        }
        autores.add(autoria);
    }

    
    public void adicionarAreaTematica(AreaTematica areaTematica) {
        if (areaTematica == null) {
            throw new IllegalArgumentException("Area tematica e obrigatoria.");
        }
        areasTematicas.add(areaTematica);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Autoria> getAutores() {
        return new ArrayList<>(autores);
    }

    public Usuario getAutorPrincipal() {
        for (Autoria autoria : autores) {
            if (autoria.getTipoAutoria() == TipoAutoria.AUTOR_PRINCIPAL) {
                return autoria.getUser();
            }
        }

        return null;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<AreaTematica> getAreasTematicas() {
        return new ArrayList<>(areasTematicas);
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ResultadoDecisao getResultadoDecisao() {
        return resultadoDecisao;
    }

    public void setResultadoDecisao(ResultadoDecisao resultadoDecisao) {
        this.resultadoDecisao = resultadoDecisao;
    }

    public EstadoArtigo getEstado() {
        return estado;
    }

    public void setEstado(EstadoArtigo estado) {
        this.estado = estado;
    }
}
