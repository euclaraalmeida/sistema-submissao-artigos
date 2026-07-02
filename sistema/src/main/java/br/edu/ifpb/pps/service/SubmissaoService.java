package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.enums.TipoAutoria;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Artigo;
import br.edu.ifpb.pps.domain.model.Autoria;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.repository.ArtigoRepository;

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
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.textoObrigatorio(titulo, "Titulo");
        validacaoService.textoObrigatorio(resumo, "Resumo");
        validacaoService.listaObrigatoria(areas, "Areas tematicas");

        Artigo artigo = new Artigo();
        artigo.setTitulo(titulo);
        artigo.setResumo(resumo);
        artigo.setEvento(evento);

        for (AreaTematica area : areas) {
            artigo.adicionarAreaTematica(area);
        }

        Autoria autoriaPrincipal = criarAutoriaPrincipal(usuario, artigo);
        artigo.adicionarAutor(autoriaPrincipal);

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

    // metodo para adicionar coAutor
    
}