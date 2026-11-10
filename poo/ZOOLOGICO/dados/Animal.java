package dados;

import java.util.Objects;

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

    public float calculaArea() {
        return largura * comprimento;
    }

    public float calculaEspacoOcupado() {
        return calculaArea();
    }

    public boolean equals(Animal a) {
        if (a == null) {
            return false;
        }
        return Objects.equals(this.nome, a.nome) &&
               Objects.equals(this.cor, a.cor) &&
               Objects.equals(this.especie, a.especie) &&
               this.idade == a.idade &&
               Float.compare(this.largura, a.largura) == 0 &&
               Float.compare(this.comprimento, a.comprimento) == 0 &&
               Float.compare(this.altura, a.altura) == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal outro = (Animal) obj;
        return equals(outro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cor, especie, idade, largura, comprimento, altura);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public float getComprimento() {
        return comprimento;
    }

    public void setComprimento(float comprimento) {
        this.comprimento = comprimento;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
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
