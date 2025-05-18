package org.example;

import java.util.*;

public class Turma {
    private int id;
    private String codigoTurma;
    private Set<DiaSemana> diasSemana;
    private String horario;
    private Professor professor;
    private List<Aluno> alunos;
    private Disciplina disciplina;

    public Turma(String codigoTurma, Set<DiaSemana> diasSemana, String horario, Professor professor) {
        this.codigoTurma = codigoTurma;
        this.diasSemana = diasSemana;
        this.horario = horario;
        this.professor = professor;
        this.alunos = new ArrayList<>();
    }

    //Construtor adicional para carregar do banco de dados
    public Turma(int id, String codigoTurma, Set<DiaSemana> diasSemana, String horario, Professor professor, Disciplina disciplina) {
        this.id = id;
        this.codigoTurma = codigoTurma;
        this.diasSemana = diasSemana;
        this.horario = horario;
        this.professor = professor;
        this.disciplina = disciplina;
        this.alunos = new ArrayList<>();
    }

    //getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Set<DiaSemana> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(Set<DiaSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Aluno> listarAlunos() {
        return alunos;
    }

    @Override
    public String toString() {
        return "Turma{" +
                "id=" + id +
                ", codigoTurma='" + codigoTurma + '\'' +
                ", diasSemana=" + diasSemana +
                ", horario='" + horario + '\'' +
                ", professor=" + (professor != null ? professor.getNome() : "null") +
                ", disciplina=" + (disciplina != null ? disciplina.getNome() : "null") +
                ", alunos=" + (alunos != null ? alunos.size() : 0) +
                '}';
    }
}