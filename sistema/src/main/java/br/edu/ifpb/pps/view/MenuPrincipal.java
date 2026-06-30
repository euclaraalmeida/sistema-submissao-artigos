package br.edu.ifpb.pps.view;

import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.ComiteService;

public class MenuPrincipal {
    private final ConsoleUI ui;
    private final ComiteService comiteService;
    private final MenuAutor menuAutor;
    private final MenuRevisor menuRevisor;
    private final MenuCoordenacao menuCoordenacao;

    public MenuPrincipal(
            ConsoleUI ui,
            ComiteService comiteService,
            MenuAutor menuAutor,
            MenuRevisor menuRevisor,
            MenuCoordenacao menuCoordenacao
    ) {
        this.ui = ui;
        this.comiteService = comiteService;
        this.menuAutor = menuAutor;
        this.menuRevisor = menuRevisor;
        this.menuCoordenacao = menuCoordenacao;
    }

    public void iniciar(Usuario usuario, Evento evento) {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Menu Principal");
            ui.mostrarMensagem("Usuario: " + usuario.getNome());
            ui.mostrarMensagem("Evento: " + evento.getNome());
            ui.mostrarMensagem("");
            ui.mostrarMensagem("1 - Area do Autor/Pesquisador");

            if (comiteService.ehMembro(usuario, evento)) {
                ui.mostrarMensagem("2 - Area do Revisor/Membro do Comite");
            }

            if (usuario.isCoordenador()) {
                ui.mostrarMensagem("3 - Area da Coordenacao");
            }

            ui.mostrarMensagem("0 - Logout");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        menuAutor.iniciar(usuario, evento);
                        break;

                    case "2":
                        if (comiteService.ehMembro(usuario, evento)) {
                            menuRevisor.iniciar(usuario, evento);
                        } else {
                            ui.mostrarErro("Acesso permitido apenas para membros do comite.");
                            ui.pausar();
                        }
                        break;

                    case "3":
                        if (usuario.isCoordenador()) {
                            menuCoordenacao.iniciar(usuario, evento);
                        } else {
                            ui.mostrarErro("Acesso permitido apenas para coordenadores.");
                            ui.pausar();
                        }
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
}