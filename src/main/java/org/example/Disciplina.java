package org.example;

import java.util.*;

public final class Disciplina {
    private int id;
    private Curso curso;
    private String nome;
    private int vagas;
    private List<Turma> turmas;
    private List<Disciplina> preRequisitos;

    public Disciplina(String nome, int vagas, Curso curso) {
        this.nome = nome;
        this.vagas = vagas;
        this.curso = curso;
        this.turmas = new ArrayList<>();
        this.preRequisitos = new ArrayList<>();
    }

    //donstrutor adicional para carregar do banco de dados
    public Disciplina(int id, String nome, int vagas, Curso curso) {
        this.id = id;
        this.nome = nome;
        this.vagas = vagas;
        this.curso = curso;
        this.turmas = new ArrayList<>();
        this.preRequisitos = new ArrayList<>();
    }

    //getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

    public List<Turma> listarTurmas() {
        return turmas;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", vagas=" + vagas +
                ", curso=" + (curso != null ? curso.getNome() : "null") +
                ", turmas=" + (turmas != null ? turmas.size() : 0) +
                ", preRequisitos=" + (preRequisitos != null ? preRequisitos.size() : 0) +
                '}';
    }
}