package br.edu.ifpb.pps.domain.model;

public class Pesquisador {

    private String nome;
    private String email;
    private String senha;
    private String instituicao;

    public Pesquisador(String nome, String email, String senha, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.instituicao = instituicao;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getInstituicao() {
        return instituicao;
    }
}