package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializar o banco de dados
        DatabaseConfig.criarTabelas();

        // 2. Carregar alunos do CSV
        String caminhoEntrada = "src/main/DadosCSV/alunos.csv";
        boolean sucesso = AlunoCSVService.carregarAlunosCSV(caminhoEntrada);

        if (sucesso) {
            System.out.println("Alunos carregados com sucesso do CSV!");

            // 3. Demonstração das operações CRUD
            demonstrarOperacoesCRUD();

            // 4. Iniciar a interface de login
            LoginView.criarJanelaDeLogin();
        } else {
            System.err.println("Falha ao carregar alunos do CSV. Verifique o arquivo.");
        }
    }

    private static void demonstrarOperacoesCRUD() {
        // Exemplo de criação de aluno manual (além dos carregados do CSV)
        Aluno novoAluno = new Aluno(
                "Maria Oliveira",
                "maria@email.com",
                "senha123",
                "2023-05-20",
                "ativo"
        );
        AlunoCRUD.create(novoAluno);
        System.out.println("\nNovo aluno criado com ID: " + novoAluno.getId());

        // Buscar todos os alunos
        List<Aluno> todosAlunos = AlunoCRUD.readAll();
        System.out.println("\nLista de todos os alunos:");
        todosAlunos.forEach(aluno -> System.out.println(aluno.getNome() + " - " + aluno.getEmail()));

        // Buscar um aluno específico por ID
        if (!todosAlunos.isEmpty()) {
            Aluno primeiroAluno = AlunoCRUD.readById(todosAlunos.get(0).getId());
            System.out.println("\nPrimeiro aluno encontrado:");
            System.out.println(primeiroAluno);

            // Atualizar um aluno
            primeiroAluno.getMatricula().setStatus("inativo");
            AlunoCRUD.update(primeiroAluno);
            System.out.println("\nStatus do aluno atualizado:");
            System.out.println(AlunoCRUD.readById(primeiroAluno.getId()));
        }

        // Exemplo de matrícula em turma (assumindo que existe turma com ID 1)
        if (novoAluno.getId() > 0) {
            AlunoCRUD.matricularEmTurma(novoAluno.getId(), 1);
            System.out.println("\nAluno matriculado na turma 1");
        }
    }
}