package br.edu.ifpb.pps.domain.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {


public class Evento {

    private String nome;
    private String cidade;
    private Date dataInicio;
    private Date dataFim;
    private Date prazoSubmissao;
    private Date prazoRevisao;
    private Coordenador coordenador;
    private List<CategoriaSubmissao> categorias;
    private boolean arquivado;

    public Evento(String nome, String cidade, Date dataInicio, Date dataFim,
                  Date prazoSubmissao, Date prazoRevisao,
                  Coordenador coordenador, List<CategoriaSubmissao> categorias) {
        this.nome = nome;
        this.cidade = cidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.prazoSubmissao = prazoSubmissao;
        this.prazoRevisao = prazoRevisao;
        this.coordenador = coordenador;
        this.categorias = categorias;
        this.arquivado = false;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public Date getPrazoSubmissao() {
        return prazoSubmissao;
    }

    public Date getPrazoRevisao() {
        return prazoRevisao;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public List<CategoriaSubmissao> getCategorias() {
        return categorias;
    }

    public boolean isArquivado() {
        return arquivado;
    }

    public void arquivar() {
        this.arquivado = true;
    }
}
}
