package org.example;

import java.util.*;

public final class Curso {
    private String nome;
    private String codigo;
    private List<Disciplina> disciplinas;

    public Curso(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.disciplinas = new ArrayList<>();
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}