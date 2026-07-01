package br.edu.ifpb.pps.service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
    private final String host;
    private final String port;
    private final String usuario;
    private final String senha;
    private final String remetente;

    public EmailService() {
        this.host = System.getenv("SMTP_HOST");
        this.port = valorOuPadrao(System.getenv("SMTP_PORT"), "587");
        this.usuario = System.getenv("SMTP_USER");
        this.senha = System.getenv("SMTP_PASSWORD");
        this.remetente = valorOuPadrao(System.getenv("SMTP_FROM"), usuario);
    }

    public void enviar(String destinatario, String assunto, String corpo) {
        validarDestinatario(destinatario);

        if (!smtpConfigurado()) {
            simularEnvio(destinatario, assunto, corpo);
            return;
        }

        try {
            Message mensagem = new MimeMessage(criarSessao());
            mensagem.setFrom(new InternetAddress(remetente));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);

            Transport.send(mensagem);
        } catch (MessagingException e) {
            throw new IllegalStateException("Nao foi possivel enviar o e-mail.", e);
        }
    }

    private void validarDestinatario(String destinatario) {
        try {
            InternetAddress endereco = new InternetAddress(destinatario);
            endereco.validate();
        } catch (MessagingException e) {
            throw new IllegalArgumentException("E-mail do destinatario invalido: " + destinatario, e);
        }
    }

    private Session criarSessao() {
        Properties propriedades = new Properties();
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");
        propriedades.put("mail.smtp.host", host);
        propriedades.put("mail.smtp.port", port);

        return Session.getInstance(propriedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });
    }

    private boolean smtpConfigurado() {
        return !estaEmBranco(host)
                && !estaEmBranco(port)
                && !estaEmBranco(usuario)
                && !estaEmBranco(senha)
                && !estaEmBranco(remetente);
    }

    private void simularEnvio(String destinatario, String assunto, String corpo) {
        System.out.println("=== E-MAIL SIMULADO ===");
        System.out.println("SMTP nao configurado. O e-mail abaixo seria enviado em producao.");
        System.out.println("Destinatario: " + destinatario);
        System.out.println("Assunto: " + assunto);
        System.out.println("Corpo:");
        System.out.println(corpo);
        System.out.println("=======================");
    }

    private boolean estaEmBranco(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private String valorOuPadrao(String valor, String padrao) {
        if (estaEmBranco(valor)) {
            return padrao;
        }

        return valor;
    }
}
