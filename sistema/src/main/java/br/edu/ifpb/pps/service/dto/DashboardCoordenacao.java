package br.edu.ifpb.pps.service.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardCoordenacao {
    private int totalArtigosSubmetidos;
    private int totalRevisores;
    private int totalArtigosAvaliados;
    private int totalArtigosPendentes;
    private List<PendenciaAvaliacao> pendencias = new ArrayList<>();

    public int getTotalArtigosSubmetidos() {
        return totalArtigosSubmetidos;
    }

    public void setTotalArtigosSubmetidos(int totalArtigosSubmetidos) {
        this.totalArtigosSubmetidos = totalArtigosSubmetidos;
    }

    public int getTotalRevisores() {
        return totalRevisores;
    }

    public void setTotalRevisores(int totalRevisores) {
        this.totalRevisores = totalRevisores;
    }

    public int getTotalArtigosAvaliados() {
        return totalArtigosAvaliados;
    }

    public void setTotalArtigosAvaliados(int totalArtigosAvaliados) {
        this.totalArtigosAvaliados = totalArtigosAvaliados;
    }

    public int getTotalArtigosPendentes() {
        return totalArtigosPendentes;
    }

    public void setTotalArtigosPendentes(int totalArtigosPendentes) {
        this.totalArtigosPendentes = totalArtigosPendentes;
    }

    public List<PendenciaAvaliacao> getPendencias() {
        return new ArrayList<>(pendencias);
    }

    public void adicionarPendencia(PendenciaAvaliacao pendencia) {
        if (pendencia == null) {
            throw new IllegalArgumentException("Pendencia e obrigatoria.");
        }

        pendencias.add(pendencia);
    }
}