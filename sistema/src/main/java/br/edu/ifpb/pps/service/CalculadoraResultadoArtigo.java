package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.enums.Veredito;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Parecer;

public class CalculadoraResultadoArtigo {

    public ResultadoDecisao calcular(Artigo artigo, List<AtribuicaoRevisao> atribuicoes) {
        int soma = 0;

        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (!pertenceAoArtigo(atribuicao, artigo)) {
                continue;
            }

            Parecer parecer = atribuicao.getParecer();

            if (parecer == null || parecer.getVeredito() == null) {
                continue;
            }

            soma += pesoDoVeredito(parecer.getVeredito());
        }

        if (soma > 0) {
            return ResultadoDecisao.ACEITO;
        }

        return ResultadoDecisao.REJEITADO;
    }

    private boolean pertenceAoArtigo(AtribuicaoRevisao atribuicao, Artigo artigo) {
        return atribuicao.getArtigo() != null && atribuicao.getArtigo().equals(artigo);
    }

    private int pesoDoVeredito(Veredito veredito) {
        switch (veredito) {
            case REJEITADO:
                return -2;
            case FRACAMENTE_REJEITADO:
                return -1;
            case FRACAMENTE_ACEITO:
                return 1;
            case ACEITO:
                return 2;
            default:
                throw new IllegalArgumentException("Veredito desconhecido: " + veredito);
        }
    }
}
