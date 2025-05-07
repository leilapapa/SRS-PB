package org.example;

import java.util.*;

public final class Disciplina {
    private String nome;
    private int vagas;
    private List<Turma> turmas;
    private List<Disciplina> preRequisitos;

    public Disciplina(String nome, int vagas) {
        this.nome = nome;
        this.vagas = vagas;
        this.turmas = new ArrayList<>();
        this.preRequisitos = new ArrayList<>();
    }

    //getter setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public List<Disciplina> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(List<Disciplina> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    //metodo
    public List<Turma> listarTurmas() {
        return turmas;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "nome='" + nome + '\'' +
                ", vagas=" + vagas +
                ", turmas=" + (turmas != null ? turmas.size() : 0) +
                ", preRequisitos=" + (preRequisitos != null ? preRequisitos.size() : 0) +
                '}';
    }
}
