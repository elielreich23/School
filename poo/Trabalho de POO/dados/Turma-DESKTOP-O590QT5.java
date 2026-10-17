package dados;

import dados.enums.SituacaoTurma;

public class Turma {
    private String codigo;
    private Curso curso;
    private Professor professor;
    private int quantidadeMaximaAlunos;
    private SituacaoTurma situacao;

    public Turma(String codigo, Curso curso, Professor professor, int quantidadeMaximaAlunos) {
        this.codigo = codigo;
        this.curso = curso;
        this.professor = professor;
        this.quantidadeMaximaAlunos = quantidadeMaximaAlunos;
        this.situacao = SituacaoTurma.ABERTA;
    }

    public void abrir() {
        situacao = SituacaoTurma.EM_ANDAMENTO;
    }

    public void encerrar() {
        situacao = SituacaoTurma.CONCLUIDA;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getQuantidadeMaximaAlunos() {
        return quantidadeMaximaAlunos;
    }

    public void setQuantidadeMaximaAlunos(int quantidadeMaximaAlunos) {
        this.quantidadeMaximaAlunos = quantidadeMaximaAlunos;
    }

    public SituacaoTurma getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoTurma situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        String nomeCurso = curso != null ? curso.getNome() : "sem curso";
        String nomeProfessor = professor != null ? professor.getNome() : "sem professor";
        return "Turma " + codigo + " | Curso: " + nomeCurso + " | Professor: " + nomeProfessor
                + " | Situação: " + situacao;
    }
}
