package br.edu.ifpb.pps.view;

import java.util.Scanner;

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
}