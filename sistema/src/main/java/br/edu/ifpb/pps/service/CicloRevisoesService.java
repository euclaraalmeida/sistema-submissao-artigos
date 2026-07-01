package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.StatusRevisao;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.pattern.observer.CicloRevisoesConcluidoEvent;
import br.edu.ifpb.pps.pattern.observer.PublicadorEventos;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;

public class CicloRevisoesService {
    private final ArtigoRepository artigoRepository;
    private final RevisaoRepository revisaoRepository;
    private final PublicadorEventos publicadorEventos;
    private final ValidacaoGenericaService validacaoService;

    public CicloRevisoesService(
            ArtigoRepository artigoRepository,
            RevisaoRepository revisaoRepository,
            PublicadorEventos publicadorEventos,
            ValidacaoGenericaService validacaoService
    ) {
        this.artigoRepository = artigoRepository;
        this.revisaoRepository = revisaoRepository;
        this.publicadorEventos = publicadorEventos;
        this.validacaoService = validacaoService;
    }

    public boolean publicarSeCicloConcluido(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento");

        if (evento.isEmailsResultadoAutoresEnviados()) {
            return false;
        }

        List<Artigo> artigos = artigoRepository.listarPorEvento(evento);

        if (artigos.isEmpty() || !todosArtigosFinalizados(artigos)) {
            return false;
        }

        if (!todasRevisoesConcluidas(evento)) {
            return false;
        }

        publicadorEventos.publicar(new CicloRevisoesConcluidoEvent(evento, artigos));
        evento.setEmailsResultadoAutoresEnviados(true);

        return true;
    }

    private boolean todosArtigosFinalizados(List<Artigo> artigos) {
        for (Artigo artigo : artigos) {
            if (artigo.getResultadoDecisao() == null) {
                return false;
            }
        }

        return true;
    }

    private boolean todasRevisoesConcluidas(Evento evento) {
        List<AtribuicaoRevisao> atribuicoes = revisaoRepository.listarPorEvento(evento);

        if (atribuicoes.isEmpty()) {
            return false;
        }

        for (AtribuicaoRevisao atribuicao : atribuicoes) {
            if (atribuicao.getStatus() != StatusRevisao.CONCLUIDA
                    || atribuicao.getParecer() == null) {
                return false;
            }
        }

        return true;
    }
}
