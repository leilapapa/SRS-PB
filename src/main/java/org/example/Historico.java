package org.example;

import java.util.*;

public class Historico {
    private Aluno aluno;
    private Map<Disciplina, Nota> disciplinasNotas;

    public Historico(Aluno aluno) {
        this.aluno = aluno;
        this.disciplinasNotas = new HashMap<>();
    }

    //getter setter
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Map<Disciplina, Nota> getDisciplinasNotas() {
        return disciplinasNotas;
    }

    public void setDisciplinasNotas(Map<Disciplina, Nota> disciplinasNotas) {
        this.disciplinasNotas = disciplinasNotas;
    }

    //metodo
    public void gerarPDF() {
        throw new UnsupportedOperationException("A Função gerarPDF() ainda não foi implementada.");
    }

    public void adicionarNota(Disciplina disciplina, Nota nota) {
        disciplinasNotas.put(disciplina, nota);
    }

    @Override
    public String toString() {
        return "Historico{" +
                "aluno='" + (aluno != null ? aluno.getNome() : "null") + '\'' +
                ", notas=" + (disciplinasNotas != null ? disciplinasNotas.size() : 0) +
                '}';
    }
}
