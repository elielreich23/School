package dados;

import exceptions.EspacoIndisponivelException;
import java.util.ArrayList;
import java.util.List;

public class Aquario extends Viveiro {

    private float altura;
    private float temperatura;

    public Aquario(float comprimento, float largura, float altura, float temperatura) {
        this("", comprimento, largura, altura, temperatura);
    }

    public Aquario(String nome, float comprimento, float largura,
                   float altura, float temperatura) {
        super(nome, comprimento, largura);
        this.altura = altura;
        this.temperatura = temperatura;
    }

    @Override
    public float calculaEspaco() {
        return getComprimento() * getLargura() * altura;
    }

    public float volumeDisponivel() {
        return (calculaEspaco() * 0.7f) - espacoOcupado();
    }

    @Override
    public boolean adicionarAnimal(Animal animal) throws EspacoIndisponivelException {
        if (!(animal instanceof Peixe)) {
            throw new EspacoIndisponivelException("Apenas peixes podem ser alocados em um aquário.");
        }

        Peixe peixe = (Peixe) animal;

        // Verifica a temperatura ideal do peixe (faixa de +- 3 graus)
        if (temperatura < peixe.getTemperaturaIdeal() - 3 ||
            temperatura > peixe.getTemperaturaIdeal() + 3) {
            throw new EspacoIndisponivelException("Temperatura do aquário (" + temperatura
                    + "°C) é incompatível com a temperatura ideal do peixe (" + peixe.getTemperaturaIdeal() + "°C ± 3°C).");
        }

        if (peixe.calculaVolume() > volumeDisponivel()) {
            throw new EspacoIndisponivelException("Volume indisponível no aquário: o volume do peixe ("
                    + peixe.calculaVolume() + ") excede o volume disponível (" + volumeDisponivel() + ").");
        }

        animais.add(peixe);
        return true;
    }

    public List<Peixe> getPeixes() {
        List<Peixe> peixes = new ArrayList<>();
        for (Animal a : animais) {
            if (a instanceof Peixe) {
                peixes.add((Peixe) a);
            }
        }
        return peixes;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }
}
