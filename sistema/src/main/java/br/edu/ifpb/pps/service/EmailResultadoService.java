package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.pattern.template.EmailAceiteTemplate;
import br.edu.ifpb.pps.pattern.template.EmailRejeicaoTemplate;
import br.edu.ifpb.pps.pattern.template.EmailResultadoTemplate;

public class EmailResultadoService {

    public String gerarEmailResultado(
            Artigo artigo,
            ResultadoDecisao resultado,
            List<Parecer> pareceres
    ) {
        EmailResultadoTemplate template = escolherTemplate(resultado);
        return template.gerar(artigo, resultado, pareceres);
    }

    private EmailResultadoTemplate escolherTemplate(ResultadoDecisao resultado) {
        if (resultado == ResultadoDecisao.ACEITO) {
            return new EmailAceiteTemplate();
        }

        return new EmailRejeicaoTemplate();
    }
}
