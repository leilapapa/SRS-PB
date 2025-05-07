package org.example;

import java.util.*;

public class Turma {
    private String codigoTurma;
    private Set<DiaSemana> diasSemana;
    private String horario;
    private Professor professor;
    private List<Aluno> alunos;

    public Turma(String codigoTurma, Set<DiaSemana> diasSemana, String horario, Professor professor) {
        this.codigoTurma = codigoTurma;
        this.diasSemana = diasSemana;
        this.horario = horario;
        this.professor = professor;
        this.alunos = new ArrayList<>();
    }

    //getter setter
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

    //metodo
    public List<Aluno> listarAlunos() {
        return alunos;
    }

    @Override
    public String toString() {
        return "Turma{" +
                "codigoTurma='" + codigoTurma + '\'' +
                ", diasSemana=" + diasSemana +
                ", horario='" + horario + '\'' +
                ", professor=" + (professor != null ? professor.getNome() : "null") +
                ", alunos=" + (alunos != null ? alunos.size() : 0) +
                '}';
    }
}
