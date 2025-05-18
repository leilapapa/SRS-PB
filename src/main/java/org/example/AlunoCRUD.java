package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoCRUD {

    //verifica se o usuario inseriu as credenciais corretas de acordo com o banco
    public static Aluno autenticar(String email, String senha) {
        String sql = "SELECT * FROM aluno WHERE email = ? AND senha = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("matricula"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar aluno: " + e.getMessage());
        }

        return null;
    }

    public static void create(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, email, senha, matricula, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getSenha());
            pstmt.setString(4, aluno.getMatricula().getMatricula());
            pstmt.setString(5, aluno.getMatricula().getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        aluno.getMatricula().setId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar aluno: " + e.getMessage());
        }
    }

    public static void update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, email = ?, senha = ?, matricula = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getSenha());
            pstmt.setString(4, aluno.getMatricula().getMatricula());
            pstmt.setString(5, aluno.getMatricula().getStatus());
            pstmt.setInt(6, aluno.getMatricula().getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    public static List<Aluno> readAll() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                alunos.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("matricula"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public static Aluno readById(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("matricula"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
        }
        return null;
    }
}