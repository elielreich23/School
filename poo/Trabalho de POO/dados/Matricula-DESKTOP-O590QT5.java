package dados;

import java.util.ArrayList;
import java.util.List;

public class Matricula {
    private Aluno aluno;
    private Turma turma;
    private double mensalidade;
    private boolean ativa;
    private List<Mensalidade> mensalidades;

    public Matricula(Aluno aluno, Turma turma, double mensalidade) {
        this.aluno = aluno;
        this.turma = turma;
        this.mensalidade = mensalidade;
        this.ativa = true;
        this.mensalidades = new ArrayList<>();
    }

    public void adicionarMensalidade(Mensalidade mensalidade) {
        mensalidades.add(mensalidade);
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public double getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(double mensalidade) {
        this.mensalidade = mensalidade;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    @Override
    public String toString() {
        String nomeAluno = aluno != null ? aluno.getNome() : "sem aluno";
        String codigoTurma = turma != null ? turma.getCodigo() : "sem turma";
        String status = ativa ? "ativa" : "inativa";
        return "Matrícula de " + nomeAluno + " na turma " + codigoTurma
                + " | Valor: R$ " + mensalidade + " | " + status;
    }
}
