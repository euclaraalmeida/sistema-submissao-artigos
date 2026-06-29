package br.edu.ifpb.pps.domain.model;

import java.time.LocalDate;

import br.edu.ifpb.pps.domain.enums.StatusRevisao;

public class AtribuicaoRevisao {
    // essa classe nao distribui , elea repesenta uma revisao atribuida
    private Long id;
    private Artigo artigo;
    private MembroComite revisor;
    private StatusRevisao status;
    private Parecer parecer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artigo getArtigo() {
        return artigo;
    }

    public void setArtigo(Artigo artigo) {
        this.artigo = artigo;
    }

    public MembroComite getRevisor() {
        return revisor;
    }

    public void setRevisor(MembroComite revisor) {
        this.revisor = revisor;
    }

    public StatusRevisao getStatus() {
        return status;
    }

    public void setStatus(StatusRevisao status) {
        this.status = status;
    }

    public Parecer getParecer(){
        return parecer;
    }
    public void setParecer(Parecer parecer){
        this.parecer = parecer;
    }
    
}
