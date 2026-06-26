package br.edu.ifpb.pps.domain.model;

import br.edu.ifpb.pps.domain.enums.TipoAutoria;

public class Autoria {
    private Long id;
    private Usuario user;
    private TipoAutoria tipoAutoria;
    private Artigo artigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public TipoAutoria getTipoAutoria() {
        return tipoAutoria;
    }

    public void setTipoAutoria(TipoAutoria tipoAutoria) {
        this.tipoAutoria = tipoAutoria;
    }

    public Artigo getArtigo() {
        return artigo;
    }

    public void setArtigo(Artigo artigo) {
        this.artigo = artigo;
    }
}
