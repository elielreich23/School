package dados;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Certificado {
    private String numero;
    private LocalDate dataEmissao;
    private Aluno aluno;
    private Curso curso;
    private Modulo modulo;
    private int cargaHoraria;
    private BigDecimal notaFinal;

    public Certificado() {
    }

    public Certificado(String numero, LocalDate dataEmissao, Aluno aluno, Curso curso, Modulo modulo, int cargaHoraria, BigDecimal notaFinal) {
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.aluno = aluno;
        this.curso = curso;
        this.modulo = modulo;
        this.cargaHoraria = cargaHoraria;
        this.notaFinal = notaFinal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public BigDecimal getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(BigDecimal notaFinal) {
        this.notaFinal = notaFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificado that)) return false;
        return Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "CERTIFICADO #" + numero + " - Aluno: " + (aluno != null ? aluno.getNome() : "N/A") +
                " concluiu o módulo '" + (modulo != null ? modulo.getNome() : "N/A") +
                "' do curso '" + (curso != null ? curso.getIdioma() : "N/A") +
                "' com carga horária de " + cargaHoraria + "h e nota final " + notaFinal +
                " em " + dataEmissao;
    }
}
