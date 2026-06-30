package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.StatusRevisao;
import br.edu.ifpb.pps.domain.enums.Veredito;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.repository.RevisaoRepository;

public class RevisaoService {
    private final RevisaoRepository revisaoRepository;
    private final ResultadoArtigoService resultadoArtigoService;
    private final ValidacaoGenericaService validacaoService;

    public RevisaoService(
            RevisaoRepository revisaoRepository,
            ResultadoArtigoService resultadoArtigoService,
            ValidacaoGenericaService validacaoService
    ) {
        this.revisaoRepository = revisaoRepository;
        this.resultadoArtigoService = resultadoArtigoService;
        this.validacaoService = validacaoService;
    }

    public List<AtribuicaoRevisao> listarRevisoesPendentes(Usuario usuario, Evento evento) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");

        return revisaoRepository.listarPendentesPorRevisor(usuario, evento);
    }

    public Parecer registrarParecer(
            Usuario usuario,
            AtribuicaoRevisao atribuicao,
            String pontosPositivos,
            String pontosNegativos,
            Veredito veredito
    ) {
        validarRegistroParecer(usuario, atribuicao, pontosPositivos, pontosNegativos, veredito);

        Parecer parecer = new Parecer();
        parecer.setPontosPositivos(pontosPositivos);
        parecer.setPontosNegativos(pontosNegativos);
        parecer.setVeredito(veredito);

        atribuicao.setParecer(parecer);
        atribuicao.setStatus(StatusRevisao.CONCLUIDA);

        Artigo artigo = atribuicao.getArtigo();
        List<AtribuicaoRevisao> atribuicoesDoEvento = revisaoRepository.listarPorEvento(artigo.getEvento());
        resultadoArtigoService.finalizarSePossivel(artigo, atribuicoesDoEvento);

        return parecer;
    }

    private void validarRegistroParecer(
            Usuario usuario,
            AtribuicaoRevisao atribuicao,
            String pontosPositivos,
            String pontosNegativos,
            Veredito veredito
    ) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(atribuicao, "Revisao");
        validacaoService.textoObrigatorio(pontosPositivos, "Pontos positivos");
        validacaoService.textoObrigatorio(pontosNegativos, "Pontos negativos");
        validacaoService.objetoObrigatorio(veredito, "Veredito");

        if (atribuicao.getStatus() != StatusRevisao.PENDENTE) {
            throw new IllegalStateException("A revisao nao esta pendente.");
        }

        if (atribuicao.getRevisor() == null
                || atribuicao.getRevisor().getUsuario() != usuario) {
            throw new IllegalArgumentException("Esta revisao nao pertence ao usuario informado.");
        }

        if (atribuicao.getArtigo() == null) {
            throw new IllegalStateException("A revisao precisa estar vinculada a um artigo.");
        }
    }
}
