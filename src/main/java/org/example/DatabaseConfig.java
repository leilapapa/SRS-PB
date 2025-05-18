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
                "matricula TEXT NOT NULL," +
                "status TEXT NOT NULL)";

        String sqlCurso = "CREATE TABLE IF NOT EXISTS curso (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "codigo TEXT NOT NULL UNIQUE)";

        String sqlDisciplina = "CREATE TABLE IF NOT EXISTS disciplina (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "vagas INTEGER NOT NULL," +
                "curso_id INTEGER NOT NULL," +
                "FOREIGN KEY (curso_id) REFERENCES curso(id))";

        String sqlTurma = "CREATE TABLE IF NOT EXISTS turma (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codigo_turma TEXT NOT NULL," +
                "dias_semana TEXT," + // armazenado como string serializada
                "horario TEXT," +
                "professor_nome TEXT," + // simplificando por enquanto
                "disciplina_id INTEGER," +
                "FOREIGN KEY (disciplina_id) REFERENCES disciplina(id))";

        String sqlTurmaAluno = "CREATE TABLE IF NOT EXISTS turma_aluno (" +
                "aluno_id INTEGER," +
                "turma_id INTEGER," +
                "PRIMARY KEY (aluno_id, turma_id)," +
                "FOREIGN KEY (aluno_id) REFERENCES aluno(id)," +
                "FOREIGN KEY (turma_id) REFERENCES turma(id))";

        String sqlSecretaria = "CREATE TABLE IF NOT EXISTS secretaria (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "senha TEXT NOT NULL," +
                "tipo TEXT NOT NULL)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAluno);
            stmt.execute(sqlCurso);
            stmt.execute(sqlDisciplina);
            stmt.execute(sqlTurma);
            stmt.execute(sqlTurmaAluno);
            stmt.execute(sqlSecretaria);

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
