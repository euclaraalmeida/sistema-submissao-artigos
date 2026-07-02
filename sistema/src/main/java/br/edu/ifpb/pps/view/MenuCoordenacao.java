package br.edu.ifpb.pps.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.ConhecimentoAreaRevisor;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.ComiteService;
import br.edu.ifpb.pps.service.DashboardService;
import br.edu.ifpb.pps.service.DistribuicaoArtigosService;
import br.edu.ifpb.pps.service.EventoAtual;
import br.edu.ifpb.pps.service.EventoService;
import br.edu.ifpb.pps.service.UsuarioService;
import br.edu.ifpb.pps.service.dto.DashboardCoordenacao;
import br.edu.ifpb.pps.service.dto.PendenciaAvaliacao;

public class MenuCoordenacao {
    private final ConsoleUI ui;
    private final EventoService eventoService;
    private final EventoAtual eventoAtual;
    private final UsuarioService usuarioService;
    private final ComiteService comiteService;
    private final DistribuicaoArtigosService distribuicaoArtigosService;
    private final DashboardService dashboardService;
    private final MenuConsultaArtigo menuConsultaArtigo;
    public MenuCoordenacao(
            ConsoleUI ui,
            EventoService eventoService,
            EventoAtual eventoAtual,
            UsuarioService usuarioService,
            ComiteService comiteService,
            DistribuicaoArtigosService distribuicaoArtigosService,
            DashboardService dashboardService,
            MenuConsultaArtigo menuConsultaArtigo
    ) {
        this.ui = ui;
        this.eventoService = eventoService;
        this.eventoAtual = eventoAtual;
        this.usuarioService = usuarioService;
        this.comiteService = comiteService;
        this.distribuicaoArtigosService = distribuicaoArtigosService;
        this.dashboardService = dashboardService;
        this.menuConsultaArtigo = menuConsultaArtigo;
    }

