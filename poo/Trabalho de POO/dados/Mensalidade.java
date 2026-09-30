package dados;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Mensalidade {
    private int numero;
    private LocalDate vencimento;
    private BigDecimal valorOriginal;
    private BigDecimal valorAtualizado;
    private LocalDate dataPagamento;
    private SituacaoMensalidade situacao = SituacaoMensalidade.PENDENTE;
    private Pagamento pagamento;
    private Matricula matricula;

    public Mensalidade() {
    }

    public Mensalidade(int numero, LocalDate vencimento, BigDecimal valorOriginal) {
        this.numero = numero;
        this.vencimento = vencimento;
        this.valorOriginal = valorOriginal;
        this.valorAtualizado = valorOriginal;
        this.situacao = SituacaoMensalidade.PENDENTE;
    }

    public Mensalidade(int numero, LocalDate vencimento, BigDecimal valorOriginal, Matricula matricula) {
        this(numero, vencimento, valorOriginal);
        this.matricula = matricula;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public BigDecimal getValorAtualizado() {
        return valorAtualizado;
    }

    public void setValorAtualizado(BigDecimal valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public SituacaoMensalidade getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMensalidade situacao) {
        this.situacao = situacao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public boolean isVencida(LocalDate dataReferencia) {
        if (situacao == SituacaoMensalidade.PAGA) return false;
        return vencimento != null && dataReferencia != null && dataReferencia.isAfter(vencimento);
    }

    @Override
    public String toString() {
        return "Mensalidade #" + numero + " [Venc: " + vencimento + ", Orig: R$ " + valorOriginal +
                ", Atualiz: R$ " + valorAtualizado + ", Situação: " + situacao +
                (dataPagamento != null ? ", Paga em: " + dataPagamento : "") + "]";
    }
}
