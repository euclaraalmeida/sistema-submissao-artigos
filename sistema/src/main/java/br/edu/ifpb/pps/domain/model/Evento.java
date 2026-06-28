package br.edu.ifpb.pps.domain.model;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;

public class Evento {
    private Long id;
    private String nome;
    private String cidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private CategoriaSubmissao categoriaSubmissao;

    private List<AreaTematica> areasTematicas;
    private List<MembroComite> membrosComite;
    private List<Artigo> artigos; // isso aqui sai

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public CategoriaSubmissao getCategoriaSubmissao() {
        return categoriaSubmissao;
    }

    public void setCategoriaSubmissao(CategoriaSubmissao categoriaSubmissao) {
        this.categoriaSubmissao = categoriaSubmissao;
    }

    public List<AreaTematica> getAreasTematicas() {
        return areasTematicas;
    }

    public void setAreasTematicas(List<AreaTematica> areasTematicas) {
        this.areasTematicas = areasTematicas;
    }

    public List<MembroComite> getMembrosComite() {
        return membrosComite;
    }

    public void setMembrosComite(List<MembroComite> membrosComite) {
        this.membrosComite = membrosComite;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }
}
