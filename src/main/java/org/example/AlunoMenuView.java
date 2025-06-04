package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlunoMenuView {

    private static JTextArea dadosAluno;
    private static JFrame frame;

    public static void criarMenuAluno(Aluno aluno, SecretariaAcademica secretaria) {

        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Nao");

        frame = new JFrame("Menu do Aluno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu do Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(50, 50, 150));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(titulo);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        //área de dados do aluno
        dadosAluno = new JTextArea();
        atualizarDadosAluno(aluno);

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
        JScrollPane scrollPane = new JScrollPane(dadosAluno);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        scrollPane.setBorder(null);
        painelPrincipal.add(scrollPane);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        //painel para botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(3, 1, 10, 10));
        painelBotoes.setBorder(new EmptyBorder(10, 50, 10, 50));

        JButton botaoGrade = new JButton("Ver Grade de Disciplinas");
        estilizarBotao(botaoGrade);
        botaoGrade.addActionListener(e -> AlunoService.visualizarGrade(aluno));
        painelBotoes.add(botaoGrade);

        JButton botaoNotas = new JButton("Ver Notas");
        estilizarBotao(botaoNotas);
        botaoNotas.addActionListener(e -> AlunoService.visualizarNotas(aluno));
        painelBotoes.add(botaoNotas);

        JButton botaoHistorico = new JButton("Solicitar Histórico");
        estilizarBotao(botaoHistorico);
        botaoHistorico.addActionListener(e -> AlunoService.solicitarHistorico(aluno, secretaria));
        painelBotoes.add(botaoHistorico);

        JButton botaoCancelar = new JButton("Cancelar Matrícula");
        estilizarBotao(botaoCancelar);
        botaoCancelar.addActionListener(e -> {
            cancelarMatriculaComCallback(aluno, secretaria);
        });
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes);

        frame.add(painelPrincipal, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    //dados do aluno na interface
    private static void atualizarDadosAluno(Aluno aluno) {
        if (dadosAluno != null) {
            dadosAluno.setText(
                    "Nome: " + aluno.getNome() + "\n" +
                            "Email: " + aluno.getEmail() + "\n" +
                            "Matricula: " + aluno.getMatricula().getMatricula() + "\n" +
                            "Status: " + aluno.getMatricula().getStatus()
            );
            dadosAluno.repaint();
        }
    }

    //cancelar matrícula
    private static void cancelarMatriculaComCallback(Aluno aluno, SecretariaAcademica secretaria) {
        int confirmacao = JOptionPane.showConfirmDialog(
                frame,
                "ATENÇÃO: Tem certeza que deseja cancelar sua matrícula?\n\n" +
                        "Esta ação irá:\n" +
                        "- Alterar seu status para 'EM PROCESSO DE CANCELAMENTO'\n" +
                        "- Enviar notificação para a Secretaria Acadêmica\n" +
                        "- Iniciar o processo de cancelamento\n\n" +
                        "Deseja continuar?",
                "Cancelar Matricula",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                aluno.getMatricula().setStatus("Em processo de cancelamento");

                atualizarDadosAluno(aluno);

                frame.repaint();
                frame.revalidate();


                aluno.solicitarCancelamento(secretaria);

                //enviar email
                AlunoService.enviarEmailCancelamento(aluno, secretaria);

                //mostrar confirmação
                JOptionPane.showMessageDialog(frame,
                        "Solicitacao de cancelamento processada com sucesso!\n\n" +
                                "Status alterado para: Em processo de cancelamento\n" +
                                "Notificacao enviada por email\n" +
                                "Aguarde analise da Secretaria Academica\n\n" +
                                "Voce sera notificado sobre o resultado.",
                        "Cancelamento Solicitado",
                        JOptionPane.INFORMATION_MESSAGE);

                atualizarDadosAluno(aluno);

                Timer timer = new Timer(8000, e -> {
                    frame.dispose();
                    System.exit(0);
                });
                timer.setRepeats(false);
                timer.start();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame,
                        "Erro ao processar cancelamento: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void atualizarInterface(Aluno aluno) {
        atualizarDadosAluno(aluno);
    }

    private static void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(230, 230, 250));
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}