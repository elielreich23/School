package dados;

public class Peixe extends Animal {

    private float temperaturaIdeal;

    public Peixe(String nome, String cor, String especie, int idade,
                 float largura, float comprimento, float altura,
                 float temperaturaIdeal) {

        super(nome, cor, especie, idade,
                largura, comprimento, altura);

        this.temperaturaIdeal = temperaturaIdeal;
    }

    public float getTemperaturaIdeal() {
        return temperaturaIdeal;
    }

    @Override
    public float calculaEspacoOcupado() {
        return getLargura() * getComprimento() * getAltura();
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nTemperatura ideal: " + temperaturaIdeal;
    }
}