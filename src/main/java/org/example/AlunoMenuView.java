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
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu do Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(50, 50, 150)); // Azul escuro
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(titulo);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextArea dadosAluno = new JTextArea(
                "Nome: " + aluno.getNome() + "\n" +
                        "Email: " + aluno.getEmail() + "\n" +
                        "Matrícula: " + aluno.getMatricula().getMatricula() + "\n" +
                        "Status: " + aluno.getMatricula().getStatus()
        );
        dadosAluno.setFont(new Font("Arial", Font.PLAIN, 16));
        dadosAluno.setEditable(false);
        dadosAluno.setLineWrap(true);
        dadosAluno.setWrapStyleWord(true);
        dadosAluno.setBackground(painelPrincipal.getBackground());
        dadosAluno.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        dadosAluno.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(dadosAluno);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Painel para botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(3, 1, 10, 10));
        painelBotoes.setBorder(new EmptyBorder(10, 50, 10, 50));

        JButton botaoGrade = new JButton("Visualizar Grade de Disciplinas");
        estilizarBotao(botaoGrade);
        botaoGrade.addActionListener(e -> AlunoService.visualizarGrade(aluno));
        painelBotoes.add(botaoGrade);

        JButton botaoNotas = new JButton("Visualizar Notas");
        estilizarBotao(botaoNotas);
        botaoNotas.addActionListener(e -> AlunoService.visualizarNotas());
        painelBotoes.add(botaoNotas);

        JButton botaoCancelar = new JButton("Cancelar Matrícula");
        estilizarBotao(botaoCancelar);
        botaoCancelar.addActionListener(e -> AlunoService.cancelarMatricula(aluno, secretaria));
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes);

        frame.add(painelPrincipal, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(230, 230, 250)); // Lavanda claro
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}