package br.edu.ifpb.pps.pattern.Decorator;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public class FiltroPorResultado extends FiltroArtigoDecorator {
    private final ResultadoDecisao resultado;

    public FiltroPorResultado(BuscaArtigos busca, ResultadoDecisao resultado) {
        super(busca);
        this.resultado = resultado;
    }

    @Override
    public List<Artigo> buscar() {
        List<Artigo> artigos = super.buscar();
        List<Artigo> filtrados = new ArrayList<>();

        for (Artigo artigo : artigos) {
            if (artigo.getResultadoDecisao() == resultado) {
                filtrados.add(artigo);
            }
        }

        return filtrados;
    }
}