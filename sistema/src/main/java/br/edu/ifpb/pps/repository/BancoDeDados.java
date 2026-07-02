package br.edu.ifpb.pps.repository;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.AtribuicaoRevisao;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Parecer;
import br.edu.ifpb.pps.domain.model.Usuario;

public class BancoDeDados {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Evento> eventos = new ArrayList<>();
    private final List<Artigo> artigos = new ArrayList<>();
    private final List<AtribuicaoRevisao> atribuicoes = new ArrayList<>();
    private final List<Parecer> pareceres = new ArrayList<>();

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public List<AtribuicaoRevisao> getAtribuicoes() {
        return atribuicoes;
    }

    public List<Parecer> getPareceres() {
        return pareceres;
    }

    public void limparDadosDoEvento() {
        eventos.clear();
        artigos.clear();
        atribuicoes.clear();
        pareceres.clear();
    }
}