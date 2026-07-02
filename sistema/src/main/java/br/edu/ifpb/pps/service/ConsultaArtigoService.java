package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.pattern.Decorator.BuscaArtigos;
import br.edu.ifpb.pps.pattern.Decorator.BuscaTodosArtigos;
import br.edu.ifpb.pps.pattern.Decorator.FiltroPorArea;
import br.edu.ifpb.pps.pattern.Decorator.FiltroPorAvaliador;
import br.edu.ifpb.pps.pattern.Decorator.FiltroPorEstado;
import br.edu.ifpb.pps.pattern.Decorator.FiltroPorResultado;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;
import br.edu.ifpb.pps.service.dto.FiltroConsultaArtigo;

public class ConsultaArtigoService {
    private final ArtigoRepository artigoRepository;
    private final RevisaoRepository revisaoRepository;
    private final ValidacaoGenericaService validacaoService;
    private final ComiteService comiteService;

    public ConsultaArtigoService(
            ArtigoRepository artigoRepository,
            RevisaoRepository revisaoRepository,
            ValidacaoGenericaService validacaoService,
            ComiteService comiteService
    ) {
        this.artigoRepository = artigoRepository;
        this.revisaoRepository = revisaoRepository;
        this.validacaoService = validacaoService;
         this.comiteService = comiteService;
    }

    public List<Artigo> consultar(Evento evento, FiltroConsultaArtigo filtro) {
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.objetoObrigatorio(filtro, "Filtro de consulta");

        BuscaArtigos busca = new BuscaTodosArtigos(artigoRepository, evento);

        if (filtro.temArea()) {
            busca = new FiltroPorArea(busca, filtro.getArea());
        }

        if (filtro.temEstado()) {
            busca = new FiltroPorEstado(busca, filtro.getEstado());
        }

        if (filtro.temResultado()) {
            busca = new FiltroPorResultado(busca, filtro.getResultado());
        }

        if (filtro.temAvaliador()) {
            busca = new FiltroPorAvaliador(
                    busca,
                    filtro.getAvaliador(),
                    revisaoRepository
            );
        }

        return busca.buscar();
    }

    public List<MembroComite> listarAvaliadores(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento");
        return comiteService.listarMembros(evento);
}
}