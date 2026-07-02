package br.edu.ifpb.pps.service;


import java.util.Collections;
import java.util.List;
import java.time.LocalDate;

import br.edu.ifpb.pps.domain.enums.TipoAutoria;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Autoria;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.repository.ArtigoRepository;
import java.time.LocalDate;

public class SubmissaoService {
    private final ArtigoRepository artigoRepository;
    private final ValidacaoGenericaService validacaoService;

    public SubmissaoService(ArtigoRepository artigoRepository, ValidacaoGenericaService validacaoService) {
        this.artigoRepository = artigoRepository;
        this.validacaoService = validacaoService;
    }

    public Artigo submeterArtigo(
            Usuario usuario,
            Evento evento,
            String titulo,
            String resumo,
            List<AreaTematica> areas
    ) {
        return submeterArtigo(usuario, evento, titulo, resumo, areas, Collections.emptyList());
    }

    public Artigo submeterArtigo(
            Usuario usuario,
            Evento evento,
            String titulo,
            String resumo,
            List<AreaTematica> areas,
            List<Usuario> coautores
    ) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.textoObrigatorio(titulo, "Titulo");
        validacaoService.textoObrigatorio(resumo, "Resumo");
        validacaoService.listaObrigatoria(areas, "Areas tematicas");
        validarPrazoSubmissao(evento);

        Artigo artigo = new Artigo();
        artigo.setTitulo(titulo);
        artigo.setResumo(resumo);
        artigo.setEvento(evento);

        for (AreaTematica area : areas) {
            artigo.adicionarAreaTematica(area);
        }

        Autoria autoriaPrincipal = criarAutoriaPrincipal(usuario, artigo);
        artigo.adicionarAutor(autoriaPrincipal);

        adicionarCoautores(artigo, usuario, coautores);

        artigoRepository.salvar(artigo);

        return artigo;
    }

    public List<Artigo> listarArtigosDoAutor(Usuario usuario, Evento evento) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");

        return artigoRepository.listarPorAutor(usuario, evento);
    }

    private Autoria criarAutoriaPrincipal(Usuario usuario, Artigo artigo) {
        Autoria autoria = new Autoria();
        autoria.setUser(usuario);
        autoria.setTipoAutoria(TipoAutoria.AUTOR_PRINCIPAL);
        autoria.setArtigo(artigo);

        return autoria;
    }
    private void validarPrazoSubmissao(Evento evento) {
        LocalDate hoje = LocalDate.now();

        if (evento.getDataFim() == null) {
            throw new IllegalStateException("Evento nao possui data final definida.");
        }

        if (hoje.isAfter(evento.getDataFim())) {
            throw new IllegalStateException("Prazo de submissao encerrado.");
        }
}

    private void adicionarCoautores(Artigo artigo, Usuario autorPrincipal, List<Usuario> coautores) {
        if (coautores == null) {
            return;
        }

        for (Usuario coautor : coautores) {
            validacaoService.objetoObrigatorio(coautor, "Coautor");

            if (coautor == autorPrincipal || artigoJaPossuiAutor(artigo, coautor)) {
                throw new IllegalArgumentException("Autor duplicado no artigo: " + coautor.getEmail());
            }

            Autoria autoria = new Autoria();
            autoria.setUser(coautor);
            autoria.setTipoAutoria(TipoAutoria.COAUTOR);
            autoria.setArtigo(artigo);
            artigo.adicionarAutor(autoria);
        }
    }

    private boolean artigoJaPossuiAutor(Artigo artigo, Usuario usuario) {
        for (Autoria autoria : artigo.getAutores()) {
            if (autoria.getUser() == usuario) {
                return true;
            }
        }

        return false;
    }
}
