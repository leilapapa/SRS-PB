package org.example;

public final class Matricula {
    private int id;
    private String matricula;
    private String status;
    private Aluno aluno;

    public Matricula(String matricula, String status, Aluno aluno) {
        this.matricula = matricula;
        this.status = status;
        this.aluno = aluno;
    }

    //getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula  () {
        return matricula;
    }

    public void setMatricula (String matricula) {
        this.matricula = matricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", matrícula ='" + matricula + '\'' +
                ", status='" + status + '\'' +
                ", aluno='" + (aluno != null ? aluno.getNome() : "null") + '\'' +
                '}';
    }
}