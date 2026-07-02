package br.edu.ifpb.pps.pattern.template;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;

public class EmailRejeicaoTemplate extends EmailResultadoTemplate {

    @Override
    protected String montarMensagemPrincipal(
            Artigo artigo,
            ResultadoDecisao resultado
    ) {
        String evento = artigo.getEvento() == null ? "evento" : artigo.getEvento().getNome();
        String categoria = artigo.getEvento() == null ? "categoria" : artigo.getEvento().getCategoriaSubmissao().name();

        return "Lamentamos informar que seu artigo de n "
                + artigo.getId()
                + " intitulado \""
                + artigo.getTitulo()
                + "\" nao pode ser aceito para o "
                + evento
                + " - "
                + categoria
                + ".\n\n"
                + "Ao final do e-mail, seguem os pareceres dos revisores, "
                + "que esperamos que possam auxilia-lo(a) em futuras submissoes.\n\n"
                + "Agradecemos sua submissao.";
    }
}
