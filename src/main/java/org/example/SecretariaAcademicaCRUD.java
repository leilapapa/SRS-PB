package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecretariaAcademicaCRUD {

    public static void create(SecretariaAcademica secretaria) {
        String sql = "INSERT INTO secretaria (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, secretaria.getNome());
            pstmt.setString(2, secretaria.getEmail());
            pstmt.setString(3, secretaria.getSenha());
            pstmt.setString(4, secretaria.getTipo().name());

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        secretaria.setId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar secretaria: " + e.getMessage());
        }
    }

    public static SecretariaAcademica autenticar(String email, String senha) {
        String sql = "SELECT * FROM secretaria WHERE email = ? AND senha = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new SecretariaAcademica(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar secretaria: " + e.getMessage());
        }

        return null;
    }

    public static void listarSecretarias() {
        String sql = "SELECT * FROM secretaria";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("📋 Secretarias cadastradas no banco:");
            while (rs.next()) {
                System.out.println("- ID: " + rs.getInt("id") +
                        ", Nome: " + rs.getString("nome") +
                        ", Email: " + rs.getString("email") +
                        ", Senha: " + rs.getString("senha") +
                        ", Tipo: " + rs.getString("tipo"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar secretarias: " + e.getMessage());
        }
    }

}