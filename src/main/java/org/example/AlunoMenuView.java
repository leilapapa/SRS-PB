package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlunoMenuView {

    public static void criarMenuAluno() {
        JFrame frame = new JFrame("Menu do Aluno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelPrincipal.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titulo = new JLabel("Menu do Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(titulo);

        JButton botaoGrade = new JButton("Visualizar Grade de Disciplinas");
        botaoGrade.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoGrade.addActionListener(e -> AlunoService.visualizarGrade());
        painelPrincipal.add(botaoGrade);

        JButton botaoNotas = new JButton("Visualizar Notas");
        botaoNotas.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoNotas.addActionListener(e -> AlunoService.visualizarNotas());
        painelPrincipal.add(botaoNotas);

        JButton botaoCancelar = new JButton("Cancelar Matrícula");
        botaoCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoCancelar.addActionListener(e -> AlunoService.cancelarMatricula());
        painelPrincipal.add(botaoCancelar);

        frame.add(painelPrincipal, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
