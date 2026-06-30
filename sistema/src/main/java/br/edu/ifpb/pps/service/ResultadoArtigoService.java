package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.enums.StatusRevisao;
import br.edu.ifpb.pps.domain.enums.Veredito;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.domain.model.Parecer;

public class ResultadoArtigoService {

    public boolean finalizarSePossivel(Artigo artigo, List<AtribuicaoRevisao> atribuicoes) {
        if (!todasRevisoesDoArtigoConcluidas(artigo, atribuicoes)) {
            return false;
        }

        ResultadoDecisao resultado = calcular(artigo, atribuicoes);
        artigo.finalizar(resultado);

        // Observer: futuramente, quando todos os artigos do evento estiverem finalizados,
        // publicar evento de conclusao do ciclo de revisoes para permitir notificacao em massa aos autores.

        return true;
    }

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

            soma += pesoDoVeredito(parecer.getVeredito())
                    * nivelConhecimentoDoRevisor(artigo, atribuicao.getRevisor());
        }

        if (soma > 0) {
            return ResultadoDecisao.ACEITO;
        }

        return ResultadoDecisao.REJEITADO;
    }

    private boolean pertenceAoArtigo(AtribuicaoRevisao atribuicao, Artigo artigo) {
        return atribuicao.getArtigo() != null && atribuicao.getArtigo().equals(artigo);
    }

    private int nivelConhecimentoDoRevisor(Artigo artigo, MembroComite revisor) {
        if (revisor == null) {
            return 1;
        }

        int nivel = revisor.maiorNivelConhecimentoNasAreas(artigo.getAreasTematicas());

        if (nivel == 0) {
            return 1;
        }

        return nivel;
    }

    private boolean todasRevisoesDoArtigoConcluidas(
            Artigo artigo,
            List<AtribuicaoRevisao> atribuicoes
    ) {
        int totalRevisoesDoArtigo = 0;

        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (!pertenceAoArtigo(atribuicao, artigo)) {
                continue;
            }

            totalRevisoesDoArtigo++;

            if (atribuicao.getStatus() != StatusRevisao.CONCLUIDA
                    || atribuicao.getParecer() == null
                    || atribuicao.getParecer().getVeredito() == null) {
                return false;
            }
        }

        return totalRevisoesDoArtigo > 0;
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
