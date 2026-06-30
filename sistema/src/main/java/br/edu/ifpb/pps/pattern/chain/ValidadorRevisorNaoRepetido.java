package br.edu.ifpb.pps.pattern.chain;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.MembroComite;

public class ValidadorRevisorNaoRepetido extends ValidadorDistribuicao {

    @Override
    protected boolean validarRegra(
            Artigo artigo,
            MembroComite revisor,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        for (AtribuicaoRevisao atribuicao : atribuicoesExistentes) {
            if (atribuicao.getArtigo() == artigo
                    && atribuicao.getRevisor() == revisor) {
                return false;
            }
        }

        return true;
    }
}
