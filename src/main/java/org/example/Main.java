package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializa o banco
        DatabaseConfig.criarTabelas();

        // Autentica ou cria a secretaria
        SecretariaAcademica secretaria = SecretariaAcademicaCRUD.autenticar("secretaria@email.com", "admin");

        if (secretaria == null) {
            secretaria = new SecretariaAcademica("Secretaria", "secretaria@email.com", "admin");
            SecretariaAcademicaCRUD.create(secretaria);
            System.out.println("✅ Secretaria cadastrada.");
            secretaria = SecretariaAcademicaCRUD.autenticar("secretaria@email.com", "admin");
        } else {
            System.out.println("🔁 Secretaria já cadastrada.");
        }

        if (secretaria != null) {
            System.out.println("\n✅ Secretaria autenticada com sucesso!\n");
        } else {
            System.err.println("❌ Falha na autenticação da secretaria.");
            return;
        }

        //Insere dados usando a secretaria autenticada
        InserirDadosIniciais.dadosCursoDisciplinasTurmasEAlunos(secretaria);

        SecretariaAcademicaCRUD.listarSecretarias();

        // Lista os cursos
        List<Curso> cursos = CursoCRUD.readAll();
        System.out.println("Cursos cadastrados:");
        for (Curso curso : cursos) {
            System.out.println("- " + curso.getNome() + " (" + curso.getCodigo() + ")");
        }

        // Lista disciplinas
        List<Disciplina> disciplinas = DisciplinaCRUD.readAll();
        System.out.println("\nDisciplinas cadastradas:");
        for (Disciplina d : disciplinas) {
            System.out.println("- " + d.getNome() + " | Curso: " + d.getCurso().getNome());
        }

        // Lista turmas
        List<Turma> turmas = TurmaCRUD.readAll();
        System.out.println("\nTurmas cadastradas:");
        for (Turma turma : turmas) {
            System.out.println("- " + turma.getCodigoTurma() +
                    " | Disciplina: " + turma.getDisciplina().getNome() +
                    " | Curso: " + turma.getDisciplina().getCurso().getNome() +
                    " | Horário: " + turma.getHorario());
        }

        // Lista alunos
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

        // Abre a interface de login do aluno
        LoginView.criarJanelaDeLogin();
    }
}
