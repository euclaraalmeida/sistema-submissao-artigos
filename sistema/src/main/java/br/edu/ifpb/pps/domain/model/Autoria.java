package br.edu.ifpb.pps.domain.model;

public class Autoria {

    private Pesquisador pesquisador;
    private Artigo artigo;
    private TipoAutoria tipo;

    public Autoria(Pesquisador pesquisador, Artigo artigo, TipoAutoria tipo) {
        this.pesquisador = pesquisador;
        this.artigo = artigo;
        this.tipo = tipo;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public Artigo getArtigo() {
        return artigo;
    }

    public TipoAutoria getTipo() {
        return tipo;
    }
}