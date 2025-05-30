package org.example;

public class SecretariaAcademica extends Usuario {
    private int id;

    public SecretariaAcademica(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.SECRETARIA);
    }

    // Construtor com ID (opcional para uso ao carregar do banco)
    public SecretariaAcademica(int id, String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.SECRETARIA);
        this.id = id;
    }

    // Getter e setter para ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void cadastrarCurso(Curso curso) {
        CursoCRUD.create(curso);
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        DisciplinaCRUD.create(disciplina);
    }

    public void cadastrarTurma(Turma turma) {
        TurmaCRUD.create(turma);
    }

    //a secretaria tem a responsabilidade de cadastrar o aluno. isso atualiza o banco
    public void cadastrarAluno(Aluno aluno) {
        AlunoCRUD.create(aluno);
    }

        public void matricularAlunoEmTurma(Aluno aluno, Turma turma) {
        AlunoCRUD.matricularEmTurma(aluno.getId(), turma.getId());
    }
    public boolean verificarConclusaoAluno(Aluno aluno) {
        return false;
    }

    public void processarCancelamento(Aluno aluno) {
        aluno.cancelarMatricula();
    }

    public void processarSolicitacaoHistorico(Aluno aluno) {
        System.out.println("Solicitação de histórico recebida: " + aluno.getNome());
    }
}