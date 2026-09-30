package dados;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String nome;
    private String especie;
    private String raca;
    private String cor;
    private int anoNascimento;
    private Tutor tutor;
    private List<Atendimento> atendimentos;

    public Animal() {
        this.atendimentos = new ArrayList<>();
    }

    public Animal(String nome, String especie, String raca, String cor, int anoNascimento) {
        this();
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.anoNascimento = anoNascimento;
    }

    public Animal(String nome, String especie, String raca, String cor, int anoNascimento, Tutor tutor) {
        this(nome, especie, raca, cor, anoNascimento);
        this.tutor = tutor;
    }

    public void adicionaAtendimento(Atendimento at) {
        if (this.atendimentos == null) {
            this.atendimentos = new ArrayList<>();
        }
        this.atendimentos.add(at);
    }

    public void adicionarAtendimento(Atendimento at) {
        adicionaAtendimento(at);
    }

    public float calculaAtendimentos() {
        float total = 0.0f;
        if (atendimentos != null) {
            for (Atendimento at : atendimentos) {
                if (at != null && at.isFinalizado()) {
                    total += at.calculaValor();
                }
            }
        }
        return total;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    @Override
    public String toString() {
        String tutorNome = (tutor != null) ? tutor.getNome() : "Sem tutor";
        return "Animal [Nome: " + nome + 
               " | Espécie: " + especie + 
               " | Raça: " + raca + 
               " | Cor: " + cor + 
               " | Ano Nasc: " + anoNascimento + 
               " | Tutor: " + tutorNome + 
               String.format(" | Total Gasto em Atendimentos: R$ %.2f", calculaAtendimentos()) + 
               "]";
    }
}
