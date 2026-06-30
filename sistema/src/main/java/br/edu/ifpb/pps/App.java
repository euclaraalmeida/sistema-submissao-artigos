package br.edu.ifpb.pps;

import br.edu.ifpb.pps.repository.ArtigoRepository;
import br.edu.ifpb.pps.repository.BancoDeDados;
import br.edu.ifpb.pps.repository.EventoRepository;
import br.edu.ifpb.pps.repository.RevisaoRepository;
import br.edu.ifpb.pps.repository.UsuarioRepository;
import br.edu.ifpb.pps.view.ConsoleUI;
import br.edu.ifpb.pps.view.MenuInicial;

public class App {
    public static void main(String[] args) {
        BancoDeDados banco = new BancoDeDados();
        UsuarioRepository usuarioRepository = new UsuarioRepository(banco);
        EventoRepository eventoRepository = new EventoRepository(banco);
        ArtigoRepository artigoRepository = new ArtigoRepository(banco);
        RevisaoRepository revisaoRepository = new RevisaoRepository(banco);

        ConsoleUI ui = new ConsoleUI();
        //MenuInicial menuInicial = new MenuInicial(ui);

       // menuInicial.iniciar();

        
    }
}