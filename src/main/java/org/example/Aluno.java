package org.example;

import java.util.*;


public class Aluno extends Usuario {
    private Matricula matricula;
    private List<Turma> turmas;
    private Historico historico;

    public Aluno(String nome, String email, String senha, String matricula, String status) {
        super(nome, email, senha, TipoUsuario.ALUNO);
        this.matricula = new Matricula(matricula, status, this);
        this.turmas = new ArrayList<>();
        this.historico = new Historico(this);
    }

    //construtor adicional para carregar do banco de dados
    public Aluno(int id, String nome, String email, String senha, String matricula, String status) {
        super(nome, email, senha, TipoUsuario.ALUNO);
        this.matricula = new Matricula(matricula, status, this);
        this.matricula.setId(id);
        this.turmas = new ArrayList<>();
        this.historico = new Historico(this);
    }

    //getters e setters
    public int getId() {
        return matricula.getId();
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
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

    //metodos solicitar cancelamento, cancelarMatricula, visualizarGrade, visualizarNotas
    public void solicitarCancelamento(SecretariaAcademica secretaria) {
        secretaria.processarCancelamento(this);
    }

    protected void cancelarMatricula() {
        turmas.clear();
        this.matricula.setStatus("cancelada");
    }

    public List<Disciplina> visualizarGradeDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        for (Turma turma : turmas) {
            disciplinas.addAll(turma.getDisciplina() != null ?
                    List.of(turma.getDisciplina()) : List.of());
        }
        return disciplinas;
    }

    public Map<Disciplina, Nota> visualizarNotas() {
        return historico.getDisciplinasNotas();
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + getId() +
                ", matricula=" + (matricula != null ? matricula.getMatricula() : "null") +
                ", status=" + (matricula != null ? matricula.getStatus() : "null") +
                ", turmas=" + (turmas != null ? turmas.size() : 0) +
                ", historico=" + (historico != null ? "OK" : "null") +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}