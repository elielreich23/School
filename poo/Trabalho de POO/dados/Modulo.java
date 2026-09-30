package dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modulo {
    private int numero;
    private String nome;
    private int cargaHoraria;
    private String horarioAprendizagem;
    private List<Modulo> preRequisitos = new ArrayList<>();
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public Modulo() {
    }

    public Modulo(int numero, String nome, int cargaHoraria, String horarioAprendizagem) {
        this.numero = numero;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.horarioAprendizagem = horarioAprendizagem;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getHorarioAprendizagem() {
        return horarioAprendizagem;
    }

    public void setHorarioAprendizagem(String horarioAprendizagem) {
        this.horarioAprendizagem = horarioAprendizagem;
    }

    public List<Modulo> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(List<Modulo> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public void adicionarPreRequisito(Modulo preRequisito) {
        if (preRequisito != null && !preRequisitos.contains(preRequisito)) {
            preRequisitos.add(preRequisito);
        }
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao != null && !avaliacoes.contains(avaliacao)) {
            avaliacoes.add(avaliacao);
            avaliacao.setModulo(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Modulo modulo)) return false;
        return numero == modulo.numero && Objects.equals(nome, modulo.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nome);
    }

    @Override
    public String toString() {
        return "Módulo " + numero + ": " + nome + " (" + cargaHoraria + "h, horário sugerido: " + horarioAprendizagem + ")";
    }
}
