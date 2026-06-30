package br.edu.ifpb.pps.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.DistribuicaoArtigosService;
import br.edu.ifpb.pps.service.EventoAtual;
import br.edu.ifpb.pps.service.EventoService;
import br.edu.ifpb.pps.service.ComiteService;
import br.edu.ifpb.pps.service.UsuarioService;

/*
2 - Cadastrar área temática 
4 - Distribuir artigos 
5 - Processar/consultar 
6 - dashboard
*/





public class MenuCoordenacao {
    private final ConsoleUI ui;
    private final EventoService eventoService;
    private final EventoAtual eventoAtual;
    private final UsuarioService usuarioService;
    private final ComiteService comiteService;
    private final DistribuicaoArtigosService distribuicaoArtigosService;

    public MenuCoordenacao(
            ConsoleUI ui,
            EventoService eventoService,
            EventoAtual eventoAtual,
            UsuarioService usuarioService,
            ComiteService comiteService,
            DistribuicaoArtigosService distribuicaoArtigosService
    ) {
        this.ui = ui;
        this.eventoService = eventoService;
        this.eventoAtual = eventoAtual;
        this.usuarioService = usuarioService;
        this.comiteService = comiteService;
        this.distribuicaoArtigosService = distribuicaoArtigosService;
    }

    public void iniciar(Usuario usuario, Evento evento) {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Area da Coordenacao");
            ui.mostrarMensagem("1 - Iniciar novo evento");
            ui.mostrarMensagem("2 - Cadastrar area tematica");
            ui.mostrarMensagem("3 - Registrar membro do comite");
            ui.mostrarMensagem("4 - Distribuir artigos");
            ui.mostrarMensagem("5 - Listar membros do comite");
            ui.mostrarMensagem("0 - Voltar");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        iniciarNovoEvento();
                        evento = eventoAtual.getEventoAtual();
                        break;
                    case "2":
                        //cadastrarAreaTematica(evento);
                        break;
                    case "3":
                        registrarMembroComite(evento);
                        break;
                    case "4":
                        //distribuirArtigos(evento);
                        break;
                    case "5":
                        listarMembros(evento);
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

    private void iniciarNovoEvento() {
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
    }

   
    private void registrarMembroComite(Evento evento) {
        ui.mostrarTitulo("Registrar Membro do Comite");

        String email = ui.lerTextoObrigatorio("Email do usuario");
        Usuario usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            ui.mostrarErro("Usuario nao encontrado.");
            ui.pausar();
            return;
        }
        // selecionar o nivel de conhecimento para cada especialidade
        //List<AreaTematica> especialidades = selecionarNivel(evento);

        MembroComite membro = comiteService.registrarMembro(
                usuario,
                evento
                // antes
        );

        ui.mostrarMensagem("Membro registrado com sucesso: " + membro.getUsuario().getNome());
        ui.pausar();
    }

    

    private void listarMembros(Evento evento) {
    
        ui.mostrarTitulo("Membros do Comite");

        List<MembroComite> membros = comiteService.listarMembros(evento);

        if (membros.isEmpty()) {
            ui.mostrarMensagem("Nenhum membro cadastrado.");
            ui.pausar();
            return;
        }

        for (int i = 0; i < membros.size(); i++) {
            MembroComite membro = membros.get(i);
            ui.mostrarMensagem((i + 1) + " - " + membro.getUsuario().getNome());
        }

        ui.pausar();
    }


    private CategoriaSubmissao escolherCategoria() {
        while (true) {
            ui.mostrarMensagem("");
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