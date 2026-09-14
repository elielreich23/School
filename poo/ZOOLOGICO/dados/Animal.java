package dados;

public class Animal {

    private String nome;
    private String cor;
    private String especie;
    private int idade;
    private float largura;
    private float comprimento;
    private float altura;

    public Animal(String nome, String cor, String especie, int idade,
                   float largura, float comprimento, float altura) {

        this.nome = nome;
        this.cor = cor;
        this.especie = especie;
        this.idade = idade;
        this.largura = largura;
        this.comprimento = comprimento;
        this.altura = altura;
    }

    public float calculaEspacoOcupado() {
        return largura * comprimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public String getEspecie() {
        return especie;
    }

    public int getIdade() {
        return idade;
    }

    public float getLargura() {
        return largura;
    }

    public float getComprimento() {
        return comprimento;
    }

    public float getAltura() {
        return altura;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nCor: " + cor +
                "\nEspécie: " + especie +
                "\nIdade: " + idade +
                "\nLargura: " + largura +
                "\nComprimento: " + comprimento +
                "\nAltura: " + altura;
    }
}