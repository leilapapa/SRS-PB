# SRS - Sistema de Registro de Alunos

Este projeto é um sistema acadêmico básico em Java com interface gráfica (Swing), voltado para simulação de operações de um aluno dentro de um ambiente educacional.

---

## 📌 Funcionalidades atuais

- **Login de Aluno via arquivo CSV**
- Visualização da grade de disciplinas (simulada)
- Visualização de notas (em memória)
- Cancelamento de matrícula (efetivo durante a execução)
- Interface gráfica com menus específicos para o aluno

---

## 🗃️ Sobre o CSV

Atualmente, o sistema utiliza **apenas um arquivo CSV** para **validar o login** do aluno.

### 📄 Formato esperado do arquivo `alunos.csv`

```csv
matricula,nome,email,senha
20231234,Maria,maria@email.com,123456
```

- Este arquivo é **lido no início da aplicação**
- Nenhuma alteração no sistema (notas, matrícula, etc.) é gravada de volta no CSV
- O CSV **não armazena histórico, turmas, notas ou status**

---

## 🧠 Funcionamento interno

- Alunos do CSV são carregados em memória e armazenados no `RepositorioDeAlunos`
- O `LoginView` utiliza o `Validador` para verificar:
  - Se o e-mail existe
  - Se a senha confere
- Após o login, o `AlunoMenuView` permite:
  - Visualizar notas (simuladas no `Historico`)
  - Visualizar disciplinas (com base nas turmas associadas)
  - Cancelar matrícula (limpa as turmas e muda o status em memória)

---

## 📂 Estrutura de pacotes

| Classe                    | Função                                                                 |
|---------------------------|------------------------------------------------------------------------|
| `AlunoCSVService`         | Lê o CSV de login e popula o repositório                              |
| `RepositorioDeAlunos`     | Guarda os alunos carregados, acessível em toda a aplicação            |
| `LoginView`               | Tela de login                                                         |
| `AlunoMenuView`           | Interface gráfica com opções para o aluno                             |
| `AlunoService`            | Contém lógica de visualização de notas e cancelamento                 |
| `Validador`               | Valida se o login é válido (e-mail e senha batem com os do CSV)       |

---

## 🚫 O que ainda não é persistente

- Notas atribuídas
- Histórico acadêmico
- Status de matrícula (cancelada/ativa)

Esses dados são simulados em memória apenas durante a execução.

---

## ✅ Pronto para o futuro

O sistema foi estruturado para ser facilmente migrado para um banco de dados, como SQLite:

- O `RepositorioDeAlunos` pode ser substituído por DAOs
- A separação entre visualização e lógica está bem definida
- A entrada atual por CSV é compatível com transição para autenticação real

---

## ▶️ Como rodar

1. Abra o projeto em sua IDE (recomendado: IntelliJ)
2. Certifique-se de que o arquivo `alunos.csv` esteja em `src/main/DadosCSV/`
3. Execute a classe `Main`
4. Faça login com os dados do CSV

---

## ✨ Exemplo de login

- **E-mail:** `maria@email.com`
- **Senha:** `123456`

---


