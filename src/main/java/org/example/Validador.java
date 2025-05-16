package org.example;

public class Validador {

    public static boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return email != null && email.matches(regex);
    }

    public static String validarSenha(String password) {
        if (password.isEmpty()) {
            return "Erro: a senha não pode estar vazia.";
        }
        if (password.length() < 8) {
            return "Erro: A senha precisa ter pelo menos 8 caracteres.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Erro: A senha precisa ter pelo menos uma letra maiúscula.";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Erro: A senha precisa ter pelo menos um número.";
        }
        if (!password.matches(".*[@#$%&!].*")) {
            return "Erro: A senha precisa ter pelo menos um caractere especial (@, #, $, %, &, !).";
        }
        return "OK";
    }
}
