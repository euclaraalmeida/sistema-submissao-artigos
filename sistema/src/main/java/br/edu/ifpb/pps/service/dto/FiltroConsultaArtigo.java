package br.edu.ifpb.pps.service.dto;

import br.edu.ifpb.pps.domain.enums.ResultadoDecisao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.MembroComite;

public class FiltroConsultaArtigo {
    private AreaTematica area;
    private String estado;
    private ResultadoDecisao resultado;
    private MembroComite avaliador;

    public AreaTematica getArea() {
        return area;
    }

    public void setArea(AreaTematica area) {
        this.area = area;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ResultadoDecisao getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoDecisao resultado) {
        this.resultado = resultado;
    }

    public MembroComite getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(MembroComite avaliador) {
        this.avaliador = avaliador;
    }

    public boolean temArea() {
        return area != null;
    }

    public boolean temEstado() {
        return estado != null && !estado.trim().isEmpty();
    }

    public boolean temResultado() {
        return resultado != null;
    }

    public boolean temAvaliador() {
        return avaliador != null;
    }
}