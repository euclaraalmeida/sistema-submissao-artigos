package br.edu.ifpb.pps.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.StatusRevisao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.pattern.chain.ValidadorDistribuicao;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;

public class DistribuicaoArtigosService {
    private static final int QUANTIDADE_REVISORES_POR_ARTIGO = 2;

    private final ArtigoRepository artigoRepository;
    private final RevisaoRepository revisaoRepository;
    private final ValidacaoGenericaService validacaoService;
    private final ValidadorDistribuicao validadorDistribuicao;

    public DistribuicaoArtigosService(
            ArtigoRepository artigoRepository,
            RevisaoRepository revisaoRepository,
            ValidacaoGenericaService validacaoService,
            ValidadorDistribuicao validadorDistribuicao
    ) {
        this.artigoRepository = artigoRepository;
        this.revisaoRepository = revisaoRepository;
        this.validacaoService = validacaoService;
        this.validadorDistribuicao = validadorDistribuicao;
    }

    public List<AtribuicaoRevisao> distribuir(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento");

        List<Artigo> artigos = artigoRepository.listarPorEvento(evento);
        List<MembroComite> revisores = evento.getMembrosComite();
        List<AtribuicaoRevisao> atribuicoesExistentes = revisaoRepository.listarPorEvento(evento);
        List<AtribuicaoRevisao> atribuicoesCriadas = new ArrayList<>();

        for (Artigo artigo : artigos) {
            List<MembroComite> revisoresSelecionados = selecionarRevisores(
                    artigo,
                    revisores,
                    atribuicoesExistentes
            );

            for (MembroComite revisor : revisoresSelecionados) {
                AtribuicaoRevisao atribuicao = criarAtribuicao(artigo, revisor);

                revisaoRepository.salvar(atribuicao);
                atribuicoesExistentes.add(atribuicao);
                atribuicoesCriadas.add(atribuicao);
            }

            if (!revisoresSelecionados.isEmpty()
                    && "SUBMETIDO".equals(artigo.getEstado().getNome())) {
                artigo.distribuirParaRevisao();
            }
        }

        return atribuicoesCriadas;
    }

    private List<MembroComite> selecionarRevisores(
            Artigo artigo,
            List<MembroComite> revisores,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        int quantidadeJaAtribuida = contarAtribuicoesDoArtigo(artigo, atribuicoesExistentes);
        int quantidadeNecessaria = QUANTIDADE_REVISORES_POR_ARTIGO - quantidadeJaAtribuida;

        if (quantidadeNecessaria <= 0) {
            return new ArrayList<>();
        }

        List<MembroComite> candidatos = ordenarPorAfinidadeECarga(
                artigo,
                revisores,
                atribuicoesExistentes
        );

        List<MembroComite> selecionados = new ArrayList<>();

        for (MembroComite revisor : candidatos) {
            if (selecionados.size() == quantidadeNecessaria) {
                break;
            }

            if (podeReceberArtigo(artigo, revisor, atribuicoesExistentes)) {
                selecionados.add(revisor);
            }
        }

        if (selecionados.size() < quantidadeNecessaria) {
            throw new IllegalStateException("Nao ha revisores suficientes para distribuir o artigo.");
        }

        return selecionados;
    }

    private List<MembroComite> ordenarPorAfinidadeECarga(
            Artigo artigo,
            List<MembroComite> revisores,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        List<MembroComite> candidatos = new ArrayList<>(revisores);

        candidatos.sort(
                Comparator
                        .comparing((MembroComite revisor) -> possuiAfinidade(artigo, revisor))
                        .reversed()
                        .thenComparing(revisor -> contarAtribuicoesDoRevisor(revisor, atribuicoesExistentes))
                        .thenComparing(revisor -> revisor.getId() == null ? Long.MAX_VALUE : revisor.getId())
        );

        return candidatos;
    }

    private boolean podeReceberArtigo(
            Artigo artigo,
            MembroComite revisor,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        return validadorDistribuicao.validar(
                artigo,
                revisor,
                atribuicoesExistentes
        );
    }

    private AtribuicaoRevisao criarAtribuicao(Artigo artigo, MembroComite revisor) {
        AtribuicaoRevisao atribuicao = new AtribuicaoRevisao();
        atribuicao.setArtigo(artigo);
        atribuicao.setRevisor(revisor);
        atribuicao.setStatus(StatusRevisao.PENDENTE);

        return atribuicao;
    }

    private boolean possuiAfinidade(Artigo artigo, MembroComite revisor) {
        for (AreaTematica areaArtigo : artigo.getAreasTematicas()) {
            for (AreaTematica especialidade : revisor.getEspecialidades()) {
                if (areaArtigo == especialidade) {
                    return true;
                }
            }
        }

        return false;
    }

    private int contarAtribuicoesDoArtigo(
            Artigo artigo,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        int total = 0;

        for (AtribuicaoRevisao atribuicao : atribuicoesExistentes) {
            if (atribuicao.getArtigo() == artigo) {
                total++;
            }
        }

        return total;
    }

    private int contarAtribuicoesDoRevisor(
            MembroComite revisor,
            List<AtribuicaoRevisao> atribuicoesExistentes
    ) {
        int total = 0;

        for (AtribuicaoRevisao atribuicao : atribuicoesExistentes) {
            if (atribuicao.getRevisor() == revisor) {
                total++;
            }
        }

        return total;
    }

}
