package br.edu.ifpb.pps.pattern.Decorator;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;

// classe decoradora abstrata
public class FiltroArtigoDecorator implements BuscaArtigos {
    private BuscaArtigos busca;

    public FiltroArtigoDecorator(BuscaArtigos busca) {
        this.busca = busca;
    }

    @Override
    public List<Artigo> buscar() {
        return busca.buscar();
    }
}
