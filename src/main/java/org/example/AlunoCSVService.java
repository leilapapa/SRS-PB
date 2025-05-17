package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AlunoCSVService {

    //lê os dados do csv  armazena na classe RepositorioDeAlunos.

    public static boolean carregarAlunosCSV(String caminhoEntrada) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoEntrada))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String matricula = partes[0].trim();
                    String nome = partes[1].trim();
                    String email = partes[2].trim();
                    String senha = partes[3].trim();

                    Aluno aluno = new Aluno(nome, email, senha, matricula, "ativa");
                    RepositorioDeAlunos.alunos.add(aluno);
                }
            }
            return true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao ler o arquivo de alunos:\n" + e.getMessage(),
                    "Erro de leitura",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return false;
        }
    }
}
