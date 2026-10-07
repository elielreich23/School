package dados;

import dados.enums.TipoAvaliacao;

public class Avaliacao {
    private String descricao;
    private double valor;
    private Modulo modulo;
    private TipoAvaliacao tipo;

    public Avaliacao(String descricao, double valor, Modulo modulo, TipoAvaliacao tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.modulo = modulo;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public TipoAvaliacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoAvaliacao tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String nomeModulo = modulo != null ? modulo.getNome() : "sem módulo";
        return tipo + ": " + descricao + " | Valor: " + valor + " | Módulo: " + nomeModulo;
    }
}
