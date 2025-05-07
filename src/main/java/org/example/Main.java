package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String caminhoEntrada = "src/main/DadosCSV/alunos.csv";
        String caminhoSaida = "src/main/DadosCSV/alunos_output.csv";
        List<Aluno> alunos = new ArrayList<>();

        //ler CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoEntrada))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] partes = linha.split(",");
                if (partes.length >= 3) {
                    String matricula = partes[0].trim();
                    String nome = partes[1].trim();
                    String email = partes[2].trim();
                    Aluno aluno = new Aluno(nome, email, "1234", matricula);
                    alunos.add(aluno);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        //atualiza csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoSaida))) {
            writer.write("matricula,nome,email\n");
            for (Aluno aluno : alunos) {
                writer.write(aluno.toString() + "\n");
            }
            System.out.println("Arquivo atualizado com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }
}