    public void iniciar(Usuario usuario, Evento evento) {
        boolean rodando = true;

        while (rodando) {
            ui.mostrarTitulo("Area da Coordenacao");
            ui.mostrarMensagem("1 - Encerrar evento atual e iniciar novo evento");
            ui.mostrarMensagem("2 - Cadastrar area tematica");
            ui.mostrarMensagem("3 - Registrar membro do comite");
            ui.mostrarMensagem("4 - Distribuir artigos");
            ui.mostrarMensagem("5 - Listar membros do comite");
            ui.mostrarMensagem("6 - Dashboard");
            ui.mostrarMensagem("7 - Consultas");

            ui.mostrarMensagem("0 - Voltar");

            String opcao = ui.lerTexto("Escolha");

            try {
                switch (opcao) {
                    case "1":
                        iniciarNovoEvento();
                        evento = eventoAtual.getEventoAtual();
                        break;
                    case "2":
                        cadastrarAreaTematica(evento);
                        break;
                    case "3":
                        registrarMembroComConhecimentos(evento) ;                       
                        break;
                    case "4":
                        distribuirArtigos(evento);
                        break;
                    case "5":
                        listarMembros(evento);
                        break;
                    case "6":
                        visualizarDashboard(evento);
                        break;
                    case "7":
                        menuConsultaArtigo.iniciar(evento);
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
        ui.mostrarTitulo("Encerrar evento atual e iniciar novo evento");

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

   
   private void registrarMembroComConhecimentos(Evento evento) {
        ui.mostrarTitulo("Registrar Membro do Comite");

        String email = ui.lerTextoObrigatorio("Email do usuario");
        Usuario usuarioEncontrado = usuarioService.buscarPorEmail(email);

        if (usuarioEncontrado == null) {
            ui.mostrarErro("Usuario nao encontrado.");
            ui.pausar();
            return;
        }

        List<ConhecimentoAreaRevisor> conhecimentos = informarConhecimentosPorArea(evento);

        MembroComite membro = comiteService.registrarMembroComConhecimentos(
                usuarioEncontrado,
                evento,
                conhecimentos
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


    private List<ConhecimentoAreaRevisor> informarConhecimentosPorArea(Evento evento) {
        List<AreaTematica> areas = evento.getAreasTematicas();

        if (areas.isEmpty()) {
            throw new IllegalStateException("Nenhuma area tematica cadastrada para o evento.");
        }

        List<ConhecimentoAreaRevisor> conhecimentos = new ArrayList<>();

        for (AreaTematica area : evento.getAreasTematicas()) {
            int nivel = escolherNivelConhecimento(area);
            conhecimentos.add(new ConhecimentoAreaRevisor(area, nivel));
        }

        return conhecimentos;
    }

        private void cadastrarAreaTematica(Evento evento) {
        ui.mostrarTitulo("Cadastrar Area Tematica");

        String nome = ui.lerTextoObrigatorio("Nome da area tematica");

        AreaTematica area = eventoService.cadastrarAreaTematica(evento, nome);

        ui.mostrarMensagem("Area tematica cadastrada com sucesso: " + area.getNome());
        ui.pausar();
    }
    private int escolherNivelConhecimento(AreaTematica area) {
        while (true) {
                ui.mostrarMensagem("Area tematica: " + area.getNome());
                ui.mostrarMensagem("Nivel de conhecimento:");
                ui.mostrarMensagem("1 - Basico");
                ui.mostrarMensagem("2 - Intermediario");
                ui.mostrarMensagem("3 - Avançado");

                int nivel = ui.lerInteiro("Escolha o nivel");

                if (nivel >= 1 && nivel <= 3) {
                    return nivel;
                }

                ui.mostrarErro("Nivel invalido. Escolha entre 1 e 3.");
            }
        }

    private void visualizarDashboard(Evento evento) {
            ui.mostrarTitulo("Dashboard da Coordenacao");

            DashboardCoordenacao dashboard = dashboardService.gerarDashboard(evento);

            ui.mostrarMensagem("Artigos submetidos: " + dashboard.getTotalArtigosSubmetidos());
            ui.mostrarMensagem("Revisores: " + dashboard.getTotalRevisores());
            ui.mostrarMensagem("Artigos avaliados: " + dashboard.getTotalArtigosAvaliados());
            ui.mostrarMensagem("Artigos pendentes: " + dashboard.getTotalArtigosPendentes());

            ui.mostrarMensagem("");
            ui.mostrarMensagem("Pendencias:");

            if (dashboard.getPendencias().isEmpty()) {
                ui.mostrarMensagem("Nenhuma pendencia de avaliacao.");
            } else {
                for (PendenciaAvaliacao pendencia : dashboard.getPendencias()) {
                    ui.mostrarMensagem(
                            "- " + pendencia.getTituloArtigo()
                                    + " | Avaliador: " + pendencia.getNomeAvaliador()
                                    + " | Email: " + pendencia.getEmailAvaliador()
                    );
                }
            }

            ui.pausar();
        }



private void distribuirArtigos(Evento evento) {
    ui.mostrarTitulo("Distribuir Artigos");

    List<AtribuicaoRevisao> atribuicoes = distribuicaoArtigosService.distribuir(evento);

    if (atribuicoes.isEmpty()) {
        ui.mostrarMensagem("Nenhuma nova atribuicao foi criada.");
        ui.pausar();
        return;
    }

    ui.mostrarMensagem("Distribuicao realizada com sucesso.");
    ui.mostrarMensagem("Atribuicoes criadas: " + atribuicoes.size());

    for (AtribuicaoRevisao atribuicao : atribuicoes) {
        ui.mostrarMensagem(
                "- Artigo: " + atribuicao.getArtigo().getTitulo()
                        + " | Revisor: " + atribuicao.getRevisor().getUsuario().getNome()
                        + " | Status: " + atribuicao.getStatus()
        );
    }

    ui.pausar();
}



        }

