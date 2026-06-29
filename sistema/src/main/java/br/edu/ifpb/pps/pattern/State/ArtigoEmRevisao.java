package br.edu.ifpb.pps.pattern.State;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public class ArtigoEmRevisao implements EstadoArtigo{
      public void distribuirParaRevisao(Artigo artigo) {
            throw new IllegalStateException("Artigo ja foi distribuido");

    }

    public void finalizar(Artigo artigo, ResultadoDecisao resultado) {
        artigo.setResultadoDecisao(resultado);
        artigo.setEstado(new ArtigoFinalizado());

    }

    public String getNome() {
        return "EM_REVISÃO";
    }
}
