package dados;

import java.math.BigDecimal;

public class Nota {
    private Avaliacao avaliacao;
    private BigDecimal pontuacao;
    private String observacoes;

    public Nota() {
    }

    public Nota(Avaliacao avaliacao, BigDecimal pontuacao, String observacoes) {
        this.avaliacao = avaliacao;
        this.pontuacao = pontuacao;
        this.observacoes = observacoes;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public BigDecimal getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(BigDecimal pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "Nota: " + pontuacao + " (Avaliação: " + (avaliacao != null ? avaliacao.getIdentificador() : "N/A") +
                (observacoes != null && !observacoes.isBlank() ? " - Obs: " + observacoes : "") + ")";
    }
}
