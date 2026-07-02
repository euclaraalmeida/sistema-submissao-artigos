package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.StatusRevisao;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;
import br.edu.ifpb.pps.service.dto.DashboardCoordenacao;
import br.edu.ifpb.pps.service.dto.PendenciaAvaliacao;
 /*
    Deve prover informações sobre 
    o número de artigos submetidos
     o número de revisores
     o número de artigos avaliados
     o número de artigos que ainda estão pendentes de avaliação 
     relação dos artigos pendentes e qual avaliador está responsável por terminar cada um deles.

    */

    
public class DashboardService {
    private final ArtigoRepository artigoRepository;
    private final RevisaoRepository revisaoRepository;
    private final ComiteService comiteService;
    private final ValidacaoGenericaService validacaoService;

    public DashboardService(
            ArtigoRepository artigoRepository,
            RevisaoRepository revisaoRepository,
            ComiteService comiteService,
            ValidacaoGenericaService validacaoService
    ) {
        this.artigoRepository = artigoRepository;
        this.revisaoRepository = revisaoRepository;
        this.comiteService = comiteService;
        this.validacaoService = validacaoService;
    }

    public DashboardCoordenacao gerarDashboard(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento");

        List<Artigo> artigos = artigoRepository.listarPorEvento(evento);
        List<AtribuicaoRevisao> atribuicoes = revisaoRepository.listarPorEvento(evento);
        List<MembroComite> revisores = comiteService.listarMembros(evento);

        DashboardCoordenacao dashboard = new DashboardCoordenacao();

        dashboard.setTotalArtigosSubmetidos(artigos.size());
        dashboard.setTotalRevisores(revisores.size());
        dashboard.setTotalArtigosAvaliados(contarArtigosAvaliados(artigos, atribuicoes));
        dashboard.setTotalArtigosPendentes(contarArtigosPendentes(artigos, atribuicoes));

        adicionarPendencias(dashboard, atribuicoes);

        return dashboard;
    }

    private int contarArtigosAvaliados(List<Artigo> artigos, List<AtribuicaoRevisao> atribuicoes) {
        int total = 0;

        for (Artigo artigo : artigos) {
            if (todasRevisoesConcluidas(artigo, atribuicoes)) {
                total++;
            }
        }

        return total;
    }

    private int contarArtigosPendentes(List<Artigo> artigos, List<AtribuicaoRevisao> atribuicoes) {
        int total = 0;

        for (Artigo artigo : artigos) {
            if (possuiRevisaoPendente(artigo, atribuicoes)) {
                total++;
            }
        }

        return total;
    }

    private boolean todasRevisoesConcluidas(Artigo artigo, List<AtribuicaoRevisao> atribuicoes) {
        boolean possuiRevisao = false;

        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (atribuicao.getArtigo() != artigo) {
                continue;
            }

            possuiRevisao = true;

            if (atribuicao.getStatus() != StatusRevisao.CONCLUIDA) {
                return false;
            }
        }

        return possuiRevisao;
    }

    private boolean possuiRevisaoPendente(Artigo artigo, List<AtribuicaoRevisao> atribuicoes) {
        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (atribuicao.getArtigo() == artigo
                    && atribuicao.getStatus() == StatusRevisao.PENDENTE) {
                return true;
            }
        }

        return false;
    }

    private void adicionarPendencias(
            DashboardCoordenacao dashboard,
            List<AtribuicaoRevisao> atribuicoes
    ) {
        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (atribuicao.getStatus() == StatusRevisao.PENDENTE) {
                dashboard.adicionarPendencia(new PendenciaAvaliacao(
                        atribuicao.getArtigo().getTitulo(),
                        atribuicao.getRevisor().getUsuario().getNome(),
                        atribuicao.getRevisor().getUsuario().getEmail()
                ));
            }
        }
    }
}
