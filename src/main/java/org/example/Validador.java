package org.example;

public class Validador {

    public static String validarLogin(String email, String senha) {
        Aluno aluno = RepositorioDeAlunos.alunos.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (aluno == null) {
            return "Erro: usuário não cadastrado.";
        }

        if (!aluno.getSenha().equals(senha)) {
            return "Erro: senha incorreta.";
        }

        return "OK";
    }
}
