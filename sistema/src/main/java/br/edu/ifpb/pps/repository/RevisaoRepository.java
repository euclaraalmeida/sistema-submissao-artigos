package br.edu.ifpb.pps.repository;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.StatusRevisao;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;

public class RevisaoRepository {
    private final BancoDeDados banco;

    public RevisaoRepository(BancoDeDados banco) {
        this.banco = banco;
    }

    public void salvar(AtribuicaoRevisao atribuicao) {
        banco.getAtribuicoes().add(atribuicao);
    }

    public List<AtribuicaoRevisao> listarTodos() {
        return banco.getAtribuicoes();
    }

    public List<AtribuicaoRevisao> listarPorEvento(Evento evento) {
        List<AtribuicaoRevisao> resultado = new ArrayList<>();

        for (AtribuicaoRevisao atribuicao : banco.getAtribuicoes()) {
            if (atribuicao.getArtigo() != null
                    && atribuicao.getArtigo().getEvento() == evento) {
                resultado.add(atribuicao);
            }
        }

        return resultado;
    }

    public List<AtribuicaoRevisao> listarPendentesPorRevisor(Usuario usuario, Evento evento) {
        List<AtribuicaoRevisao> resultado = new ArrayList<>();

        for (AtribuicaoRevisao atribuicao : banco.getAtribuicoes()) {
            if (atribuicao.getStatus() != StatusRevisao.PENDENTE) {
                continue;
            }

            if (atribuicao.getArtigo() == null
                    || atribuicao.getArtigo().getEvento() != evento) {
                continue;
            }

            if (atribuicao.getRevisor() != null
                    && atribuicao.getRevisor().getUsuario() == usuario) {
                resultado.add(atribuicao);
            }
        }

        return resultado;
    }

    public void limpar() {
        banco.getAtribuicoes().clear();
    }
}