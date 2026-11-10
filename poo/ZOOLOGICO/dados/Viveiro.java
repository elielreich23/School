package dados;

import exceptions.EspacoIndisponivelException;
import java.util.ArrayList;
import java.util.List;

public class Viveiro {

    private String nome;
    private float comprimento;
    private float largura;
    protected List<Animal> animais;

    public Viveiro(float comprimento, float largura) {
        this("", comprimento, largura);
    }

    public Viveiro(String nome, float comprimento, float largura) {
        this.nome = nome;
        this.comprimento = comprimento;
        this.largura = largura;
        this.animais = new ArrayList<>();
    }

    public float calculaEspaco() {
        return comprimento * largura;
    }

    public float espacoOcupado() {
        float total = 0;
        for (Animal animal : animais) {
            total += animal.calculaEspacoOcupado();
        }
        return total;
    }

    public float areaDisponivel() {
        return (calculaEspaco() * 0.7f) - espacoOcupado();
    }

    public boolean espacoDisponivel() {
        return areaDisponivel() > 0;
    }

    public boolean adicionarAnimal(Animal animal) throws EspacoIndisponivelException {
        if (animal instanceof Peixe && !(this instanceof Aquario)) {
            throw new EspacoIndisponivelException("Peixes não podem ser alocados em viveiros convencionais, apenas em aquários.");
        }

        if (animal.calculaEspacoOcupado() > areaDisponivel()) {
            throw new EspacoIndisponivelException("Espaço indisponível no viveiro: a área requerida ("
                    + animal.calculaEspacoOcupado() + ") excede a área disponível (" + areaDisponivel() + ").");
        }

        animais.add(animal);
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getComprimento() {
        return comprimento;
    }

    public void setComprimento(float comprimento) {
        this.comprimento = comprimento;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public List<Animal> getAnimais() {
        return animais;
    }
}
