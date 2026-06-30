package br.edu.ifpb.pps.repository;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Autoria;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;

public class ArtigoRepository {
    private final BancoDeDados banco;

    public ArtigoRepository(BancoDeDados banco) {
        this.banco = banco;
    }

    public void salvar(Artigo artigo) {
        banco.getArtigos().add(artigo);
    }

    public List<Artigo> listarTodos() {
        return banco.getArtigos();
    }

    public List<Artigo> listarPorEvento(Evento evento) {
        List<Artigo> resultado = new ArrayList<>();

        for (Artigo artigo : banco.getArtigos()) {
            if (artigo.getEvento() == evento) {
                resultado.add(artigo);
            }
        }

        return resultado;
    }

    public List<Artigo> listarPorAutor(Usuario usuario, Evento evento) {
        List<Artigo> resultado = new ArrayList<>();

        for (Artigo artigo : banco.getArtigos()) {
            if (artigo.getEvento() != evento || artigo.getAutores() == null) {
                continue;
            }

            for (Autoria autoria : artigo.getAutores()) {
                if (autoria.getUser() == usuario) {
                    resultado.add(artigo);
                    break;
                }
            }
        }

        return resultado;
    }

    public void limpar() {
        banco.getArtigos().clear();
    }
}