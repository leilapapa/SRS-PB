package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaCRUD {
    public static void create(Disciplina disciplina) {
        String sql = "INSERT INTO disciplina (nome, vagas, curso_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, disciplina.getNome());
            pstmt.setInt(2, disciplina.getVagas());
            pstmt.setInt(3, disciplina.getCurso().getId());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    disciplina.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir disciplina: " + e.getMessage());
        }
    }

    public static List<Disciplina> readAll() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT d.id, d.nome, d.vagas, c.id as curso_id, c.nome as curso_nome, c.codigo as curso_codigo " +
                "FROM disciplina d JOIN curso c ON d.curso_id = c.id";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("curso_id"),
                        rs.getString("curso_nome"),
                        rs.getString("curso_codigo")
                );
                Disciplina disciplina = new Disciplina(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("vagas"),
                        curso
                );
                disciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar disciplinas: " + e.getMessage());
        }
        return disciplinas;
    }

    //verifica se a disciplina já existe
    public static boolean disciplinaExiste(String nome, int cursoId) {
        String sql = "SELECT COUNT(*) FROM disciplina WHERE nome = ? AND curso_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, cursoId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência da disciplina: " + e.getMessage());
            return false;
        }
    }

}
