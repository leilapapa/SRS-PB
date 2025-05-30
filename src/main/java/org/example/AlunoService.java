package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlunoService {

    // credenciais
    private static final String EMAIL_REMETENTE = "leandrobm05@gmail.com";
    private static final String SENHA_APP = "wcvx agfq zfqg tsbs";

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
            grade.append("Dias: ").append(formatarDiasSemana(turma.getDiasSemana())).append("\n");
            grade.append("Horario: ").append(turma.getHorario()).append("\n");
            grade.append("Professor: ").append(
                    turma.getProfessor() != null ? turma.getProfessor().getNome() : "Nao informado"
            ).append("\n\n");
        }

        Object[] opcoes = {"Ver Grade Completa", "Enviar Materias por Email", "Salvar Grade em PDF", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "O que deseja fazer com sua grade de disciplinas?",
                "Grade de Disciplinas",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        switch (escolha) {
            case 0: // Visualizar grade completa
                JOptionPane.showMessageDialog(null, grade.toString(),
                        "Grade de Disciplinas", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 1: // Enviar matérias por email
                enviarEmailComMaterias(aluno.getEmail(), turmas);
                break;
            case 2: // Salvar grade em PDF
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Salvar Grade de Disciplinas em PDF");
                fileChooser.setSelectedFile(new java.io.File("grade_disciplinas.pdf"));
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    java.io.File fileToSave = fileChooser.getSelectedFile();
                    if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                        fileToSave = new java.io.File(fileToSave.getAbsolutePath() + ".pdf");
                    }
                    salvarGradeEmPDF(fileToSave, turmas, aluno);
                }
                break;
            default:
                break;
        }
    }

    // Método para converter Set<DiaSemana> para String
    private static String formatarDiasSemana(Object diasSemana) {
        if (diasSemana == null) {
            return "Nao informado";
        }

        if (diasSemana instanceof Set) {
            Set<?> diasSet = (Set<?>) diasSemana;
            if (diasSet.isEmpty()) {
                return "Nao informado";
            }

            return diasSet.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }

        return diasSemana.toString();
    }

    public static void enviarEmailComMaterias(String emailDestino, List<Turma> turmas) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            mensagem.setSubject("Disciplinas Matriculadas - Secretaria Acadêmica");

            // Criando conteúdo HTML para melhor formatação
            StringBuilder conteudoHTML = new StringBuilder();
            conteudoHTML.append("<html><body>");
            conteudoHTML.append("<h2>Suas Disciplinas Matriculadas</h2>");
            conteudoHTML.append("<p>Ola!</p>");
            conteudoHTML.append("<p>Segue abaixo a lista das disciplinas em que você esta matriculado(a):</p>");
            conteudoHTML.append("<ul>");

            for (Turma turma : turmas) {
                conteudoHTML.append("<li><strong>")
                        .append(turma.getDisciplina().getNome())
                        .append("</strong>");
                conteudoHTML.append("<br>Dias: ").append(formatarDiasSemana(turma.getDiasSemana()));
                conteudoHTML.append("<br>Horário: ").append(turma.getHorario());
                conteudoHTML.append("<br>Professor: ")
                        .append(turma.getProfessor() != null ? turma.getProfessor().getNome() : "Não informado");
                conteudoHTML.append("</li><br>");
            }

            conteudoHTML.append("</ul>");
            conteudoHTML.append("<p><strong>Total de disciplinas: ").append(turmas.size()).append("</strong></p>");
            conteudoHTML.append("<br><p>Atenciosamente,<br>Secretaria Acadêmica</p>");
            conteudoHTML.append("</body></html>");

            mensagem.setContent(conteudoHTML.toString(), "text/html; charset=utf-8");

            Transport.send(mensagem);

            JOptionPane.showMessageDialog(null,
                    "Email enviado com sucesso para: " + emailDestino,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao enviar o email: " + e.getMessage() +
                            "\n\nVerifique:\n- Conexao com internet\n- Configuracoes do email\n- Email de destino valido",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void salvarGradeEmPDF(java.io.File arquivo, List<Turma> turmas, Aluno aluno) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(arquivo));
            document.open();

            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("GRADE DE DISCIPLINAS", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Informações do aluno
            Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph infoAluno = new Paragraph("Aluno: " + aluno.getNome(), subtituloFont);
            infoAluno.setSpacingAfter(10);
            document.add(infoAluno);

            Paragraph matricula = new Paragraph("Matricula: " + aluno.getMatricula(), subtituloFont);
            matricula.setSpacingAfter(20);
            document.add(matricula);

            // Tabela com as disciplinas
            PdfPTable tabela = new PdfPTable(5);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10);

            // Definir larguras das colunas
            float[] largurasColunas = {15f, 30f, 20f, 15f, 20f};
            tabela.setWidths(largurasColunas);

            // Cabeçalho da tabela
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            PdfPCell header1 = new PdfPCell(new Phrase("Cod. Turma", headerFont));
            PdfPCell header2 = new PdfPCell(new Phrase("Disciplina", headerFont));
            PdfPCell header3 = new PdfPCell(new Phrase("Dias", headerFont));
            PdfPCell header4 = new PdfPCell(new Phrase("Horario", headerFont));
            PdfPCell header5 = new PdfPCell(new Phrase("Professor", headerFont));

            header1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header5.setBackgroundColor(BaseColor.LIGHT_GRAY);

            header1.setPadding(8);
            header2.setPadding(8);
            header3.setPadding(8);
            header4.setPadding(8);
            header5.setPadding(8);

            tabela.addCell(header1);
            tabela.addCell(header2);
            tabela.addCell(header3);
            tabela.addCell(header4);
            tabela.addCell(header5);

            // Dados das turmas
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 9);
            for (Turma turma : turmas) {
                PdfPCell cellCodigo = new PdfPCell(new Phrase(turma.getCodigoTurma(), cellFont));
                PdfPCell cellDisciplina = new PdfPCell(new Phrase(turma.getDisciplina().getNome(), cellFont));
                PdfPCell cellDias = new PdfPCell(new Phrase(formatarDiasSemana(turma.getDiasSemana()), cellFont));
                PdfPCell cellHorario = new PdfPCell(new Phrase(turma.getHorario(), cellFont));
                PdfPCell cellProfessor = new PdfPCell(new Phrase(
                        turma.getProfessor() != null ? turma.getProfessor().getNome() : "Nao informado", cellFont));

                // Adicionar padding às células
                cellCodigo.setPadding(5);
                cellDisciplina.setPadding(5);
                cellDias.setPadding(5);
                cellHorario.setPadding(5);
                cellProfessor.setPadding(5);

                tabela.addCell(cellCodigo);
                tabela.addCell(cellDisciplina);
                tabela.addCell(cellDias);
                tabela.addCell(cellHorario);
                tabela.addCell(cellProfessor);
            }

            document.add(tabela);

            // Rodapé
            Paragraph rodape = new Paragraph("\nTotal de disciplinas: " + turmas.size(), subtituloFont);
            rodape.setSpacingBefore(20);
            document.add(rodape);

            Paragraph dataGeracao = new Paragraph("Documento gerado em: " + new java.util.Date().toString());
            dataGeracao.setAlignment(Element.ALIGN_RIGHT);
            dataGeracao.setSpacingBefore(30);
            document.add(dataGeracao);

            document.close();

            JOptionPane.showMessageDialog(null,
                    "Grade salva em PDF com sucesso!\nArquivo: " + arquivo.getAbsolutePath(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao salvar PDF: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void visualizarNotas(Aluno aluno) {
        // Buscar notas reais do banco de dados
        List<Nota> notas = NotaCRUD.getNotasDoAluno(aluno.getId());
        if (notas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Voce ainda nao possui notas cadastradas.",
                    "Notas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder notasFormatadas = new StringBuilder("Notas por disciplina:\n\n");
        for (Nota nota : notas) {
            notasFormatadas.append("Disciplina: ").append(nota.getDisciplina().getNome()).append("\n")
                    .append("Nota: ").append(nota.getValor()).append("\n")
                    .append("Situacao: ").append(nota.getSituacao().toString()).append("\n\n");
        }

        Object[] opcoes = {"Enviar por email", "Mostrar grafico", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                notasFormatadas.toString(),
                "Notas",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == JOptionPane.YES_OPTION) {
            enviarEmailComNotasReais(aluno.getEmail(), notas);
        } else if (escolha == JOptionPane.NO_OPTION) {
            mostrarGraficoNotasReais(notas);
        }
    }

    private static void mostrarGraficoNotasReais(List<Nota> notas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Nota nota : notas) {
            dataset.addValue(nota.getValor(), "Notas", nota.getDisciplina().getNome());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Notas",
                "Disciplinas",
                "Notas",
                dataset
        );

        chart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelWidthRatio(0.8f);
        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(
                org.jfree.chart.axis.CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        // Configurar tamanho preferido do painel do gráfico
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 300));

        JFrame chartFrame = new JFrame("Grafico de Notas");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Definir tamanho da janela - mais larga e menos alta
        chartFrame.setSize(750, 350);

        // Centralizar na tela
        chartFrame.setLocationRelativeTo(null);

        // Impedir redimensionamento se desejar
        chartFrame.setResizable(true);

        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    public static void enviarEmailComNotasReais(String emailDestino, List<Nota> notas) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            mensagem.setSubject("Notas - Secretaria Acadêmica");

            //conteúdo HTML para melhor formatação
            StringBuilder conteudoHTML = new StringBuilder();
            conteudoHTML.append("<html><body>");
            conteudoHTML.append("<h2>Suas Notas</h2>");
            conteudoHTML.append("<p>Ola!</p>");
            conteudoHTML.append("<p>Segue abaixo suas notas acadêmicas:</p>");
            conteudoHTML.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
            conteudoHTML.append("<tr style='background-color: #f2f2f2;'>");
            conteudoHTML.append("<th style='padding: 8px;'>Disciplina</th>");
            conteudoHTML.append("<th style='padding: 8px;'>Nota</th>");
            conteudoHTML.append("<th style='padding: 8px;'>Situação</th>");
            conteudoHTML.append("</tr>");

            double somaNotas = 0;
            int totalNotas = 0;

            for (Nota nota : notas) {
                String corSituacao = "";
                String situacao = nota.getSituacao().toString();
                if ("APROVADO".equalsIgnoreCase(situacao)) {
                    corSituacao = "color: green;";
                } else if ("REPROVADO".equalsIgnoreCase(situacao)) {
                    corSituacao = "color: red;";
                } else {
                    corSituacao = "color: orange;";
                }

                conteudoHTML.append("<tr>");
                conteudoHTML.append("<td style='padding: 8px;'>").append(nota.getDisciplina().getNome()).append("</td>");
                conteudoHTML.append("<td style='padding: 8px; text-align: center;'>").append(nota.getValor()).append("</td>");
                conteudoHTML.append("<td style='padding: 8px; text-align: center; ").append(corSituacao).append("'>")
                        .append(nota.getSituacao().toString()).append("</td>");
                conteudoHTML.append("</tr>");

                somaNotas += nota.getValor();
                totalNotas++;
            }

            conteudoHTML.append("</table>");

            if (totalNotas > 0) {
                double media = somaNotas / totalNotas;
                conteudoHTML.append("<br><p><strong>CR: ")
                        .append(String.format("%.2f", media)).append("</strong></p>");
            }

            conteudoHTML.append("<br><p>Atenciosamente,<br>Secretaria Acadêmica</p>");
            conteudoHTML.append("</body></html>");

            mensagem.setContent(conteudoHTML.toString(), "text/html; charset=utf-8");

            Transport.send(mensagem);

            JOptionPane.showMessageDialog(null,
                    "Email enviado com sucesso para: " + emailDestino,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao enviar o email: " + e.getMessage() +
                            "\n\nVerifique:\n- Conexao com internet\n- Configuracoes do email\n- Email de destino valido",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void solicitarHistorico(Aluno aluno, SecretariaAcademica secretaria) {
        JOptionPane.showMessageDialog(null,
                "Solicitação de histórico enviada para a Secretaria.\n\n"
                        + "O documento será gerado e enviado por email ao aluno em breve.",
                "Solicitação de Histórico",
                JOptionPane.INFORMATION_MESSAGE);

        try {
            secretaria.processarSolicitacaoHistorico(aluno);
            enviarEmailHistorico(aluno);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao processar o histórico: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void enviarEmailHistorico(Aluno aluno) {
        List<Nota> notas = NotaCRUD.getNotasDoAluno(aluno.getId());
        List<Turma> turmasEmAndamento = aluno.getTurmas();

        if (notas.isEmpty() && (turmasEmAndamento == null || turmasEmAndamento.isEmpty())) {
            JOptionPane.showMessageDialog(null,
                    "O aluno ainda não possui dados suficientes para compor o histórico.",
                    "Sem Dados",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String emailDestino = aluno.getEmail();
        if (emailDestino == null || emailDestino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Email do aluno não informado.",
                    "Erro de Email",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            mensagem.setSubject("Histórico - " + aluno.getNome());

            StringBuilder conteudoHTML = new StringBuilder();
            conteudoHTML.append("<html><body>");
            conteudoHTML.append("<h2 style='color: #2e7d32;'>HISTÓRICO</h2>");
            conteudoHTML.append("<hr>");
            conteudoHTML.append("<p><strong>Nome:</strong> ").append(aluno.getNome()).append("</p>");
            conteudoHTML.append("<p><strong>Matrícula:</strong> ").append(aluno.getMatricula().getMatricula()).append("</p>");
            conteudoHTML.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
            conteudoHTML.append("<tr style='background-color: #f2f2f2;'>");
            conteudoHTML.append("<th style='padding: 8px;'>Disciplina</th>");
            conteudoHTML.append("<th style='padding: 8px;'>Nota</th>");
            conteudoHTML.append("<th style='padding: 8px;'>Situação</th>");
            conteudoHTML.append("</tr>");

            for (Nota nota : notas) {
                conteudoHTML.append("<tr>");
                conteudoHTML.append("<td style='padding: 8px;'>").append(nota.getDisciplina().getNome()).append("</td>");
                conteudoHTML.append("<td style='padding: 8px; text-align: center;'>").append(nota.getValor()).append("</td>");
                conteudoHTML.append("<td style='padding: 8px; text-align: center;'>").append(nota.getSituacao().toString()).append("</td>");
                conteudoHTML.append("</tr>");
            }

            for (Turma turma : turmasEmAndamento) {
                Disciplina disciplina = turma.getDisciplina();
                conteudoHTML.append("<tr>");
                conteudoHTML.append("<td style='padding: 8px;'>").append(disciplina.getNome()).append("</td>");
                conteudoHTML.append("<td style='padding: 8px; text-align: center;'>-</td>");
                conteudoHTML.append("<td style='padding: 8px; text-align: center;'>Em andamento</td>");
                conteudoHTML.append("</tr>");
            }

            conteudoHTML.append("</table>");
            conteudoHTML.append("<br><p style='color: #666;'>Documento gerado automaticamente pela Secretaria Acadêmica</p>");
            conteudoHTML.append("</body></html>");

            mensagem.setContent(conteudoHTML.toString(), "text/html; charset=utf-8");
            Transport.send(mensagem);

            JOptionPane.showMessageDialog(null,
                    "Histórico enviado com sucesso para: " + emailDestino,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao enviar o email do histórico: " + e.getMessage(),
                    "Erro de Email",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

public static void enviarEmailCancelamento(Aluno aluno, SecretariaAcademica secretaria) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE));

            // Enviar para a secretaria -
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(secretaria.getEmail()));

            // Cópia para o aluno
            if (aluno.getEmail() != null && !aluno.getEmail().trim().isEmpty()) {
                mensagem.setRecipients(Message.RecipientType.CC, InternetAddress.parse(aluno.getEmail()));
            }

            mensagem.setSubject("Solicitação de Cancelamento de Matrícula - " + aluno.getNome());

            // Criando conteúdo HTML
            StringBuilder conteudoHTML = new StringBuilder();
            conteudoHTML.append("<html><body>");
            conteudoHTML.append("<h2 style='color: #d32f2f;'>SOLICITAÇÃO DE CANCELAMENTO DE MATRÍCULA</h2>");
            conteudoHTML.append("<hr>");

            conteudoHTML.append("<h3>Dados do Aluno:</h3>");
            conteudoHTML.append("<p><strong>Nome:</strong> ").append(aluno.getNome()).append("</p>");
            conteudoHTML.append("<p><strong>Matrícula:</strong> ").append(aluno.getMatricula()).append("</p>");
            if (aluno.getEmail() != null) {
                conteudoHTML.append("<p><strong>Email:</strong> ").append(aluno.getEmail()).append("</p>");
            }

            conteudoHTML.append("<h3>Informaçõess da Solicitação:</h3>");
            conteudoHTML.append("<p><strong>Data/Hora:</strong> ")
                    .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .append("</p>");
            conteudoHTML.append("<p><strong>Status Atual:</strong> <span style='color: #ff9800;'>EM PROCESSO DE CANCELAMENTO</span></p>");

            // Listar disciplinas matriculadas
            if (aluno.getTurmas() != null && !aluno.getTurmas().isEmpty()) {
                conteudoHTML.append("<h3>Disciplinas Matriculadas:</h3>");
                conteudoHTML.append("<ul>");
                for (Turma turma : aluno.getTurmas()) {
                    conteudoHTML.append("<li>")
                            .append(turma.getDisciplina().getNome())
                            .append(" (").append(turma.getCodigoTurma()).append(")")
                            .append("</li>");
                }
                conteudoHTML.append("</ul>");
            }

            conteudoHTML.append("<hr>");
            conteudoHTML.append("<p><strong>AÇÃO NECESSÁRIA:</strong></p>");
            conteudoHTML.append("<p>Esta solicitação requer análise e aprovação da Secretaria Acadêmica.</p>");
            conteudoHTML.append("<p>O aluno será notificado sobre o resultado do processo.</p>");

            conteudoHTML.append("<br><p style='color: #666;'>Atenciosamente,<br>");
            conteudoHTML.append("Secretaria Acadêmica</p>");
            conteudoHTML.append("</body></html>");

            mensagem.setContent(conteudoHTML.toString(), "text/html; charset=utf-8");

            Transport.send(mensagem);

            JOptionPane.showMessageDialog(null,
                    "Notificação de cancelamento enviada com sucesso!\n" +
                            "Secretaria e aluno foram notificados por email.",
                    "Email Enviado",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao enviar notificação por email: " + e.getMessage() +
                            "\n\nO cancelamento foi processado, mas a notificação falhou.",
                    "Erro no Email",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void cancelarMatricula(Aluno aluno, SecretariaAcademica secretaria) {
        int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "ATENÇÃO: Tem certeza que deseja cancelar sua matricula?\n\n" +
                        "Esta ação irá:\n" +
                        "- Alterar seu status para 'EM PROCESSO DE CANCELAMENTO'\n" +
                        "- Enviar notificação para a Secretaria Acadêmica\n" +
                        "- Iniciar o processo de cancelamento\n\n" +
                        "Deseja continuar?",
                "Cancelar Matrícula",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                aluno.getMatricula().setStatus("EM PROCESSO DE CANCELAMENTO");

                aluno.solicitarCancelamento(secretaria);

                enviarEmailCancelamento(aluno, secretaria);

                JOptionPane.showMessageDialog(null,
                        "Solicitação de cancelamento processada com sucesso!\n\n" +
                                "Status alterado para: EM PROCESSO DE CANCELAMENTO\n" +
                                "Notificação enviada por email\n" +
                                "Aguarde análise da Secretaria Acadêmica\n\n" +
                                "Você receberá uma atualização em breve.",
                        "Cancelamento Solicitado",
                        JOptionPane.INFORMATION_MESSAGE);

                Timer timer = new Timer(8000, e -> System.exit(0));
                timer.setRepeats(false);
                timer.start();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Erro ao processar cancelamento: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}