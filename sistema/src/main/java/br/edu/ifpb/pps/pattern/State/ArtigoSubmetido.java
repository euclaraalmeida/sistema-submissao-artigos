package br.edu.ifpb.pps.pattern.State;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public class ArtigoSubmetido implements EstadoArtigo{
      public void distribuirParaRevisao(Artigo artigo) {
            artigo.setEstado(new ArtigoEmRevisao());
    }

    public void finalizar(Artigo artigo, ResultadoDecisao resultado) {
        throw new IllegalStateException("Artigo não pode ser finalizado sem revisao");
    }

    public String getNome() {
        return "SUBMETIDO";
    }
}
