package br.edu.ifpb.pps.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MembroComite {
    private Long id;
    private Usuario usuario;
    private List<ConhecimentoAreaRevisor> conhecimentosPorArea = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ConhecimentoAreaRevisor> getConhecimentosPorArea() {
        return new ArrayList<>(conhecimentosPorArea);
    }

    public void adicionarConhecimentoArea(AreaTematica areaTematica, int nivelConhecimento) {
        if (areaTematica == null) {
            throw new IllegalArgumentException("Area tematica e obrigatoria.");
        }

        conhecimentosPorArea.add(new ConhecimentoAreaRevisor(areaTematica, nivelConhecimento));
    }

    public void adicionarEspecialidade(AreaTematica areaTematica) {
        if (areaTematica == null) {
            throw new IllegalArgumentException("Area tematica e obrigatoria.");
        }

        adicionarConhecimentoArea(areaTematica, 1);
    }

    public int maiorNivelConhecimentoNasAreas(List<AreaTematica> areasTematicas) {
        int maiorNivel = 0;

        for (AreaTematica areaTematica : areasTematicas) {
            for (ConhecimentoAreaRevisor conhecimento : conhecimentosPorArea) {
                if (conhecimento.getArea() == areaTematica
                        && conhecimento.getNivelConhecimento() > maiorNivel) {
                    maiorNivel = conhecimento.getNivelConhecimento();
                }
            }
        }

        return maiorNivel;
    }
}
