# 🎓 Sistema de Gestão Acadêmica

Sistema orientado a objetos desenvolvido em Java para gerenciamento acadêmico. Ele simula as operações essenciais de uma instituição de ensino, como matrícula de alunos, gerenciamento de disciplinas, notas, turmas e geração de histórico.

---

## 🧩 Funcionalidades

- Cadastro de Alunos, Professores, Secretarias, Disciplinas e Cursos
- Matrícula e Cancelamento de Matrícula de Alunos
- Atribuição e Visualização de Notas
- Geração de Histórico em PDF
- Visualização de Grade Horária
- Interface gráfica para Aluno com Swing
- Simulação de envio de e-mail
- Autenticação de Usuários

---

## 🛠️ Tecnologias Utilizadas

- **Java SE 11+**
- **Swing (GUI)**
- **Java Collections Framework**
- **JDBC (Simulado)**
- **Apache PDFBox (PDF)**
- **Mermaid (para UML)**

---

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/sistema-academico-java.git

## 📁 Estrutura de Diretórios

src/

├── model/                   # Classes principais (Aluno, Curso, Turma, etc.)

├── view/                    # Interfaces gráficas (Swing)

├── service/                 # Lógica de negócio

├── persistence/             # Acesso e simulação de dados (CRUDs)

├── utils/                   # Utilitários (PDF, e-mail simulado)

└── Main.java                # Classe principal

## 👤 Perfis de Usuário

- Aluno

    -  Visualiza notas, grade horária, solicita cancelamento de matrícula

- Secretaria Acadêmica

    - Cadastra entidades, processa cancelamentos, matricula alunos

## 📃 Licença

SRS-PB está de acordo com a MIT License.

