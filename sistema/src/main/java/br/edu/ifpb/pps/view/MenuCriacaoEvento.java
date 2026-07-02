package br.edu.ifpb.pps.view;

import java.time.LocalDate;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.EventoAtual;
import br.edu.ifpb.pps.service.EventoService;

public class MenuCriacaoEvento {
    private final ConsoleUI ui;
    private final EventoService eventoService;
    private final EventoAtual eventoAtual;
    private final MenuPrincipal menuPrincipal;

    public MenuCriacaoEvento(
            ConsoleUI ui,
            EventoService eventoService,
            EventoAtual eventoAtual,
            MenuPrincipal menuPrincipal
    ) {
        this.ui = ui;
        this.eventoService = eventoService;
        this.eventoAtual = eventoAtual;
        this.menuPrincipal = menuPrincipal;
    }

    public void iniciar(Usuario usuario) {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Configuracao Inicial do Evento");
            ui.mostrarMensagem("Nenhum evento ativo.");
            ui.mostrarMensagem("1 - Iniciar novo evento");
            ui.mostrarMensagem("0 - Logout");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        criarEvento(usuario);
                        rodando = false;
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

    private void criarEvento(Usuario usuario) {
        ui.mostrarTitulo("Iniciar Novo Evento");

        String nome = ui.lerTextoObrigatorio("Nome do evento");
        String cidade = ui.lerTextoObrigatorio("Cidade");

        LocalDate dataInicio = ui.lerData("Data de inicio");
        LocalDate dataFim = ui.lerData("Data de fim");

        CategoriaSubmissao categoria = escolherCategoria();

        Evento evento = eventoService.iniciarNovoEvento(
                nome,
                cidade,
                dataInicio,
                dataFim,
                categoria
        );

        ui.mostrarMensagem("Evento iniciado com sucesso: " + evento.getNome());
        ui.pausar();

        Evento eventoAtivo = eventoAtual.getEventoAtual();
        menuPrincipal.iniciar(usuario, eventoAtivo);
    }

    private CategoriaSubmissao escolherCategoria() {
        while (true) {
            ui.mostrarMensagem("Categoria de submissao:");
            ui.mostrarMensagem("1 - FULL_PAPER");
            ui.mostrarMensagem("2 - SHORT_PAPER");
            ui.mostrarMensagem("3 - DEMO");

            String opcao = ui.lerTexto("Escolha");

            switch (opcao) {
                case "1":
                    return CategoriaSubmissao.FULL_PAPER;
                case "2":
                    return CategoriaSubmissao.SHORT_PAPER;
                case "3":
                    return CategoriaSubmissao.DEMO;
                default:
                    ui.mostrarErro("Opcao invalida.");
            }
        }
    }
}