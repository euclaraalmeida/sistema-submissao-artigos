package br.edu.ifpb.pps.view;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.Veredito;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.RevisaoService;

public class MenuRevisor {
    private final ConsoleUI ui;
    private final RevisaoService revisaoService;

    public MenuRevisor(ConsoleUI ui, RevisaoService revisaoService) {
        this.ui = ui;
        this.revisaoService = revisaoService;
    }

    public void iniciar(Usuario usuario, Evento evento) {
        boolean rodando = true;}
}