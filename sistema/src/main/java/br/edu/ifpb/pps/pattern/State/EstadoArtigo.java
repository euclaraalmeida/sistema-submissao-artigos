package br.edu.ifpb.pps.pattern.State;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public interface EstadoArtigo {
    void distribuirParaRevisao(Artigo artigo);

    void finalizar(Artigo artigo, ResultadoDecisao resultado);
    
    String getNome();
        
}
