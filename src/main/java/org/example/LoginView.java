package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView {

    public static void criarJanelaDeLogin() {
        JFrame frame = new JFrame("Sistema de Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelPrincipal.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("SRS - INFNET", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(titulo);

        JPanel painelEmail = new JPanel(new BorderLayout());
        JLabel labelEmail = new JLabel("E-mail:");
        JTextField campoEmail = new JTextField();
        painelEmail.add(labelEmail, BorderLayout.NORTH);
        painelEmail.add(campoEmail, BorderLayout.CENTER);
        painelPrincipal.add(painelEmail);

        JPanel painelSenha = new JPanel(new BorderLayout());
        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();
        painelSenha.add(labelSenha, BorderLayout.NORTH);
        painelSenha.add(campoSenha, BorderLayout.CENTER);
        painelPrincipal.add(painelSenha);

        JLabel mensagemStatus = new JLabel("", JLabel.CENTER);
        mensagemStatus.setForeground(Color.RED);
        painelPrincipal.add(mensagemStatus);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBackground(new Color(0, 120, 215));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        painelPrincipal.add(botaoEntrar);

        frame.add(painelPrincipal, BorderLayout.CENTER);

        botaoEntrar.addActionListener(e -> {
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword()).trim();

            //busca o aluno na lista carregada do CSV
            Aluno alunoLogado = RepositorioDeAlunos.alunos.stream()
                    .filter(a -> a.getEmail().equals(email) && a.getSenha().equals(senha))
                    .findFirst()
                    .orElse(null);


            if (alunoLogado != null) {
                mensagemStatus.setText("");
                JOptionPane.showMessageDialog(frame, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                SecretariaAcademica secretaria = new SecretariaAcademica("Secretaria", "sec@email.com", "admin");
                AlunoMenuView.criarMenuAluno(alunoLogado, secretaria);
            } else {
                mensagemStatus.setText("Erro: e-mail ou senha inválidos.");
            }
        });
        frame.setVisible(true);
    }
}
