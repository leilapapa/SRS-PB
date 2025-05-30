package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//cria dados iniciais para as classes Curso, Disciplinas, Turmas, Alunos, Nota
public class InserirDadosIniciais {
    public static void dadosIniciais(SecretariaAcademica secretaria) {

        //lista de cursos
        List<Curso> cursos = List.of(
                new Curso("Desenvolvimento Back-end [25E1-25E2]", "BACKEND2525"),
                new Curso("Desenvolvimento Front-end com Frameworks [24E3 - 24E4]", "FRONTEND2424"),
                new Curso("Fundamentos do Desenvolvimento de Software [23E3 - 23E4]", "FUNDDEV2324")
        );

        for (Curso curso : cursos) {
            if (!CursoCRUD.cursoExiste(curso.getCodigo())) {
                CursoCRUD.create(curso);
            }
        }

        //lista de disciplinas
        List<Disciplina> disciplinas = new ArrayList<>(List.of(
                // Backend
                new Disciplina("Desenvolvimento de Serviços Web e Testes com Java [25E2_3]", 40, cursos.get(0)),
                new Disciplina("Desenvolvimento Web com .NET e Bases de Dados [25E2_4]", 40, cursos.get(0)),
                new Disciplina("Projeto de Bloco: Desenvolvimento Back-end [25E1_5]", 40, cursos.get(0)),
                new Disciplina("Fundamentos de Desenvolvimento com Java [25E1_1]", 40, cursos.get(0)),
                new Disciplina("Fundamentos de Desenvolvimento com C# [25E1_2]", 40, cursos.get(0)),

                // Front-end
                new Disciplina("Projeto de Bloco: Desenvolvimento Front-end com Frameworks [24E3_5]", 40, cursos.get(1)),
                new Disciplina("Desenvolvimento Web com React [24E4_4]", 40, cursos.get(1)),
                new Disciplina("Desenvolvimento Mobile com React Native [24E4_3]", 40, cursos.get(1)),
                new Disciplina("Mobile-first UI com React [24E3_1]", 40, cursos.get(1)),
                new Disciplina("Fundamentos de React [24E3_2]", 40, cursos.get(1)),

                // Fundamentos do Desenvolvimento
                new Disciplina("Programação Web com HTML 5 e CSS 3 [23E3_1]", 40, cursos.get(2)),
                new Disciplina("Interatividade em Páginas Web [23E4_3]", 40, cursos.get(2)),
                new Disciplina("Projeto de Bloco: Fundamentos do Desenvolvimento de Software [23E3_5]", 40, cursos.get(2)),
                new Disciplina("Programação Web com JavaScript I [23E3_2]", 40, cursos.get(2)),
                new Disciplina("Programação Web com JavaScript II [23E4_4]", 40, cursos.get(2))
        ));

        for (Disciplina disciplina : disciplinas) {
            if (!DisciplinaCRUD.disciplinaExiste(disciplina.getNome(), disciplina.getCurso().getId())) {
                DisciplinaCRUD.create(disciplina);
            }
        }
        //lista de turmas
        List<Turma> turmas = List.of(
                new Turma("TURMA-JAVA-01", Set.of(DiaSemana.TER, DiaSemana.QUI), "09:50",
                        new Professor("Victor Amadeu", "victor.amadeu@email.com", "123", TituloProfessor.CONTRATADO)),
                new Turma("TURMA-DOTNET-01", Set.of(DiaSemana.SEG, DiaSemana.QUA), "09:50",
                        new Professor("Luiz Paulo Maia", "lp@email.com", "123", TituloProfessor.PROVISORIO)),
                new Turma("TURMA-PROJETO-01", Set.of(DiaSemana.SEX), "09:50",
                        new Professor("Victor Amadeu", "victor.amadeu@email.com", "123", TituloProfessor.CONTRATADO)),
                new Turma("TURMA-FUND-JAVA-01", Set.of(DiaSemana.TER, DiaSemana.QUA), "09:50",
                        new Professor("Victor Amadeu", "victor.amadeu@email.com", "123", TituloProfessor.CONTRATADO)),
                new Turma("TURMA-FUND-CSHARP-01", Set.of(DiaSemana.SEG, DiaSemana.QUA), "09:50",
                        new Professor("Luiz Paulo Maia", "lp@email.com", "123", TituloProfessor.PROVISORIO)),
                new Turma("TURMA-REACT-01", Set.of(DiaSemana.TER, DiaSemana.QUA), "09:50",
                        new Professor("Victor Amadeu", "victor@email.com", "123", TituloProfessor.CONTRATADO))
        );

        //turmas atribuidas às disciplinas
        turmas.get(0).setDisciplina(disciplinas.get(0)); // Java
        turmas.get(1).setDisciplina(disciplinas.get(1)); // .NET
        turmas.get(2).setDisciplina(disciplinas.get(2)); // Projeto Back-end
        turmas.get(3).setDisciplina(disciplinas.get(3)); // Fund. Java
        turmas.get(4).setDisciplina(disciplinas.get(4)); // Fund. C#
        turmas.get(5).setDisciplina(disciplinas.get(6)); // React

        for (Turma turma : turmas) {
            if (!TurmaCRUD.turmaExiste(turma.getCodigoTurma())) {
                TurmaCRUD.create(turma);
            }
        }

        //alunos + alunos atribuidos às turmas
        List<Aluno> alunos = List.of(
                new Aluno("Leandro Barbosa", "leandro.bmartins@al.infnet.edu.br", "123", "20231238", "ativo"),
                new Aluno("Leilane Papa", "leilane.papa@al.infnet.edu.br", "123", "20231239", "ativo"),
                new Aluno("Victor Cezar", "victor.cxavier@al.infnet.edu.br ", "123", "20231240", "ativo")
        );

        for (Aluno aluno : alunos) {
            if (AlunoCRUD.autenticar(aluno.getEmail(), aluno.getSenha()) == null) {
                secretaria.cadastrarAluno(aluno);

                for (Turma turma : turmas) {
                    String cod = turma.getCodigoTurma();
                    if (cod.equals("TURMA-JAVA-01") || cod.equals("TURMA-DOTNET-01") || cod.equals("TURMA-PROJETO-01")) {
                        secretaria.matricularAlunoEmTurma(aluno, turma);
                    }
                }

            } else {
                System.out.println("🔁 Aluno já cadastrado: " + aluno.getEmail());
            }
        }

        //notas para disciplinas já cursadas
        List<Nota> notas = List.of(
                new Nota(8.0f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(5)),
                new Nota(7.5f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(6)),
                new Nota(9.0f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(7)),
                new Nota(6.0f, SituacaoNota.REPROVADO, alunos.get(0), disciplinas.get(8)),
                new Nota(7.0f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(9)),

                new Nota(8.5f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(10)),
                new Nota(7.2f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(11)),
                new Nota(8.8f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(12)),
                new Nota(6.8f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(13)),
                new Nota(9.1f, SituacaoNota.APROVADO, alunos.get(0), disciplinas.get(14)),


                new Nota(8.0f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(5)),
                new Nota(7.5f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(6)),
                new Nota(9.0f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(7)),
                new Nota(6.0f, SituacaoNota.REPROVADO, alunos.get(1), disciplinas.get(8)),
                new Nota(7.0f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(9)),

                new Nota(8.5f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(10)),
                new Nota(7.2f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(11)),
                new Nota(8.8f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(12)),
                new Nota(6.8f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(13)),
                new Nota(9.1f, SituacaoNota.APROVADO, alunos.get(1), disciplinas.get(14)),

                new Nota(8.0f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(5)),
                new Nota(7.5f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(6)),
                new Nota(9.0f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(7)),
                new Nota(6.0f, SituacaoNota.REPROVADO, alunos.get(2), disciplinas.get(8)),
                new Nota(7.0f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(9)),

                new Nota(8.5f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(10)),
                new Nota(7.2f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(11)),
                new Nota(8.8f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(12)),
                new Nota(6.8f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(13)),
                new Nota(9.1f, SituacaoNota.APROVADO, alunos.get(2), disciplinas.get(14))
        );

        for (Nota nota : notas) {
            int alunoId = nota.getAluno().getId();
            int disciplinaId = nota.getDisciplina().getId();
            if (!NotaCRUD.notaExiste(alunoId, disciplinaId)) {
                NotaCRUD.create(nota);
            }
        }


        //secretaria
        System.out.println("\n📋 Secretaria cadastrada:");
        System.out.println("ID: " + secretaria.getId());
        System.out.println("Nome: " + secretaria.getNome());
        System.out.println("Email: " + secretaria.getEmail());
        System.out.println("Tipo: " + secretaria.getTipo());
        System.out.println("-------------------------------");

        System.out.println("✅ Dados iniciais de cursos, disciplinas, turmas e alunos foram inseridos com sucesso.");
    }
}