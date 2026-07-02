package br.edu.ifpb.pps.pattern.chain;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Autoria;
import br.edu.ifpb.pps.domain.model.MembroComite;

public class ValidadorNaoAutor extends ValidadorDistribuicao {

    @Override
    protected boolean validarRegra(
            Artigo artigo,
            MembroComite revisor,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        for (Autoria autoria : artigo.getAutores()) {
            if (autoria.getUser() == revisor.getUsuario()) {
                return false;
            }
        }

        return true;
    }
}
