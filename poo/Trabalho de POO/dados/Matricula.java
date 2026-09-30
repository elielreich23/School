package dados;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Matricula {
    private String numero;
    private Aluno aluno;
    private Turma turma;
    private LocalDate dataMatricula;
    private BigDecimal valorMensalidade;
    private int quantidadeParcelas;
    private BigDecimal desconto = BigDecimal.ZERO;
    private String formaPagamento;
    private boolean ativa = true;

    private List<Mensalidade> mensalidades = new ArrayList<>();
    private List<Nota> notas = new ArrayList<>();
    private List<RegistroFrequencia> registrosFrequencia = new ArrayList<>();

    public Matricula() {
    }

    public Matricula(String numero, Aluno aluno, Turma turma, LocalDate dataMatricula, BigDecimal valorMensalidade, int quantidadeParcelas, BigDecimal desconto, String formaPagamento) {
        this.numero = numero;
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = dataMatricula;
        this.valorMensalidade = valorMensalidade;
        this.quantidadeParcelas = quantidadeParcelas;
        this.desconto = desconto != null ? desconto : BigDecimal.ZERO;
        this.formaPagamento = formaPagamento;
        this.ativa = true;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public BigDecimal getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(BigDecimal valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
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

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public void adicionarMensalidade(Mensalidade mensalidade) {
        if (mensalidade != null && !mensalidades.contains(mensalidade)) {
            mensalidades.add(mensalidade);
            mensalidade.setMatricula(this);
        }
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public void adicionarNota(Nota nota) {
        if (nota != null) {
            notas.add(nota);
        }
    }

    public List<RegistroFrequencia> getRegistrosFrequencia() {
        return registrosFrequencia;
    }

    public void setRegistrosFrequencia(List<RegistroFrequencia> registrosFrequencia) {
        this.registrosFrequencia = registrosFrequencia;
    }

    public void adicionarRegistroFrequencia(RegistroFrequencia reg) {
        if (reg != null) {
            registrosFrequencia.add(reg);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matricula matricula)) return false;
        return Objects.equals(numero, matricula.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Matrícula [" + numero + "] Aluno: " + (aluno != null ? aluno.getNome() : "N/A") +
                " - Turma: " + (turma != null ? turma.getCodigo() : "N/A") +
                " - Ativa: " + ativa + " - " + quantidadeParcelas + "x R$ " + valorMensalidade;
    }
}
