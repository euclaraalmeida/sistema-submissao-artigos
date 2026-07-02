package br.edu.ifpb.pps.pattern.observer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.ifpb.pps.domain.model.Autoria;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.repository.RevisaoRepository;
import br.edu.ifpb.pps.service.EmailResultadoService;
import br.edu.ifpb.pps.service.EmailService;

public class EmailAutorObserver implements ObservadorEvento {
    private final EmailResultadoService emailResultadoService;
    private final EmailService emailService;
    private final RevisaoRepository revisaoRepository;

    public EmailAutorObserver(
            EmailResultadoService emailResultadoService,
            EmailService emailService,
            RevisaoRepository revisaoRepository
    ) {
        this.emailResultadoService = emailResultadoService;
        this.emailService = emailService;
        this.revisaoRepository = revisaoRepository;
    }

    @Override
    public boolean aceita(EventoSistema evento) {
        return evento instanceof CicloRevisoesConcluidoEvent;
    }

    @Override
    public void atualizar(EventoSistema evento) {
        CicloRevisoesConcluidoEvent cicloConcluido = (CicloRevisoesConcluidoEvent) evento;

        for (Artigo artigo : cicloConcluido.getArtigosFinalizados()) {
            enviarResultadoParaAutores(artigo);
        }
    }

    private void enviarResultadoParaAutores(Artigo artigo) {
        List<Parecer> pareceres = revisaoRepository.listarPareceresPorArtigo(artigo);
        String corpo = emailResultadoService.gerarEmailResultado(
                artigo,
                artigo.getResultadoDecisao(),
                pareceres
        );

        Set<String> destinatarios = emailsDosAutores(artigo);

        if (destinatarios.isEmpty()) {
            throw new IllegalStateException("Artigo precisa ter autores com e-mail.");
        }

        for (String destinatario : destinatarios) {
            emailService.enviar(
                    destinatario,
                    "Resultado do artigo: " + artigo.getTitulo(),
                    corpo
            );
        }
    }

    private Set<String> emailsDosAutores(Artigo artigo) {
        Set<String> emails = new HashSet<>();

        for (Autoria autoria : artigo.getAutores()) {
            Usuario usuario = autoria.getUser();

            if (usuario != null
                    && usuario.getEmail() != null
                    && !usuario.getEmail().trim().isEmpty()) {
                emails.add(usuario.getEmail());
            }
        }

        return emails;
    }
}
