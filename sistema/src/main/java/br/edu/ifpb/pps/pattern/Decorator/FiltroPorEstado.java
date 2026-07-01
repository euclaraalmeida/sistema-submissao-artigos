package br.edu.ifpb.pps.pattern.Decorator;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;

public class FiltroPorEstado extends FiltroArtigoDecorator {
    private final String nomeEstado;

    public FiltroPorEstado(BuscaArtigos busca, String nomeEstado) {
        super(busca);
        this.nomeEstado = nomeEstado;
    }

    @Override
    public List<Artigo> buscar() {
        List<Artigo> artigos = super.buscar();
        List<Artigo> filtrados = new ArrayList<>();

        for (Artigo artigo : artigos) {
            if (artigo.getEstado().getNome().equalsIgnoreCase(nomeEstado)) {
                filtrados.add(artigo);
            }
        }

        return filtrados;
    }
}