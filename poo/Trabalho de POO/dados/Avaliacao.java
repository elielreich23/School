package dados;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Avaliacao {
    private String identificador;
    private TipoAvaliacao tipo;
    private LocalDate dataAplicacao;
    private BigDecimal valorMaximo;
    private BigDecimal peso;
    private Modulo modulo;

    public Avaliacao() {
    }

    public Avaliacao(String identificador, TipoAvaliacao tipo, LocalDate dataAplicacao, BigDecimal valorMaximo, BigDecimal peso) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.dataAplicacao = dataAplicacao;
        this.valorMaximo = valorMaximo;
        this.peso = peso;
    }

    public Avaliacao(String identificador, TipoAvaliacao tipo, LocalDate dataAplicacao, BigDecimal valorMaximo, BigDecimal peso, Modulo modulo) {
        this(identificador, tipo, dataAplicacao, valorMaximo, peso);
        this.modulo = modulo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public TipoAvaliacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoAvaliacao tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avaliacao avaliacao)) return false;
        return Objects.equals(identificador, avaliacao.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "Avaliacao [" + identificador + "] " + tipo + " (Data: " + dataAplicacao + ", Max: " + valorMaximo + ", Peso: " + peso + ")";
    }
}
