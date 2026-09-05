/*
 * Classe Funcionario para a lista 1 (atividade de POO).
 * Sem declaração de pacote, fica no diretório raiz "lista 1".
 */
public class Funcionario {
    private String nome;
    private String cpf;
    private String cargo;

    // Construtor completo
    public Funcionario(String nome, String cpf, String cargo) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
    }

    // Construtor com nome e cargo (cpf opcional)
    public Funcionario(String nome, String cargo) {
        this.nome = nome;
        this.cpf = null;
        this.cargo = cargo;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public String toString() {
        return "Funcionario{nome='" + nome + "', cpf='" + cpf + "', cargo='" + cargo + "'}";
    }
}
