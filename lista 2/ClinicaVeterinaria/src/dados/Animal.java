package dados;

public class Animal {
    private String nome;
    private String especie;
    private int idade;
    private Dono dono;

    public Animal(String nome, String especie, int idade) {
        this.nome = nome; this.especie = especie; this.idade = idade;
    }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public int getIdade() { return idade; }
    public Dono getDono() { return dono; }
    public void setDono(Dono dono) { this.dono = dono; }
    @Override public String toString() { return nome + " (" + especie + ", " + idade + " anos)"; }
}
