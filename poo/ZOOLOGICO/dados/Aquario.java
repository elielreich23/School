package dados;

public class Aquario extends Viveiro {

    private float altura;
    private float temperatura;

    public Aquario(String nome, float comprimento, float largura,
                   float altura, float temperatura) {

        super(nome, comprimento, largura);

        this.altura = altura;
        this.temperatura = temperatura;
    }

    @Override
    public float calculaEspaco() {

        return getComprimento()
                * getLargura()
                * altura;
    }

    @Override
    public boolean adicionarAnimal(Animal animal) {

        if (!(animal instanceof Peixe)) {
            return false;
        }

        Peixe peixe = (Peixe) animal;

        // Verifica a temperatura ideal do peixe
        if (temperatura < peixe.getTemperaturaIdeal() - 3 ||
            temperatura > peixe.getTemperaturaIdeal() + 3) {

            return false;
        }

        return super.adicionarAnimal(animal);
    }

    public float getAltura() {
        return altura;
    }

    public float getTemperatura() {
        return temperatura;
    }
}