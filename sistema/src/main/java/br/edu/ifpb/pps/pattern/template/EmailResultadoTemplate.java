package br.edu.ifpb.pps.pattern.template;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.domain.model.Usuario;

public abstract class EmailResultadoTemplate {

    public final String gerar(
            Artigo artigo,
            ResultadoDecisao resultado,
            List<Parecer> pareceres
    ) {
        StringBuilder email = new StringBuilder();

        email.append(montarSaudacao(artigo));
        email.append("\n\n");
        email.append(montarMensagemPrincipal(artigo, resultado));
        email.append("\n\n");
        email.append(montarAssinatura(artigo));
        email.append("\n\n");
        email.append(montarPareceres(pareceres));

        return email.toString();
    }

    protected String montarSaudacao(Artigo artigo) {
        Usuario autorPrincipal = artigo.getAutorPrincipal();

        if (autorPrincipal == null || autorPrincipal.getNome() == null) {
            return "Prezado(a) autor(a):";
        }

        return "Prezado(a) " + autorPrincipal.getNome() + ":";
    }

    protected abstract String montarMensagemPrincipal(
            Artigo artigo,
            ResultadoDecisao resultado
    );

    protected String montarAssinatura(Artigo artigo) {
        String nomeEvento = artigo.getEvento() == null ? "evento" : artigo.getEvento().getNome();

        // O RF09 usa o nome da coordenadora na assinatura.
        // O modelo atual ainda nao possui o coordenador associado diretamente ao evento.
        return "Atenciosamente,\n\nCoordenacao do Comite de Programa do " + nomeEvento;
    }

    protected String montarPareceres(List<Parecer> pareceres) {
        StringBuilder texto = new StringBuilder();
        texto.append("Ao final do e-mail, seguem os pareceres dos revisores.");

        for (int i = 0; i < pareceres.size(); i++) {
            Parecer parecer = pareceres.get(i);

            texto.append("\n\n[Revisor ");
            texto.append(i + 1);
            texto.append("]\n");
            texto.append("Principal contribuicao ou pontos positivos\n");
            texto.append("================================\n");
            texto.append(parecer.getPontosPositivos());
            texto.append("\n\n");
            texto.append("Pontos negativos\n");
            texto.append("================================\n");
            texto.append(parecer.getPontosNegativos());
        }

        return texto.toString();
    }
}
