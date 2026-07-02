package br.edu.ifpb.pps.pattern.chain;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.MembroComite;

public abstract class ValidadorDistribuicao {
    private ValidadorDistribuicao proximo;

    public ValidadorDistribuicao ligarCom(ValidadorDistribuicao proximo) {
        this.proximo = proximo;
        return proximo;
    }

    public boolean validar(
            Artigo artigo,
            MembroComite revisor,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        if (!validarRegra(artigo, revisor, atribuicoesExistentes)) {
            return false;
        }

        if (proximo == null) {
            return true;
        }

        return proximo.validar(artigo, revisor, atribuicoesExistentes);
    }

    protected abstract boolean validarRegra(
            Artigo artigo,
            MembroComite revisor,
            List<AtribuicaoRevisao> atribuicoesExistentes
    );
}
