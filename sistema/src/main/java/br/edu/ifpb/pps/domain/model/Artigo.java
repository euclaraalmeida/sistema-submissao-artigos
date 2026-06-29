package br.edu.ifpb.pps.domain.model;

import java.util.List;
import java.util.Set;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;
import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.pattern.State.EstadoArtigo;
import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.pattern.State.ArtigoSubmetido;
import br.edu.ifpb.pps.pattern.State.ArtigoEmRevisao;
import br.edu.ifpb.pps.pattern.State.ArtigoFinalizado;

public class Artigo {
    private long id;
    private List<Autoria> autores;
    private String titulo;
    private List<AreaTematica> areaTematica;
    private String resumo;
    private Evento evento; 
    private ResultadoDecisao resultadoDecisao;
    private EstadoArtigo estado = new ArtigoSubmetido(); // interface
    // e acho que o evento é necessario aqui pois como saberemos questao do artigo ter prazo paar ser submetido
    
    public void distribuirParaRevisao() {
        estado.distribuirParaRevisao(this);
    }

    public void finalizar(ResultadoDecisao resultado) {
        estado.finalizar(this, resultado);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Autoria> getAutores() {
        return autores;
    }

    public void setAutores(List<Autoria> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<AreaTematica> getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(List<AreaTematica> areaTematica) {
        this.areaTematica = areaTematica;
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
