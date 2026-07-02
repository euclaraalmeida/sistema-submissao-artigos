package br.edu.ifpb.pps.service;
import java.util.List;
import java.util.regex.Pattern;

public class ValidacaoGenericaService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    

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

    public void emailValido(String email, String nomeCampo) {
        textoObrigatorio(email, nomeCampo);

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException(nomeCampo + " invalido.");
        }
    }

}
