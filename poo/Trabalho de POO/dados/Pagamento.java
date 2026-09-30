package dados;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {
    private LocalDate data;
    private BigDecimal valorPago;
    private String formaPagamento;

    public Pagamento() {
    }

    public Pagamento(LocalDate data, BigDecimal valorPago, String formaPagamento) {
        this.data = data;
        this.valorPago = valorPago;
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Override
    public String toString() {
        return "Pagamento [data=" + data + ", valor=" + valorPago + ", forma=" + formaPagamento + "]";
    }
}
