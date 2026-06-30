package br.edu.ifpb.pps.service;
import java.util.List;

public class ValidacaoGenericaService {
    

    public void textoObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " e obrigatorio.");
        }
    }

    public void objetoObrigatorio(Object valor, String nomeCampo) {
        if (valor == null) {
            throw new IllegalArgumentException(nomeCampo + " e obrigatorio.");
        }
    }

    public void listaObrigatoria(List<?> valores, String nomeCampo) {
        if (valores == null || valores.isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " deve possuir ao menos um item.");
        }
    }

}
