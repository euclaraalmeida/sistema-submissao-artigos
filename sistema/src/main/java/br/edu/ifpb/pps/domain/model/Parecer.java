package br.edu.ifpb.pps.domain.model;

import java.time.LocalDateTime;

import br.edu.ifpb.pps.domain.enums.Veredito;

public class Parecer {
    private AtribuicaoRevisao atribuicaoRevisao;
    private String pontosPositivos;
    private String pontosNegativos;
    private Veredito veredito;

    public AtribuicaoRevisao getAtribuicaoRevisao() {
        return atribuicaoRevisao;
    }

    public void setAtribuicaoRevisao(AtribuicaoRevisao atribuicaoRevisao) {
        this.atribuicaoRevisao = atribuicaoRevisao;
    }

    public String getPontosPositivos() {
        return pontosPositivos;
    }

    public void setPontosPositivos(String pontosPositivos) {
        this.pontosPositivos = pontosPositivos;
    }

    public String getPontosNegativos() {
        return pontosNegativos;
    }

    public void setPontosNegativos(String pontosNegativos) {
        this.pontosNegativos = pontosNegativos;
    }

    public Veredito getVeredito() {
        return veredito;
    }

    public void setVeredito(Veredito veredito) {
        this.veredito = veredito;
    }
}
