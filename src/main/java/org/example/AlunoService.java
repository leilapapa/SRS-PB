package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import javax.swing.*;
import java.util.List;
import java.util.Properties;

public class AlunoService {

    //credenciais
    private static final String EMAIL_REMETENTE = "leandrobm05@gmail.com";
    private static final String SENHA_APP = "wcvx agfq zfqg tsbs\n";

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

        Object[] opcoes = {"Visualizar", "Salvar em Arquivo", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "O que deseja fazer com sua grade de disciplinas?",
                "Grade de Disciplinas",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, grade.toString(),
                    "Grade de Disciplinas", JOptionPane.INFORMATION_MESSAGE);
        } else if (escolha == JOptionPane.NO_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar Grade de Disciplinas");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.FileWriter writer = new java.io.FileWriter(fileToSave)) {
                    writer.write(grade.toString());
                    JOptionPane.showMessageDialog(null, "Grade salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void visualizarNotas() {
        String[] disciplinas = {"Matemática", "Português", "História", "Ciências"};
        double[] notas = {8.5, 7.0, 9.0, 6.5};

        StringBuilder textoNotas = new StringBuilder("Suas notas:\n\n");
        for (int i = 0; i < disciplinas.length; i++) {
            textoNotas.append(disciplinas[i]).append(": ").append(notas[i]).append("\n");
        }

        Object[] opcoes = {"Enviar por e-mail", "Mostrar gráfico", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                textoNotas.toString(),
                "Notas",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == JOptionPane.YES_OPTION) {
            String email = JOptionPane.showInputDialog(null, "Digite o e-mail para envio:");
            if (email != null && !email.trim().isEmpty()) {
                enviarEmailComNotas(email, disciplinas, notas);
            }
        } else if (escolha == JOptionPane.NO_OPTION) {
            mostrarGraficoNotas(disciplinas, notas);
        }
    }

    private static void mostrarGraficoNotas(String[] disciplinas, double[] notas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < disciplinas.length; i++) {
            dataset.addValue(notas[i], "Notas", disciplinas[i]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Notas",
                "Disciplinas",
                "Notas",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame("Gráfico de Notas");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(600, 400);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    public static void enviarEmailComNotas(String emailDestino, String[] disciplinas, double[] notas) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        //  autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });

        try {
            // Criar mensagem
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            mensagem.setSubject("Notas Acadêmicas - Sistema Escolar");

            StringBuilder conteudo = new StringBuilder();
            conteudo.append("Olá!\n\n");
            conteudo.append("Segue abaixo suas notas acadêmicas:\n\n");

            double soma = 0;
            for (int i = 0; i < disciplinas.length; i++) {
                conteudo.append("📚 ").append(disciplinas[i]).append(": ").append(notas[i]).append("\n");
                soma += notas[i];
            }

            double media = soma / disciplinas.length;
            conteudo.append("\n📊 Média Geral: ").append(String.format("%.2f", media)).append("\n\n");
            conteudo.append("Atenciosamente,\n");
            conteudo.append("Sistema Acadêmico");

            mensagem.setText(conteudo.toString());

            // Enviar email
            Transport.send(mensagem);

            JOptionPane.showMessageDialog(null,
                    "✅ E-mail enviado com sucesso para: " + emailDestino,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Erro ao enviar o e-mail: " + e.getMessage() +
                            "\n\nVerifique:\n- Conexão com internet\n- Configurações do email\n- Email de destino válido",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void cancelarMatricula(Aluno aluno, SecretariaAcademica secretaria) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar sua matrícula?", "Cancelar Matrícula", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            aluno.solicitarCancelamento(secretaria);
            JOptionPane.showMessageDialog(null, "Matrícula cancelada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}