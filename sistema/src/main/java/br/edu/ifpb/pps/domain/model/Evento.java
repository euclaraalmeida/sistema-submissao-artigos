package br.edu.ifpb.pps.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;

public class Evento {
    private Long id;
    private String nome;
    private String cidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private CategoriaSubmissao categoriaSubmissao;
    private boolean emailsResultadoAutoresEnviados;

    private List<AreaTematica> areasTematicas = new ArrayList<>();
    private List<MembroComite> membrosComite = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public CategoriaSubmissao getCategoriaSubmissao() {
        return categoriaSubmissao;
    }

    public void setCategoriaSubmissao(CategoriaSubmissao categoriaSubmissao) {
        this.categoriaSubmissao = categoriaSubmissao;
    }

    public boolean isEmailsResultadoAutoresEnviados() {
        return emailsResultadoAutoresEnviados;
    }

    public void setEmailsResultadoAutoresEnviados(boolean emailsResultadoAutoresEnviados) {
        this.emailsResultadoAutoresEnviados = emailsResultadoAutoresEnviados;
    }

    public List<AreaTematica> getAreasTematicas() {
        return new ArrayList<>(areasTematicas);
    }

    public void adicionarAreaTematica(AreaTematica areaTematica) {
        if (areaTematica == null) {
            throw new IllegalArgumentException("Area tematica e obrigatoria.");
        }

        areasTematicas.add(areaTematica);
    }

    public List<MembroComite> getMembrosComite() {
        return new ArrayList<>(membrosComite);
    }

    public void adicionarMembroComite(MembroComite membro) {
        if (membro == null) {
            throw new IllegalArgumentException("Membro do comite e obrigatorio.");
        }

        membrosComite.add(membro);
    }
}
