package dados;

public class Veterinario {
    private String nome;
    private String cpf;
    private double salario;

    public Veterinario(String nome, String cpf, double salario) {
        this.nome = nome; this.cpf = cpf; this.salario = salario;
    }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public double getSalario() { return salario; }
    @Override public String toString() { return nome + " (CPF: " + cpf + ")"; }
}
