package org.example;

public final class Nota {
    private float valor;
    private SituacaoNota situacao;
    private Aluno aluno;
    private Disciplina disciplina;

    public Nota(float valor, SituacaoNota situacao, Aluno aluno, Disciplina disciplina) {
        this.valor = valor;
        this.situacao = situacao;
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    public Nota(float valor, SituacaoNota situacaoNota) {
        this.valor = valor;
        this.situacao = situacaoNota;
    }

    //getter setter
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public SituacaoNota getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoNota situacao) {
        this.situacao = situacao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "valor=" + valor +
                ", situacao=" + situacao +
                '}';
    }
}
