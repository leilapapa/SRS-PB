package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:sqlite:escola.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelas() {
        String sqlAluno = "CREATE TABLE IF NOT EXISTS aluno (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "senha TEXT NOT NULL," +
                "data_matricula TEXT NOT NULL," +
                "status TEXT NOT NULL)";

        String sqlTurmaAluno = "CREATE TABLE IF NOT EXISTS turma_aluno (" +
                "aluno_id INTEGER," +
                "turma_id INTEGER," +
                "PRIMARY KEY (aluno_id, turma_id)," +
                "FOREIGN KEY (aluno_id) REFERENCES aluno(id)," +
                "FOREIGN KEY (turma_id) REFERENCES turma(id))";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAluno);
            stmt.execute(sqlTurmaAluno);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
