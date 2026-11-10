package dados;

import java.util.Objects;

public class Peixe extends Animal {

    private float temperaturaIdeal;

    public Peixe(String nome, String cor, String especie, int idade,
                 float largura, float comprimento, float altura,
                 float temperaturaIdeal) {
        super(nome, cor, especie, idade, largura, comprimento, altura);
        this.temperaturaIdeal = temperaturaIdeal;
    }

    public float calculaVolume() {
        return getLargura() * getComprimento() * getAltura();
    }

    @Override
    public float calculaEspacoOcupado() {
        return calculaVolume();
    }

    public boolean equals(Peixe p) {
        if (p == null) {
            return false;
        }
        if (!super.equals((Animal) p)) {
            return false;
        }
        return Float.compare(this.temperaturaIdeal, p.temperaturaIdeal) == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Peixe outro = (Peixe) obj;
        return equals(outro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), temperaturaIdeal);
    }

    public float getTemperaturaIdeal() {
        return temperaturaIdeal;
    }

    public void setTemperaturaIdeal(float temperaturaIdeal) {
        this.temperaturaIdeal = temperaturaIdeal;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nTemperatura ideal: " + temperaturaIdeal;
    }
}
