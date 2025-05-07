package org.example;

public final class Matricula {
    private String dataMatricula;
    private String status;
    private Aluno aluno;

    public Matricula(String dataMatricula, String status, Aluno aluno) {
        this.dataMatricula = dataMatricula;
        this.status = status;
        this.aluno = aluno;
    }

    //getter setter
    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
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
                "dataMatricula='" + dataMatricula + '\'' +
                ", status='" + status + '\'' +
                ", aluno='" + (aluno != null ? aluno.getNome() : "null") + '\'' +
                '}';
    }
}
