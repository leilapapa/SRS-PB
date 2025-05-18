// AlunoMenuView.java
package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlunoMenuView {

    public static void criarMenuAluno(Aluno aluno, SecretariaAcademica secretaria) {

        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");

        JFrame frame = new JFrame("Menu do Aluno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu do Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(titulo);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel dadosAluno = new JLabel("Nome: " + aluno.getNome() +
                " | Email: " + aluno.getEmail() +
                " | Matrícula: " + aluno.getMatricula().getMatricula() +
                " | Status: " + aluno.getMatricula().getStatus(), JLabel.CENTER);
        dadosAluno.setFont(new Font("Arial", Font.PLAIN, 14));
        dadosAluno.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(dadosAluno);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton botaoGrade = new JButton("Visualizar Grade de Disciplinas");
        botaoGrade.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoGrade.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoGrade.addActionListener(e -> AlunoService.visualizarGrade());
        painelPrincipal.add(botaoGrade);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton botaoNotas = new JButton("Visualizar Notas");
        botaoNotas.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoNotas.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoNotas.addActionListener(e -> AlunoService.visualizarNotas());
        painelPrincipal.add(botaoNotas);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton botaoCancelar = new JButton("Cancelar Matrícula");
        botaoCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCancelar.addActionListener(e -> AlunoService.cancelarMatricula(aluno, secretaria));
        painelPrincipal.add(botaoCancelar);

        frame.add(painelPrincipal, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
