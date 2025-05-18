package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Inicializa o banco
        DatabaseConfig.criarTabelas();

        //Lista de alunos de exemplo (saida no terminal e grava no banco). Excluir escola.db sempre que rodar o main
        demonstrarOperacoesCRUD();

        //Abre janela de login
        LoginView.criarJanelaDeLogin();
    }

    //Lista de alunos
    private static void demonstrarOperacoesCRUD() {
        List<Aluno> novosAlunos = List.of(
                new Aluno("Leandro Barbosa", "leandro@email.com", "123", "20231238", "ativo"),
                new Aluno("Leilane Papa", "leilane@email.com", "123", "20231239", "ativo"),
                new Aluno("Victor Cezar", "victor@email.com", "123", "20231239", "ativo")
        );

        for (Aluno a : novosAlunos) {
            AlunoCRUD.create(a);
        }

        //Mostra no terminal
        List<Aluno> alunos = AlunoCRUD.readAll();

        System.out.println("\n📋 Lista de alunos cadastrados:");
        for (Aluno a : alunos) {
            System.out.println("ID: " + a.getId());
            System.out.println("Nome: " + a.getNome());
            System.out.println("Email: " + a.getEmail());
            System.out.println("Matrícula: " + a.getMatricula().getMatricula());
            System.out.println("Status: " + a.getMatricula().getStatus());
            System.out.println("-------------------------------");
        }
    }
}