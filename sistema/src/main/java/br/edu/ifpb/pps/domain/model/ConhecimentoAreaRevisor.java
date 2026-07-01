package br.edu.ifpb.pps.domain.model;

public class ConhecimentoAreaRevisor {
    private AreaTematica area;
    private int nivelConhecimento;

    public ConhecimentoAreaRevisor(AreaTematica area, int nivelConhecimento) {
        setArea(area);
        setNivelConhecimento(nivelConhecimento);
    }

    public AreaTematica getArea() {
        return area;
    }

    public void setArea(AreaTematica area) {
        if (area == null) {
            throw new IllegalArgumentException("Area tematica e obrigatoria.");
        }

        this.area = area;
    }

    public int getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(int nivelConhecimento) {
        if (nivelConhecimento < 1 || nivelConhecimento > 3) {
            throw new IllegalArgumentException("Nivel de conhecimento deve estar entre 1 e 3.");
        }

        this.nivelConhecimento = nivelConhecimento;
    }
}
