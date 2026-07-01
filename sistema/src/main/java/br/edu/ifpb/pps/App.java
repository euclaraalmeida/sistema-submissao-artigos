package br.edu.ifpb.pps;

import br.edu.ifpb.pps.pattern.chain.ValidadorDistribuicao;
import br.edu.ifpb.pps.pattern.chain.ValidadorNaoAutor;
import br.edu.ifpb.pps.pattern.chain.ValidadorRevisorNaoRepetido;
import br.edu.ifpb.pps.pattern.observer.EmailAutorObserver;
import br.edu.ifpb.pps.pattern.observer.PublicadorEventos;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.BancoDeDados;
import br.edu.ifpb.pps.repository.EventoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;
import br.edu.ifpb.pps.repository.UsuarioRepository;
import br.edu.ifpb.pps.service.AutenticacaoService;
import br.edu.ifpb.pps.service.CicloRevisoesService;
import br.edu.ifpb.pps.service.ComiteService;
import br.edu.ifpb.pps.service.DistribuicaoArtigosService;
import br.edu.ifpb.pps.service.EmailResultadoService;
import br.edu.ifpb.pps.service.EmailService;
import br.edu.ifpb.pps.service.EventoAtual;
import br.edu.ifpb.pps.service.EventoService;
import br.edu.ifpb.pps.service.ResultadoArtigoService;
import br.edu.ifpb.pps.service.RevisaoService;
import br.edu.ifpb.pps.service.SubmissaoService;
import br.edu.ifpb.pps.service.UsuarioService;
import br.edu.ifpb.pps.service.ValidacaoGenericaService;
import br.edu.ifpb.pps.view.ConsoleUI;
import br.edu.ifpb.pps.view.MenuAutor;
import br.edu.ifpb.pps.view.MenuCoordenacao;
import br.edu.ifpb.pps.view.MenuCriacaoEvento;
import br.edu.ifpb.pps.view.MenuInicial;
import br.edu.ifpb.pps.view.MenuPrincipal;
import br.edu.ifpb.pps.view.MenuRevisor;

public class App {
    public static void main(String[] args) {
        BancoDeDados banco = new BancoDeDados();

        UsuarioRepository usuarioRepository = new UsuarioRepository(banco);
        EventoRepository eventoRepository = new EventoRepository(banco);
        ArtigoRepository artigoRepository = new ArtigoRepository(banco);
        RevisaoRepository revisaoRepository = new RevisaoRepository(banco);

        ValidacaoGenericaService validacaoService = new ValidacaoGenericaService();
        EventoAtual eventoAtual = new EventoAtual(validacaoService);

        UsuarioService usuarioService = new UsuarioService(usuarioRepository, validacaoService);
        AutenticacaoService autenticacaoService = new AutenticacaoService(usuarioRepository, validacaoService);

        EventoService eventoService = new EventoService(
                eventoRepository,
                artigoRepository,
                revisaoRepository,
                eventoAtual,
                validacaoService
        );

        ComiteService comiteService = new ComiteService(validacaoService);
        SubmissaoService submissaoService = new SubmissaoService(artigoRepository, validacaoService);

        ResultadoArtigoService resultadoArtigoService = new ResultadoArtigoService();
        EmailResultadoService emailResultadoService = new EmailResultadoService();
        EmailService emailService = new EmailService();
        PublicadorEventos publicadorEventos = new PublicadorEventos();

        publicadorEventos.adicionarObservador(new EmailAutorObserver(
                emailResultadoService,
                emailService,
                revisaoRepository
        ));

        CicloRevisoesService cicloRevisoesService = new CicloRevisoesService(
                artigoRepository,
                revisaoRepository,
                publicadorEventos,
                validacaoService
        );

        RevisaoService revisaoService = new RevisaoService(
                revisaoRepository,
                resultadoArtigoService,
                validacaoService,
                cicloRevisoesService
        );

        ValidadorDistribuicao validadorDistribuicao = new ValidadorNaoAutor();
        validadorDistribuicao.ligarCom(new ValidadorRevisorNaoRepetido());

        DistribuicaoArtigosService distribuicaoArtigosService = new DistribuicaoArtigosService(
                artigoRepository,
                revisaoRepository,
                validacaoService,
                validadorDistribuicao
        );

        ConsoleUI ui = new ConsoleUI();

        MenuAutor menuAutor = new MenuAutor(ui, submissaoService);
        MenuRevisor menuRevisor = new MenuRevisor(ui, revisaoService);

        MenuCoordenacao menuCoordenacao = new MenuCoordenacao(
                ui,
                eventoService,
                eventoAtual,
                usuarioService,
                comiteService,
                distribuicaoArtigosService
        );

        MenuPrincipal menuPrincipal = new MenuPrincipal(
                ui,
                comiteService,
                menuAutor,
                menuRevisor,
                menuCoordenacao
        );

        MenuCriacaoEvento menuCriacaoEvento = new MenuCriacaoEvento(
                ui,
                eventoService,
                eventoAtual,
                menuPrincipal
        );

        MenuInicial menuInicial = new MenuInicial(
                ui,
                usuarioService,
                autenticacaoService,
                eventoAtual,
                menuPrincipal,
                menuCriacaoEvento
        );

        menuInicial.iniciar();
    }
}
