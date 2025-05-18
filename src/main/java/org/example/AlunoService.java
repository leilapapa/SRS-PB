package org.example;

import javax.swing.*;
import java.util.List;

public class AlunoService {

    public static void visualizarGrade(Aluno aluno) {
        List<Turma> turmas = aluno.getTurmas();
        if (turmas == null || turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você não está matriculado em nenhuma turma.",
                    "Grade de Disciplinas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder grade = new StringBuilder("Grade de Disciplinas:\n\n");
        for (Turma turma : turmas) {
            grade.append("Turma: ").append(turma.getCodigoTurma()).append("\n");
            grade.append("Disciplina: ").append(turma.getDisciplina().getNome()).append("\n");
            grade.append("Dias: ").append(turma.getDiasSemana()).append("\n");
            grade.append("Horário: ").append(turma.getHorario()).append("\n");
            grade.append("Professor: ").append(
                    turma.getProfessor() != null ? turma.getProfessor().getNome() : "N/A"
            ).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, grade.toString(),
                "Grade de Disciplinas", JOptionPane.INFORMATION_MESSAGE);
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
