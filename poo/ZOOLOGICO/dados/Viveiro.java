package dados;

import java.util.ArrayList;
import java.util.List;

public class Viveiro {

    private String nome;
    private float comprimento;
    private float largura;

    private List<Animal> animais;

    public Viveiro(String nome, float comprimento, float largura) {

        this.nome = nome;
        this.comprimento = comprimento;
        this.largura = largura;

        animais = new ArrayList<>();
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

    public boolean espacoDisponivel() {

        return espacoOcupado() <= calculaEspaco() * 0.7;
    }

    public boolean adicionarAnimal(Animal animal) {

        float novoEspaco =
                espacoOcupado() + animal.calculaEspacoOcupado();

        if (novoEspaco <= calculaEspaco() * 0.7) {

            animais.add(animal);
            return true;
        }

        return false;
    }

    public String getNome() {
        return nome;
    }

    public float getComprimento() {
        return comprimento;
    }

    public float getLargura() {
        return largura;
    }

    public List<Animal> getAnimais() {
        return animais;
    }
}