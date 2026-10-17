package dados;

import java.util.ArrayList;

public class Dono {
    private String nome;
    private String cpf;
    private ArrayList<Animal> animais = new ArrayList<>();

    public Dono(String nome, String cpf) { this.nome = nome; this.cpf = cpf; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public ArrayList<Animal> getAnimais() { return animais; }
    public void cadastrarAnimal(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("Animal inválido.");
        if (!animais.contains(animal)) animais.add(animal);
        animal.setDono(this);
    }
    @Override public String toString() { return nome + " (CPF: " + cpf + ")"; }
}
