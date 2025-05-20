package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaCRUD {

    public static void create(Nota nota) {
        String sql = "INSERT INTO nota (aluno_id, disciplina_id, valor, situacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nota.getAluno().getId());
            pstmt.setInt(2, nota.getDisciplina().getId());
            pstmt.setFloat(3, nota.getValor());
            pstmt.setString(4, nota.getSituacao().name());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao criar nota: " + e.getMessage());
        }
    }

    public static List<Nota> getNotasDoAluno(int alunoId) {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT n.valor, n.situacao, d.id AS disciplina_id, d.nome AS disciplina_nome, d.vagas, " +
                "c.id AS curso_id, c.nome AS curso_nome, c.codigo AS curso_codigo " +
                "FROM nota n " +
                "JOIN disciplina d ON n.disciplina_id = d.id " +
                "JOIN curso c ON d.curso_id = c.id " +
                "WHERE n.aluno_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso(rs.getInt("curso_id"), rs.getString("curso_nome"), rs.getString("curso_codigo"));
                Disciplina disciplina = new Disciplina(rs.getInt("disciplina_id"), rs.getString("disciplina_nome"), rs.getInt("vagas"), curso);
                SituacaoNota situacao = SituacaoNota.valueOf(rs.getString("situacao"));
                float valor = rs.getFloat("valor");
                Nota nota = new Nota(valor, situacao);
                nota.setDisciplina(disciplina);
                notas.add(nota);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar notas do aluno: " + e.getMessage());
        }
        return notas;
    }

    //verifica se a nota ja existe
    public static boolean notaExiste(int alunoId, int disciplinaId) {
        String sql = "SELECT COUNT(*) FROM nota WHERE aluno_id = ? AND disciplina_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            pstmt.setInt(2, disciplinaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência da nota: " + e.getMessage());
        }

        return false;
    }
}
