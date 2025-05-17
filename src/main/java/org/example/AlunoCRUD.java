package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoCRUD {
    // Método para adicionar um novo aluno
    public static void create(Aluno aluno) {
        String sql = "INSERT INTO aluno(nome, email, senha, data_matricula, status) VALUES(?,?,?,?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getSenha());
            pstmt.setString(4, aluno.getMatricula().getDataMatricula());
            pstmt.setString(5, aluno.getMatricula().getStatus());

            pstmt.executeUpdate();

            // Obter o ID gerado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.getMatricula().setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
        }
    }

    // Método para buscar todos os alunos
    public static List<Aluno> readAll() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("data_matricula"),
                        rs.getString("status")
                );
                aluno.getMatricula().setId(rs.getInt("id"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos: " + e.getMessage());
        }

        return alunos;
    }

    // Método para buscar um aluno por ID
    public static Aluno readById(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        Aluno aluno = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("data_matricula"),
                        rs.getString("status")
                );
                aluno.getMatricula().setId(rs.getInt("id"));
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
        }

        return aluno;
    }

    // Método para atualizar um aluno
    public static void update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, email = ?, senha = ?, data_matricula = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getSenha());
            pstmt.setString(4, aluno.getMatricula().getDataMatricula());
            pstmt.setString(5, aluno.getMatricula().getStatus());
            pstmt.setInt(6, aluno.getMatricula().getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    // Método para deletar um aluno
    public static void delete(int id) {
        // Primeiro deletar as relações com turmas
        String sqlTurmaAluno = "DELETE FROM turma_aluno WHERE aluno_id = ?";
        String sqlAluno = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            // Desativar o auto-commit para transação
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtTurma = conn.prepareStatement(sqlTurmaAluno);
                 PreparedStatement pstmtAluno = conn.prepareStatement(sqlAluno)) {

                // Remover relações com turmas
                pstmtTurma.setInt(1, id);
                pstmtTurma.executeUpdate();

                // Remover o aluno
                pstmtAluno.setInt(1, id);
                pstmtAluno.executeUpdate();

                // Commit da transação
                conn.commit();
            } catch (SQLException e) {
                // Rollback em caso de erro
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
        }
    }

    // Método para matricular aluno em uma turma
    public static void matricularEmTurma(int alunoId, int turmaId) {
        String sql = "INSERT INTO turma_aluno(aluno_id, turma_id) VALUES(?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            pstmt.setInt(2, turmaId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao matricular aluno na turma: " + e.getMessage());
        }
    }

    // Método para cancelar matrícula em uma turma
    public static void cancelarMatriculaTurma(int alunoId, int turmaId) {
        String sql = "DELETE FROM turma_aluno WHERE aluno_id = ? AND turma_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            pstmt.setInt(2, turmaId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar matrícula: " + e.getMessage());
        }
    }
}