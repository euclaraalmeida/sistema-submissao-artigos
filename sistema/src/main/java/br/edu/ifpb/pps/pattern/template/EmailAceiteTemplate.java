package br.edu.ifpb.pps.pattern.template;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public class EmailAceiteTemplate extends EmailResultadoTemplate {

    @Override
    protected String montarMensagemPrincipal(
            Artigo artigo,
            ResultadoDecisao resultado
    ) {
        String evento = artigo.getEvento() == null ? "evento" : artigo.getEvento().getNome();
        String categoria = artigo.getEvento() == null ? "categoria" : artigo.getEvento().getCategoriaSubmissao().name();

        return "Parabens! Sua submissao de n "
                + artigo.getId()
                + ", intitulada \""
                + artigo.getTitulo()
                + "\", para o "
                + evento
                + " - "
                + categoria
                + ", foi aceita.\n\n"
                + "As avaliacoes estao disponiveis ao final do e-mail.";
    }
}
