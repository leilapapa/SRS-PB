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

            if (!Validador.validarEmail(email)) {
                mensagemStatus.setText("Erro: E-mail não cadastrado.");
            } else {
                String resultadoSenha = Validador.validarSenha(senha);
                if (resultadoSenha.equals("OK")) {
                    mensagemStatus.setText("");
                    JOptionPane.showMessageDialog(frame, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    Aluno aluno = new Aluno("Maria", "maria@email.com", "123", "20231234");
                    SecretariaAcademica secretaria = new SecretariaAcademica("Secretaria", "sec@email.com", "admin");

                    AlunoMenuView.criarMenuAluno(aluno, secretaria);
                } else {
                    mensagemStatus.setText(resultadoSenha);
                }
            }
        });

        frame.setVisible(true);
    }
}
