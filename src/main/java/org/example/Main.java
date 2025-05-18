package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializa o banco
        DatabaseConfig.criarTabelas();

        // Importa cursos e disciplinas a partir do JSON
        CursoJsonImport.importarCursosParaBanco("src/main/java/org/example/cursos.json");

        // Tenta autenticar antes de criar
        SecretariaAcademica secretaria = SecretariaAcademicaCRUD.autenticar("secretaria@email.com", "admin");

        if (secretaria == null) {
            // Se não existe, cria
            secretaria = new SecretariaAcademica("Secretaria", "secretaria@email.com", "admin");
            SecretariaAcademicaCRUD.create(secretaria);
            System.out.println("✅ Secretaria cadastrada.");
        } else {
            System.out.println("🔁 Secretaria já cadastrada.");
        }

        SecretariaAcademicaCRUD.listarSecretarias();
        demonstrarOperacoesCRUD(secretaria);

        // Testa autenticação da secretaria
        SecretariaAcademica secretariaAutenticada = SecretariaAcademicaCRUD.autenticar("secretaria@email.com", "admin");
        if (secretariaAutenticada != null) {
            System.out.println("\n✅ Secretaria autenticada com sucesso!\n");
            demonstrarOperacoesCRUD(secretariaAutenticada);
        } else {
            System.err.println("❌ Falha na autenticação da secretaria.");
            return;
        }

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

        // Abre a interface de login do aluno
        LoginView.criarJanelaDeLogin();
    }

    private static void demonstrarOperacoesCRUD(SecretariaAcademica secretaria) {
        List<Aluno> novosAlunos = List.of(
                new Aluno("Leandro Barbosa", "leandro@email.com", "123", "20231238", "ativo"),
                new Aluno("Leilane Papa", "leilane@email.com", "123", "20231239", "ativo"),
                new Aluno("Victor Cezar", "victor@email.com", "123", "20231239", "ativo")
        );

        for (Aluno a : novosAlunos) {
            if (AlunoCRUD.autenticar(a.getEmail(), a.getSenha()) == null) {
                secretaria.cadastrarAluno(a);
            } else {
                System.out.println("🔁 Aluno já cadastrado: " + a.getEmail());
            }
        }

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
