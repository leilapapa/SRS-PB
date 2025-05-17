package org.example; //comentario

import java.util.*;

public class Aluno extends Usuario {
    private Matricula matricula;
    private List<Turma> turmas;
    private Historico historico;

    public Aluno(String nome, String email, String senha, String dataMatricula, String status) {
        super(nome, email, senha, TipoUsuario.ALUNO);
        this.matricula = new Matricula(dataMatricula, status, this);
        this.turmas = new ArrayList<>();
        this.historico = new Historico(this);
    }

    // Getters e setters
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

    //metodos

    //aluuno pede o cancelamento, mas não executa diretamente
    public void solicitarCancelamento(SecretariaAcademica secretaria) {
        secretaria.processarCancelamento(this);
    }

    //método interno, só a secretaria pode usar este método diretamente
    protected void cancelarMatricula() {
        turmas.clear();
        this.matricula.setStatus("cancelada");//controla o status no objeto Matricula
    }


    public List<Disciplina> visualizarGradeDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();//cria nova lista de disciplinas
        for (Turma turma : turmas) {//percorre as turamas que o aluno esta matriculado
            disciplinas.addAll(turma.getDisciplina() != null ?  //verifica em cada turma se tem uma disciplina associada
                    List.of(turma.getDisciplina()) : List.of()); //se existir a disciplina, adiciona na lista final. senao nao adiciona nada
        }
        return disciplinas;
    }

    public Map<Disciplina, Nota> visualizarNotas() {
        return historico.getDisciplinasNotas();
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula=" + (matricula != null ? matricula.getDataMatricula() : "null") +
                ", status=" + (matricula != null ? matricula.getStatus() : "null") +
                ", turmas=" + (turmas != null ? turmas.size() : 0) +
                ", historico=" + (historico != null ? "OK" : "null") +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
