package br.edu.ifpb.pps.pattern.Decorator;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;

public class FiltroPorArea extends FiltroArtigoDecorator {
    private final AreaTematica area;

    public FiltroPorArea(BuscaArtigos busca, AreaTematica area) {
        super(busca);
        this.area = area;
    }

    @Override
    public List<Artigo> buscar() {
        List<Artigo> artigos = super.buscar();
        List<Artigo> filtrados = new ArrayList<>();

        for (Artigo artigo : artigos) {
            if (artigo.getAreasTematicas().contains(area)) {
                filtrados.add(artigo);
            }
        }

        return filtrados;
    }
}