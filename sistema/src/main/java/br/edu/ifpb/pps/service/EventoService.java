package br.edu.ifpb.pps.service;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.EventoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;

public class EventoService {

    private final EventoRepository eventoRepository;
    private final ArtigoRepository artigoRepository;
    private final RevisaoRepository revisaoRepository;
    private final EventoAtual eventoAtual;
    private final ValidacaoGenericaService validacaoService;

    public EventoService(
            EventoRepository eventoRepository,
            ArtigoRepository artigoRepository,
            RevisaoRepository revisaoRepository,
            EventoAtual eventoAtual,
            ValidacaoGenericaService validacaoService
    ) {
        this.eventoRepository = eventoRepository;
        this.artigoRepository = artigoRepository;
        this.revisaoRepository = revisaoRepository;
        this.eventoAtual = eventoAtual;
        this.validacaoService = validacaoService;
    }

    public Evento iniciarNovoEvento(
            String nome,
            String cidade,
            java.time.LocalDate dataInicio,
            java.time.LocalDate dataFim,
            CategoriaSubmissao categoriaSubmissao
    ) {
        validacaoService.textoObrigatorio(nome, "Nome do evento");
        validacaoService.textoObrigatorio(cidade, "Cidade");
        validacaoService.objetoObrigatorio(dataInicio, "Data de inicio");
        validacaoService.objetoObrigatorio(dataFim, "Data de fim");
        validacaoService.objetoObrigatorio(categoriaSubmissao, "Categoria de submissao");

        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("Data de fim nao pode ser anterior a data de inicio.");
        }

        limparDadosDoEventoAnterior();

        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setCidade(cidade);
        evento.setDataInicio(dataInicio);
        evento.setDataFim(dataFim);
        evento.setCategoriaSubmissao(categoriaSubmissao);

        eventoRepository.salvar(evento);
        eventoAtual.definirEventoAtual(evento);

        return evento;
    }

    private void limparDadosDoEventoAnterior() {
        eventoRepository.limpar();
        artigoRepository.limpar();
        revisaoRepository.limpar();
        eventoAtual.limparEventoAtual();
    }


     public AreaTematica cadastrarAreaTematica(Evento evento, String nome) {
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.textoObrigatorio(nome, "Nome da area tematica");

        AreaTematica area = new AreaTematica();
        area.setNome(nome);

        evento.adicionarAreaTematica(area);

        return area;
}

}