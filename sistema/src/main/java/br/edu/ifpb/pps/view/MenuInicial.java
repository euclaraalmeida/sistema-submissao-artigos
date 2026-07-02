package br.edu.ifpb.pps.view;

import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.AutenticacaoService;
import br.edu.ifpb.pps.service.EventoAtual;
import br.edu.ifpb.pps.service.UsuarioService;

public class MenuInicial {
    private final ConsoleUI ui;
    private final UsuarioService usuarioService;
    private final AutenticacaoService autenticacaoService;
    private final EventoAtual eventoAtual;
    private final MenuPrincipal menuPrincipal;
    private final MenuCriacaoEvento menuCriacaoEvento;

    public MenuInicial(
            ConsoleUI ui,
            UsuarioService usuarioService,
            AutenticacaoService autenticacaoService,
            EventoAtual eventoAtual,
            MenuPrincipal menuPrincipal,
            MenuCriacaoEvento menuCriacaoEvento
    ) {
        this.ui = ui;
        this.usuarioService = usuarioService;
        this.autenticacaoService = autenticacaoService;
        this.eventoAtual = eventoAtual; // pode ser null
        this.menuPrincipal = menuPrincipal;
        this.menuCriacaoEvento = menuCriacaoEvento;
    }

    public void iniciar() {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Sistema de Submissao e Avaliacao de Artigos");
            ui.mostrarMensagem("1 - Login");
            ui.mostrarMensagem("2 - Cadastrar usuario");
            ui.mostrarMensagem("0 - Sair");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        realizarLogin();
                        break;
                    case "2":
                        cadastrarUsuario();
                        break;
                    case "0":
                        rodando = false;
                        break;
                    default:
                        ui.mostrarErro("Opcao invalida.");
                        ui.pausar();
                }
            } catch (Exception e) {
                ui.mostrarErro(e.getMessage());
                ui.pausar();
            }
        }
    }

    private void realizarLogin() {
        ui.mostrarTitulo("Login");

        String email = ui.lerTextoObrigatorio("Email");
        String senha = ui.lerTextoObrigatorio("Senha");

        Usuario usuario = autenticacaoService.login(email, senha);

        if (!eventoAtual.possuiEventoAtual()) {
            tratarSistemaSemEvento(usuario);
            return;
        }

        Evento evento = eventoAtual.getEventoAtual();
        menuPrincipal.iniciar(usuario, evento);
    }

    private void tratarSistemaSemEvento(Usuario usuario) {
        if (!usuario.isCoordenador()) {
            ui.mostrarErro("Nenhum evento ativo. Aguarde a coordenacao iniciar um evento.");
            ui.pausar();
            return;
        }

        menuCriacaoEvento.iniciar(usuario);
    }

    private void cadastrarUsuario() {
        ui.mostrarTitulo("Cadastro de Usuario");

        String nome = ui.lerTextoObrigatorio("Nome");
        String email = ui.lerTextoObrigatorio("Email");
        String senha = ui.lerTextoObrigatorio("Senha");
        String instituicao = ui.lerTextoObrigatorio("Instituicao");

        boolean coordenador = perguntarSeCoordenador();

        Usuario usuario = usuarioService.cadastrarUsuario(
                nome,
                email,
                senha,
                instituicao,
                coordenador
        );

        ui.mostrarMensagem("Usuario cadastrado com sucesso: " + usuario.getNome());
        ui.pausar();
    }

    private boolean perguntarSeCoordenador() {
        while (true) {
            ui.mostrarMensagem("Usuario coordenador?");
            ui.mostrarMensagem("1 - Sim");
            ui.mostrarMensagem("2 - Nao");

            String opcao = ui.lerTexto("Escolha");

            if ("1".equals(opcao)) {
                return true;
            }

            if ("2".equals(opcao)) {
                return false;
            }

            ui.mostrarErro("Opcao invalida.");
        }
    }
}