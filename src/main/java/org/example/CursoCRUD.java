package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoCRUD {
    public static void create(Curso curso) {
        String sql = "INSERT INTO curso (nome, codigo) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, curso.getNome());
            pstmt.setString(2, curso.getCodigo());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    curso.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir curso: " + e.getMessage());
        }
    }

    public static List<Curso> readAll() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT id, nome, codigo FROM curso";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo")
                );
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
        }
        return cursos;
    }

    //verifica se o curso ja existe
    public static boolean cursoExiste(String codigo) {
        String sql = "SELECT COUNT(*) FROM curso WHERE codigo = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência do curso: " + e.getMessage());
            return false;
        }
    }

}
