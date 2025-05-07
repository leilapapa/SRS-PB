package org.example;

import java.util.*;

public class Aluno extends Usuario {
    private String matricula;
    private List<Turma> turmas;
    private Historico historico;

    public Aluno(String nome, String email, String senha, String matricula) {
        super(nome, email, senha, TipoUsuario.ALUNO);
        this.matricula = matricula;
        this.turmas = new ArrayList<>();
        this.historico = new Historico(this);
    }

    //getter setter
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    //metodos
    public void solicitarCancelamento(SecretariaAcademica secretaria) {
        secretaria.processarCancelamento(this);
    }

    //metodo interno que vai ser usado pela classe Secretaria Academica
    protected void cancelarMatricula() {
        turmas.clear();
    }

    public List<Disciplina> visualizarGradeDisciplinas() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula='" + matricula + '\'' +
                ", turmas=" + (turmas != null ? turmas.size() : 0) +
                ", historico=" + (historico != null ? "OK" : "null") +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}