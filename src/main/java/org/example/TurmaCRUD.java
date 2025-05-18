package org.example;

import java.sql.*;
import java.util.*;

public class TurmaCRUD {

    public static void create(Turma turma) {
        String sql = "INSERT INTO turma (codigo_turma, dias_semana, horario, professor_nome, disciplina_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, turma.getCodigoTurma());
            pstmt.setString(2, String.join(",", turma.getDiasSemana().stream().map(Enum::name).toList()));
            pstmt.setString(3, turma.getHorario());
            pstmt.setString(4, turma.getProfessor() != null ? turma.getProfessor().getNome() : null);
            pstmt.setInt(5, turma.getDisciplina().getId());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    turma.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir turma: " + e.getMessage());
        }
    }

    public static List<Turma> readAll() {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT t.id, t.codigo_turma, t.dias_semana, t.horario, t.professor_nome, d.id as disciplina_id, d.nome as disciplina_nome, d.vagas, c.id as curso_id, c.nome as curso_nome, c.codigo as curso_codigo " +
                "FROM turma t " +
                "JOIN disciplina d ON t.disciplina_id = d.id " +
                "JOIN curso c ON d.curso_id = c.id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso(rs.getInt("curso_id"), rs.getString("curso_nome"), rs.getString("curso_codigo"));
                Disciplina disciplina = new Disciplina(rs.getInt("disciplina_id"), rs.getString("disciplina_nome"), rs.getInt("vagas"), curso);

                Set<DiaSemana> diasSemana = new HashSet<>();
                String[] dias = rs.getString("dias_semana").split(",");
                for (String dia : dias) {
                    try {
                        diasSemana.add(DiaSemana.valueOf(dia));
                    } catch (IllegalArgumentException ignored) {}
                }

                Turma turma = new Turma(
                        rs.getInt("id"),
                        rs.getString("codigo_turma"),
                        diasSemana,
                        rs.getString("horario"),
                        new Professor(rs.getString("professor_nome"), "", "", null),
                        disciplina
                );
                turmas.add(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar turmas: " + e.getMessage());
        }

        return turmas;
    }

    //lista as turmas que o aluno está matriculado
    public static List<Turma> buscarTurmasDoAluno(int alunoId) {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT t.*, d.id as disciplina_id, d.nome as disciplina_nome, d.vagas, " +
                "c.id as curso_id, c.nome as curso_nome, c.codigo as curso_codigo " +
                "FROM turma t " +
                "JOIN disciplina d ON t.disciplina_id = d.id " +
                "JOIN curso c ON d.curso_id = c.id " +
                "JOIN turma_aluno ta ON t.id = ta.turma_id " +
                "WHERE ta.aluno_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("curso_id"),
                        rs.getString("curso_nome"),
                        rs.getString("curso_codigo")
                );
                Disciplina disciplina = new Disciplina(
                        rs.getInt("disciplina_id"),
                        rs.getString("disciplina_nome"),
                        rs.getInt("vagas"),
                        curso
                );
                Set<DiaSemana> diasSemana = new HashSet<>();
                String[] dias = rs.getString("dias_semana").split(",");
                for (String dia : dias) {
                    try {
                        diasSemana.add(DiaSemana.valueOf(dia));
                    } catch (IllegalArgumentException ignored) {}
                }

                Turma turma = new Turma(
                        rs.getInt("id"),
                        rs.getString("codigo_turma"),
                        diasSemana,
                        rs.getString("horario"),
                        new Professor(rs.getString("professor_nome"), "", "", null),
                        disciplina
                );
                turmas.add(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas do aluno: " + e.getMessage());
        }

        return turmas;
    }

    //verifica se turma já existe
    public static boolean turmaExiste(String codigoTurma) {
        String sql = "SELECT COUNT(*) FROM turma WHERE codigo_turma = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigoTurma);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência da turma: " + e.getMessage());
            return false;
        }
    }

}
