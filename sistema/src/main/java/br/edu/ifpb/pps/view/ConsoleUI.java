package br.edu.ifpb.pps.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void mostrarErro(String erro) {
        System.err.println("ERRO: " + erro);
    }

    public void mostrarTitulo(String titulo) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(titulo);
        System.out.println("========================================");
    }

    public String lerTexto(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public String lerTextoObrigatorio(String prompt) {
        String valor;

        do {
            valor = lerTexto(prompt);
            if (valor.isEmpty()) {
                mostrarErro("Campo obrigatorio.");
            }
        } while (valor.isEmpty());

        return valor;
    }

    public void pausar() {
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }


    private static final DateTimeFormatter DATA_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate lerData(String prompt) {
        while (true) {
            String valor = lerTexto(prompt + " (dd/MM/yyyy)");

            try {
                return LocalDate.parse(valor, DATA_FORMATTER);
            } catch (DateTimeParseException e) {
                mostrarErro("Informe uma data valida no formato dd/MM/yyyy.");
            }
        }
    }

    public int lerInteiro(String prompt) {
    while (true) {
        String valor = lerTexto(prompt);

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            mostrarErro("Informe um numero inteiro valido.");
        }
    }
}
}