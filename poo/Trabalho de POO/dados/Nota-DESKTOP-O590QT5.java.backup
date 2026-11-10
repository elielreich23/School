package dados;

import negocio.RegraNegocioException;

public class Nota {
    private Aluno aluno;
    private Avaliacao avaliacao;
    private double nota;

    public Nota(Aluno aluno, Avaliacao avaliacao, double nota) {
        if (nota < 0 || nota > 10) {
            throw new RegraNegocioException("A nota deve estar entre 0 e 10.");
        }
        this.aluno = aluno;
        this.avaliacao = avaliacao;
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new RegraNegocioException("A nota deve estar entre 0 e 10.");
        }
        this.nota = nota;
    }

    @Override
    public String toString() {
        String nomeAluno = aluno != null ? aluno.getNome() : "sem aluno";
        String descAvaliacao = avaliacao != null ? avaliacao.getDescricao() : "sem avaliação";
        return "Nota de " + nomeAluno + " em " + descAvaliacao + ": " + nota;
    }
}
