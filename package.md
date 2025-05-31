classDiagram
direction BT
class Aluno {
  + Aluno(int, String, String, String, String, String) 
  + Aluno(String, String, String, String, String) 
  - List~Turma~ turmas
  - Historico historico
  - Matricula matricula
  + setStatus(String) void
  # cancelarMatricula() void
  + setTurmas(List~Turma~) void
  + solicitarCancelamento(SecretariaAcademica) void
  + getTurmas() List~Turma~
  + getId() int
  + visualizarNotas() Map~Disciplina, Nota~
  + setMatricula(Matricula) void
  + visualizarGradeDisciplinas() List~Disciplina~
  + toString() String
  + getMatricula() Matricula
  + getHistorico() Historico
  + setHistorico(Historico) void
}
class AlunoCRUD {
  + AlunoCRUD() 
  + buscarAlunoPorId(int) Aluno?
  + readAll() List~Aluno~
  + matricularEmTurma(int, int) void
  + create(Aluno) void
  + atualizarAluno(Aluno) void
  + alunoExiste(String) boolean
  + autenticar(String, String) Aluno?
}
class AlunoMenuView {
  + AlunoMenuView() 
  - JFrame frame
  - JTextArea dadosAluno
  + atualizarInterface(Aluno) void
  - estilizarBotao(JButton) void
  - atualizarDadosAluno(Aluno) void
  + criarMenuAluno(Aluno, SecretariaAcademica) void
  - cancelarMatriculaComCallback(Aluno, SecretariaAcademica) void
}
class AlunoService {
  + AlunoService() 
  - String EMAIL_REMETENTE
  - String SENHA_APP
  + visualizarNotas(Aluno) void
  + enviarEmailHistorico(Aluno) void
  - mostrarGraficoNotasReais(List~Nota~) void
  + cancelarMatricula(Aluno, SecretariaAcademica) void
  + enviarEmailCancelamento(Aluno, SecretariaAcademica) void
  + enviarEmailComNotasReais(String, List~Nota~) void
  + solicitarHistorico(Aluno, SecretariaAcademica) void
  + enviarEmailComMaterias(String, List~Turma~) void
  + visualizarGrade(Aluno) void
  + salvarGradeEmPDF(File, List~Turma~, Aluno) void
  - formatarDiasSemana(Object) String
}
class Curso {
  + Curso(String, String) 
  + Curso(int, String, String) 
  - int id
  - String codigo
  - String nome
  - List~Disciplina~ disciplinas
  + getNome() String
  + setId(int) void
  + toString() String
  + setDisciplinas(List~Disciplina~) void
  + setNome(String) void
  + getId() int
  + listarDisciplinas() List~Disciplina~
  + getDisciplinas() List~Disciplina~
  + getCodigo() String
  + setCodigo(String) void
}
class CursoCRUD {
  + CursoCRUD() 
  + create(Curso) void
  + cursoExiste(String) boolean
  + readAll() List~Curso~
}
class DatabaseConfig {
  + DatabaseConfig() 
  - String URL
  + getConnection() Connection
  + criarTabelas() void
}
class DiaSemana {
<<enumeration>>
  + DiaSemana() 
  +  SEG
  +  QUI
  +  TER
  +  QUA
  +  SEX
  +  SAB
  + valueOf(String) DiaSemana
  + values() DiaSemana[]
}
class Disciplina {
  + Disciplina(String, int, Curso) 
  + Disciplina(int, String, int, Curso) 
  - String nome
  - int id
  - int vagas
  - List~Turma~ turmas
  - Curso curso
  - List~Disciplina~ preRequisitos
  + setId(int) void
  + setTurmas(List~Turma~) void
  + toString() String
  + getNome() String
  + getVagas() int
  + getTurmas() List~Turma~
  + setPreRequisitos(List~Disciplina~) void
  + getId() int
  + setVagas(int) void
  + getCurso() Curso
  + setCurso(Curso) void
  + getPreRequisitos() List~Disciplina~
  + listarTurmas() List~Turma~
  + setNome(String) void
}
class DisciplinaCRUD {
  + DisciplinaCRUD() 
  + create(Disciplina) void
  + readAll() List~Disciplina~
  + disciplinaExiste(String, int) boolean
}
class Historico {
  + Historico(Aluno) 
  - Aluno aluno
  - Map~Disciplina, Nota~ disciplinasNotas
  + adicionarNota(Disciplina, Nota) void
  + setAluno(Aluno) void
  + setDisciplinasNotas(Map~Disciplina, Nota~) void
  + toString() String
  + getDisciplinasNotas() Map~Disciplina, Nota~
  + getAluno() Aluno
}
class InserirDadosIniciais {
  + InserirDadosIniciais() 
  + dadosIniciais(SecretariaAcademica) void
}
class LoginView {
  + LoginView() 
  + criarJanelaDeLogin() void
}
class Main {
  + Main() 
  + main(String[]) void
}
class Matricula {
  + Matricula(String, String, Aluno) 
  - Aluno aluno
  - String status
  - String matricula
  - int id
  + setMatricula(String) void
  + setAluno(Aluno) void
  + getStatus() String
  + getMatricula() String
  + toString() String
  + getId() int
  + setId(int) void
  + getAluno() Aluno
  + setStatus(String) void
}
class Nota {
  + Nota(float, SituacaoNota) 
  + Nota(float, SituacaoNota, Aluno, Disciplina) 
  - Disciplina disciplina
  - Aluno aluno
  - float valor
  - SituacaoNota situacao
  + getSituacao() SituacaoNota
  + setAluno(Aluno) void
  + getValor() float
  + setSituacao(SituacaoNota) void
  + setDisciplina(Disciplina) void
  + getAluno() Aluno
  + getDisciplina() Disciplina
  + setValor(float) void
  + toString() String
}
class NotaCRUD {
  + NotaCRUD() 
  + notaExiste(int, int) boolean
  + create(Nota) void
  + getNotasDoAluno(int) List~Nota~
}
class Professor {
  + Professor(String, String, String, TituloProfessor) 
  - TituloProfessor titulo
  + getTitulo() TituloProfessor
  + atribuirNota(Aluno, Disciplina, float) void
  + toString() String
  + setTitulo(TituloProfessor) void
}
class SecretariaAcademica {
  + SecretariaAcademica(String, String, String) 
  + SecretariaAcademica(int, String, String, String) 
  - int id
  + cadastrarAluno(Aluno) void
  + getId() int
  + cadastrarCurso(Curso) void
  + matricularAlunoEmTurma(Aluno, Turma) void
  + cadastrarTurma(Turma) void
  + processarSolicitacaoHistorico(Aluno) void
  + verificarConclusaoAluno(Aluno) boolean
  + setId(int) void
  + cadastrarDisciplina(Disciplina) void
  + processarCancelamento(Aluno) void
}
class SecretariaAcademicaCRUD {
  + SecretariaAcademicaCRUD() 
  + listarSecretarias() void
  + create(SecretariaAcademica) void
  + autenticar(String, String) SecretariaAcademica?
}
class SituacaoNota {
<<enumeration>>
  + SituacaoNota() 
  +  APROVADO
  +  REPROVADO
  + values() SituacaoNota[]
  + valueOf(String) SituacaoNota
}
class TipoUsuario {
<<enumeration>>
  + TipoUsuario() 
  +  ALUNO
  +  PROFESSOR
  +  SECRETARIA
  + values() TipoUsuario[]
  + valueOf(String) TipoUsuario
}
class TituloProfessor {
<<enumeration>>
  + TituloProfessor() 
  +  CONTRATADO
  +  PROVISORIO
  + values() TituloProfessor[]
  + valueOf(String) TituloProfessor
}
class Turma {
  + Turma(String, Set~DiaSemana~, String, Professor) 
  + Turma(int, String, Set~DiaSemana~, String, Professor, Disciplina) 
  - String codigoTurma
  - Set~DiaSemana~ diasSemana
  - Disciplina disciplina
  - String horario
  - Professor professor
  - List~Aluno~ alunos
  - int id
  + setHorario(String) void
  + setDisciplina(Disciplina) void
  + setCodigoTurma(String) void
  + getId() int
  + getDisciplina() Disciplina
  + setDiasSemana(Set~DiaSemana~) void
  + getHorario() String
  + setAlunos(List~Aluno~) void
  + getAlunos() List~Aluno~
  + getProfessor() Professor
  + toString() String
  + setId(int) void
  + getCodigoTurma() String
  + getDiasSemana() Set~DiaSemana~
  + setProfessor(Professor) void
  + listarAlunos() List~Aluno~
}
class TurmaCRUD {
  + TurmaCRUD() 
  + create(Turma) void
  + turmaExiste(String) boolean
  + buscarTurmasDoAluno(int) List~Turma~
  + readAll() List~Turma~
}
class Usuario {
  + Usuario(String, String, String, TipoUsuario) 
  # String email
  # String senha
  # TipoUsuario tipo
  # String nome
  + setNome(String) void
  + getEmail() String
  + setSenha(String) void
  + getTipo() TipoUsuario
  + getSenha() String
  + toString() String
  + getNome() String
  + setEmail(String) void
  + setTipo(TipoUsuario) void
}

Aluno  -->  Usuario 
Professor  -->  Usuario 
SecretariaAcademica  -->  Usuario 
