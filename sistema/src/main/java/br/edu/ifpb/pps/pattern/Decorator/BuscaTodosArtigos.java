package br.edu.ifpb.pps.pattern.Decorator;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.repository.ArtigoRepository;

// componente concreto
public class BuscaTodosArtigos implements BuscaArtigos {
    private final ArtigoRepository artigoRepository;
    private final Evento evento;

    public BuscaTodosArtigos(ArtigoRepository artigoRepository, Evento evento) {
        this.artigoRepository = artigoRepository;
        this.evento = evento;
    }

    @Override
    public List<Artigo> buscar() {
        return artigoRepository.listarPorEvento(evento);
    }
    
}
