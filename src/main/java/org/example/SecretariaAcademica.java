package org.example;

public class SecretariaAcademica extends Usuario {
    public SecretariaAcademica(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.SECRETARIA);
    }


    public void cadastrarCurso(Curso curso) {}
    public void cadastrarDisciplina(Disciplina disciplina) {}
    public void cadastrarTurma(Turma turma) {}
    public boolean verificarConclusaoAluno(Aluno aluno) { return false; }
}
