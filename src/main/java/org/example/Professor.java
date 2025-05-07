package org.example;

public class Professor extends Usuario {
    private TituloProfessor titulo;

    public Professor(String nome, String email, String senha, TituloProfessor titulo) {
        super(nome, email, senha, TipoUsuario.PROFESSOR);
        this.titulo = titulo;
    }

    //getter setter
    public TituloProfessor getTitulo() {
        return titulo;
    }

    public void setTitulo(TituloProfessor titulo) {
        this.titulo = titulo;
    }

    //metodo
    public void atribuirNota(Aluno aluno, Disciplina disciplina, float valor) {
        Nota nota = new Nota(valor, SituacaoNota.APROVADO);
        aluno.getHistorico().adicionarNota(disciplina, nota);
    }

    @Override
    public String toString() {
        return "Professor{" +
                "titulo=" + titulo +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}