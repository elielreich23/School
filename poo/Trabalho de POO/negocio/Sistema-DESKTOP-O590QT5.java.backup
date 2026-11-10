package negocio;

import dados.*;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Aluno> alunos;
    private List<Professor> professores;
    private List<Curso> cursos;
    private List<Turma> turmas;
    private List<Matricula> matriculas;
    private List<Avaliacao> avaliacoes;
    private List<Nota> notas;
    private List<Mensalidade> mensalidades;
    private List<Pagamento> pagamentos;
    private List<Encontro> encontros;

    public Sistema() {
        alunos = new ArrayList<>();
        professores = new ArrayList<>();
        cursos = new ArrayList<>();
        turmas = new ArrayList<>();
        matriculas = new ArrayList<>();
        avaliacoes = new ArrayList<>();
        notas = new ArrayList<>();
        mensalidades = new ArrayList<>();
        pagamentos = new ArrayList<>();
        encontros = new ArrayList<>();
    }

    public void cadastrarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new RegraNegocioException("Aluno não pode ser nulo.");
        }
        if (aluno.getCpf() == null || aluno.getCpf().isEmpty()) {
            throw new RegraNegocioException("Não é permitido cadastrar aluno com CPF vazio.");
        }
        alunos.add(aluno);
    }

    public void cadastrarProfessor(Professor professor) {
        if (professor == null) {
            throw new RegraNegocioException("Professor não pode ser nulo.");
        }
        if (professor.getCpf() == null || professor.getCpf().isEmpty()) {
            throw new RegraNegocioException("Não é permitido cadastrar professor com CPF vazio.");
        }
        professores.add(professor);
    }

    public void cadastrarCurso(Curso curso) {
        if (curso == null) {
            throw new RegraNegocioException("Curso não pode ser nulo.");
        }
        if (curso.getCodigo() == null || curso.getCodigo().isEmpty()) {
            throw new RegraNegocioException("Não é permitido cadastrar curso com código vazio.");
        }
        cursos.add(curso);
    }

    public void cadastrarTurma(Turma turma) {
        if (turma == null) {
            throw new RegraNegocioException("Turma não pode ser nula.");
        }
        if (turma.getCurso() == null || turma.getProfessor() == null) {
            throw new RegraNegocioException("Não é permitido cadastrar turma sem curso ou professor.");
        }
        turmas.add(turma);
    }

    public void cadastrarMatricula(Matricula matricula) {
        if (matricula == null) {
            throw new RegraNegocioException("Matrícula não pode ser nula.");
        }
        if (matricula.getAluno() == null || matricula.getTurma() == null) {
            throw new RegraNegocioException("Não é permitido cadastrar matrícula sem aluno ou turma.");
        }

        for (int i = 0; i < matriculas.size(); i++) {
            Matricula existente = matriculas.get(i);
            if (existente.getAluno() == matricula.getAluno()
                    && existente.getTurma() == matricula.getTurma()) {
                throw new RegraNegocioException("Este aluno já está matriculado nesta turma.");
            }
        }

        matriculas.add(matricula);
    }

    public void cadastrarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new RegraNegocioException("Avaliação não pode ser nula.");
        }
        avaliacoes.add(avaliacao);
    }

    public void cadastrarNota(Nota nota) {
        if (nota == null) {
            throw new RegraNegocioException("Nota não pode ser nula.");
        }
        if (nota.getNota() < 0 || nota.getNota() > 10) {
            throw new RegraNegocioException("A nota deve estar entre 0 e 10.");
        }
        notas.add(nota);
    }

    public void cadastrarMensalidade(Mensalidade mensalidade) {
        if (mensalidade == null) {
            throw new RegraNegocioException("Mensalidade não pode ser nula.");
        }
        if (mensalidade.getValor() <= 0) {
            throw new RegraNegocioException("O valor da mensalidade deve ser maior que zero.");
        }
        if (mensalidade.getMatricula() != null) {
            mensalidade.getMatricula().adicionarMensalidade(mensalidade);
        }
        mensalidades.add(mensalidade);
    }

    public void cadastrarPagamento(Mensalidade mensalidade, Pagamento pagamento) {
        if (mensalidade == null || pagamento == null) {
            throw new RegraNegocioException("Mensalidade e pagamento são obrigatórios.");
        }
        if (pagamento.getValor() <= 0) {
            throw new RegraNegocioException("O valor do pagamento deve ser maior que zero.");
        }
        mensalidade.registrarPagamento(pagamento);
        pagamentos.add(pagamento);
    }

    public void cadastrarEncontro(Encontro encontro) {
        if (encontro == null) {
            throw new RegraNegocioException("Encontro não pode ser nulo.");
        }
        encontros.add(encontro);
    }

    public Aluno buscarAluno(String cpf) {
        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);
            if (aluno.getCpf() != null && aluno.getCpf().equals(cpf)) {
                return aluno;
            }
        }
        return null;
    }

    public Professor buscarProfessor(String cpf) {
        for (int i = 0; i < professores.size(); i++) {
            Professor professor = professores.get(i);
            if (professor.getCpf() != null && professor.getCpf().equals(cpf)) {
                return professor;
            }
        }
        return null;
    }

    public Curso buscarCurso(String codigo) {
        for (int i = 0; i < cursos.size(); i++) {
            Curso curso = cursos.get(i);
            if (curso.getCodigo() != null && curso.getCodigo().equals(codigo)) {
                return curso;
            }
        }
        return null;
    }

    public Turma buscarTurma(String codigo) {
        for (int i = 0; i < turmas.size(); i++) {
            Turma turma = turmas.get(i);
            if (turma.getCodigo() != null && turma.getCodigo().equals(codigo)) {
                return turma;
            }
        }
        return null;
    }

    public Matricula buscarMatricula(String cpfAluno, String codigoTurma) {
        for (int i = 0; i < matriculas.size(); i++) {
            Matricula matricula = matriculas.get(i);
            if (matricula.getAluno() != null && matricula.getTurma() != null
                    && matricula.getAluno().getCpf().equals(cpfAluno)
                    && matricula.getTurma().getCodigo().equals(codigoTurma)) {
                return matricula;
            }
        }
        return null;
    }

    public Avaliacao buscarAvaliacao(String descricao) {
        for (int i = 0; i < avaliacoes.size(); i++) {
            Avaliacao avaliacao = avaliacoes.get(i);
            if (avaliacao.getDescricao() != null && avaliacao.getDescricao().equals(descricao)) {
                return avaliacao;
            }
        }
        return null;
    }

    public Mensalidade buscarMensalidade(int numero) {
        for (int i = 0; i < mensalidades.size(); i++) {
            Mensalidade mensalidade = mensalidades.get(i);
            if (mensalidade.getNumero() == numero) {
                return mensalidade;
            }
        }
        return null;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public List<Encontro> getEncontros() {
        return encontros;
    }
}
