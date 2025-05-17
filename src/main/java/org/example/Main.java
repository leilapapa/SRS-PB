package org.example;

public class Main {
    public static void main(String[] args) {
        String caminhoEntrada = "src/main/DadosCSV/alunos.csv";

        boolean sucesso = AlunoCSVService.carregarAlunosCSV(caminhoEntrada);

        if (sucesso) {
            LoginView.criarJanelaDeLogin();
        }
    }
}
