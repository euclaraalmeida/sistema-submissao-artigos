package br.edu.ifpb.pps.service.dto;

public class PendenciaAvaliacao {
    private String tituloArtigo;
    private String nomeAvaliador;
    private String emailAvaliador;

    public PendenciaAvaliacao(String tituloArtigo, String nomeAvaliador, String emailAvaliador) {
        this.tituloArtigo = tituloArtigo;
        this.nomeAvaliador = nomeAvaliador;
        this.emailAvaliador = emailAvaliador;
    }

    public String getTituloArtigo() {
        return tituloArtigo;
    }

    public String getNomeAvaliador() {
        return nomeAvaliador;
    }

    public String getEmailAvaliador() {
        return emailAvaliador;
    }
}