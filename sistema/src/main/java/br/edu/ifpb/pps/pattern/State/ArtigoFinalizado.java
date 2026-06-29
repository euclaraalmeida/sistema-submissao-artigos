package br.edu.ifpb.pps.pattern.State;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public class ArtigoFinalizado implements EstadoArtigo {
    
    public void distribuirParaRevisao(Artigo artigo) {
        throw new IllegalStateException("Artigo finalizado não pode voltar para revisão.");
    }

    public void finalizar(Artigo artigo, ResultadoDecisao resultado) {
        throw new IllegalStateException("Artigo já está finalizado.");
    }

    public String getNome() {
        return "FINALIZADO";
    }
}
