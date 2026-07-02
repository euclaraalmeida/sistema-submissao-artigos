package br.edu.ifpb.pps.pattern.Decorator;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.repository.RevisaoRepository;

public class FiltroPorAvaliador extends FiltroArtigoDecorator {
    private final MembroComite avaliador;
    private final RevisaoRepository revisaoRepository;

    public FiltroPorAvaliador(
            BuscaArtigos busca,
            MembroComite avaliador,
            RevisaoRepository revisaoRepository
    ) {
        super(busca);
        this.avaliador = avaliador;
        this.revisaoRepository = revisaoRepository;
    }

    @Override
    public List<Artigo> buscar() {
        List<Artigo> artigos = super.buscar();
        List<Artigo> filtrados = new ArrayList<>();

        for (Artigo artigo : artigos) {
            if (artigoFoiAtribuidoAoAvaliador(artigo)) {
                filtrados.add(artigo);
            }
        }

        return filtrados;
    }

    private boolean artigoFoiAtribuidoAoAvaliador(Artigo artigo) {
        List<AtribuicaoRevisao> atribuicoes = revisaoRepository.listarPorArtigo(artigo);

        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (atribuicao.getRevisor() == avaliador) {
                return true;
            }
        }

        return false;
    }
}