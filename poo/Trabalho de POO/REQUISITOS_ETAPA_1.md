# Requisitos — Escola de Idiomas (Etapa I)

## 1. Objetivo

Desenvolver, em Java, um sistema simples para uma escola de idiomas, usando os conceitos básicos de Programação Orientada a Objetos.

Nesta etapa:

- os dados ficam em memória, em listas da classe `Sistema`;
- não há interface gráfica;
- não há banco de dados;
- não há frameworks.

O programa deve permitir cadastrar as informações principais pelo terminal, com `Scanner` e um menu. A classe `Programa` contém o `main()` e começa com o sistema vazio: o usuário cria os objetos e os relacionamentos.

## 2. Pacotes

- `dados`: classes que representam as informações da escola.
- `dados.enums`: enumeradores simples.
- `negocio`: classe `Sistema`, exceção de regra de negócio e classe `Programa`.

## 3. Classes de dados

### Aluno

Representa um aluno da escola.

Atributos: `nome`, `cpf`, `email`.

### Professor

Representa um professor.

Atributos: `nome`, `cpf`, `email`.

### Curso

Representa um curso de idioma.

Atributos: `codigo`, `nome`, `idioma`, `nivel`, lista de `Modulo`.

Um curso pode ter vários módulos. O método `adicionarModulo()` inclui um módulo no curso.

### Modulo

Representa uma parte do curso.

Atributos: `numero`, `nome`, `cargaHoraria`.

Não há sistema de pré-requisitos.

### Turma

Representa uma turma de um curso, com um professor responsável.

Atributos: `codigo`, `curso`, `professor`, `quantidadeMaximaAlunos`, `situacao`.

Métodos:

- `abrir()`: situa a turma como em andamento;
- `encerrar()`: situa a turma como concluída.

### Matricula

Classe de associação entre aluno e turma.

Atributos: `aluno`, `turma`, `mensalidade`, `ativa`.

Relacionamento:

```text
Aluno 1 ----- * Matricula * ----- 1 Turma
```

### Avaliacao

Representa uma avaliação de um módulo.

Atributos: `descricao`, `valor`, `modulo`, `tipo`.

Tipos: prova, trabalho ou exercício.

### Nota

Representa a nota de um aluno em uma avaliação.

Atributos: `aluno`, `avaliacao`, `nota`.

A nota deve estar entre 0 e 10.

### Mensalidade

Representa uma cobrança da matrícula.

Atributos: `numero`, `valor`, `paga`, além da matrícula e do pagamento (quando existir).

Não há juros, multa, desconto ou parcelamento avançado.

### Pagamento

Representa o pagamento de uma mensalidade.

Atributos: `valor`, `formaPagamento` (por exemplo PIX, CARTAO ou DINHEIRO).

Uma mensalidade pode ter no máximo um pagamento.

### Encontro

Representa uma aula.

Atributos: `numero`, `modulo`, `data`.

A data é guardada como texto, para manter o código simples.

## 4. Enums

- `NivelCurso`: BASICO, INTERMEDIARIO, AVANCADO
- `SituacaoTurma`: ABERTA, EM_ANDAMENTO, CONCLUIDA
- `TipoAvaliacao`: PROVA, TRABALHO, EXERCICIO

## 5. Classe Sistema

A classe `Sistema` guarda listas em memória:

- alunos
- professores
- cursos
- turmas
- matrículas
- avaliações
- notas
- mensalidades
- pagamentos
- encontros

Métodos de cadastro:

- `cadastrarAluno`
- `cadastrarProfessor`
- `cadastrarCurso`
- `cadastrarTurma`
- `cadastrarMatricula`
- `cadastrarAvaliacao`
- `cadastrarNota`
- `cadastrarMensalidade`
- `cadastrarPagamento`
- `cadastrarEncontro`

Consultas:

- `buscarAluno`
- `buscarProfessor`
- `buscarCurso`
- `buscarTurma`
- `buscarMatricula` (CPF do aluno e código da turma)
- `buscarAvaliacao` (descrição)
- `buscarMensalidade` (número)

## 6. Regras de negócio

São poucas regras, fáceis de explicar:

- aluno e professor não podem ter CPF vazio;
- curso não pode ter código vazio;
- turma precisa de curso e professor;
- matrícula precisa de aluno e turma;
- o mesmo aluno não pode ser matriculado duas vezes na mesma turma;
- a nota deve estar entre 0 e 10;
- o valor da mensalidade deve ser maior que zero;
- o valor do pagamento deve ser maior que zero.

Quando uma regra é violada, o sistema lança `RegraNegocioException`.

## 7. Programa interativo

A classe `Programa` mostra um menu no terminal até o usuário escolher `0 - Sair`.

Nada é cadastrado automaticamente, só o `Sistema`. O usuário escolhe as opções e informa os dados.

Menu principal:

1. Cadastrar aluno
2. Cadastrar professor
3. Cadastrar curso
4. Adicionar módulo ao curso
5. Cadastrar turma
6. Matricular aluno
7. Cadastrar avaliação
8. Registrar nota
9. Cadastrar mensalidade
10. Registrar pagamento
11. Cadastrar encontro
12. Listar dados
0. Sair

A opção 12 abre um submenu para listar cada tipo de cadastro com `for`.

Para localizar uma matrícula (mensalidade), o programa pede o CPF do aluno e o código da turma.
