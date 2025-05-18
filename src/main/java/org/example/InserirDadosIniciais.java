package org.example;

import java.util.List;
import java.util.Set;

//cria dados iniciais para as classes Curso, Disciplinas, Turmas e Alunos
public class InserirDadosIniciais {
    public static void dadosCursoDisciplinasTurmasEAlunos(SecretariaAcademica secretaria) {

        //Curso
        Curso curso = new Curso("Desenvolvimento Back-end", "BACKEND2025");
        if (!CursoCRUD.cursoExiste(curso.getCodigo())) {
            CursoCRUD.create(curso);
        }

        //Disciplinas
        Disciplina d1 = new Disciplina("Desenvolvimento de Serviços Web e Testes com Java", 40, curso);
        Disciplina d2 = new Disciplina("Desenvolvimento Web com .NET e Bases de Dados", 40, curso);
        Disciplina d3 = new Disciplina("Projeto de Bloco: Desenvolvimento Back-end", 40, curso);

        if (!DisciplinaCRUD.disciplinaExiste(d1.getNome(), curso.getId())) DisciplinaCRUD.create(d1);
        if (!DisciplinaCRUD.disciplinaExiste(d2.getNome(), curso.getId())) DisciplinaCRUD.create(d2);
        if (!DisciplinaCRUD.disciplinaExiste(d3.getNome(), curso.getId())) DisciplinaCRUD.create(d3);

        //Turmas
        Turma t1 = new Turma("TURMA-JAVA-01", Set.of(DiaSemana.TER, DiaSemana.QUI), "09:50",
                new Professor("Victor Amadeu", "victor.amadeu@email.com", "senha123", TituloProfessor.CONTRATADO));
        t1.setDisciplina(d1);

        Turma t2 = new Turma("TURMA-DOTNET-01", Set.of(DiaSemana.SEG, DiaSemana.QUA), "09:50",
                new Professor("Luiz Paulo Maia", "lp@email.com", "senha123", TituloProfessor.PROVISORIO));
        t2.setDisciplina(d2);

        Turma t3 = new Turma("TURMA-PROJETO-01", Set.of(DiaSemana.SEX), "09:50",
                new Professor("Victor Amadeu", "victor.amadeu@email.com", "senha123", TituloProfessor.CONTRATADO));
        t3.setDisciplina(d3);

        if (!TurmaCRUD.turmaExiste(t1.getCodigoTurma())) TurmaCRUD.create(t1);
        if (!TurmaCRUD.turmaExiste(t2.getCodigoTurma())) TurmaCRUD.create(t2);
        if (!TurmaCRUD.turmaExiste(t3.getCodigoTurma())) TurmaCRUD.create(t3);

        // Alunos e matrícula
        List<Aluno> novosAlunos = List.of(
                new Aluno("Leandro Barbosa", "leandro@email.com", "123", "20231238", "ativo"),
                new Aluno("Leilane Papa", "leilane@email.com", "123", "20231239", "ativo"),
                new Aluno("Victor Cezar", "victor@email.com", "123", "20231240", "ativo")
        );

        for (Aluno a : novosAlunos) {
            if (AlunoCRUD.autenticar(a.getEmail(), a.getSenha()) == null) {
                secretaria.cadastrarAluno(a);
                secretaria.matricularAlunoEmTurma(a, t1); // Matricula todos os alunos na turma JAVA por padrão
            } else {
                System.out.println("🔁 Aluno já cadastrado: " + a.getEmail());
            }
        }

        // Imprime dados da secretaria cadastrada
        System.out.println("\n📋 Secretaria cadastrada:");
        System.out.println("ID: " + secretaria.getId());
        System.out.println("Nome: " + secretaria.getNome());
        System.out.println("Email: " + secretaria.getEmail());
        System.out.println("Tipo: " + secretaria.getTipo());
        System.out.println("-------------------------------");

        System.out.println("✅ Dados iniciais de cursos, disciplinas, turmas e alunos inseridos com sucesso.");
    }
}
