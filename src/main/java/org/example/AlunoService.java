package org.example;

import javax.swing.*;

public class AlunoService {

    public static void visualizarGrade() {
        String[] disciplinas = {"Matemática", "Português", "História", "Ciências"};
        StringBuilder grade = new StringBuilder("Grade de Disciplinas:\n\n");
        for (String disciplina : disciplinas) {
            grade.append("- ").append(disciplina).append("\n");
        }
        JOptionPane.showMessageDialog(null, grade.toString(), "Grade de Disciplinas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void visualizarNotas() {
        String[] notas = {"Matemática: 8.5", "Português: 7.0", "História: 9.0", "Ciências: 6.5"};
        StringBuilder notasFormatadas = new StringBuilder("Notas:\n\n");
        for (String nota : notas) {
            notasFormatadas.append("- ").append(nota).append("\n");
        }
        JOptionPane.showMessageDialog(null, notasFormatadas.toString(), "Notas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void cancelarMatricula(Aluno aluno, SecretariaAcademica secretaria) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar sua matrícula?", "Cancelar Matrícula", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            aluno.solicitarCancelamento(secretaria);
            JOptionPane.showMessageDialog(null, "Matrícula cancelada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Fecha o programa
        }
    }
}
