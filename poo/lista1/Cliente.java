/*
 * Classe Cliente para a lista 1 (atividade de POO).
 * Não possui declaração de pacote para ficar no diretório raiz "lista 1".
 */
public class Cliente {
    private String nome;
    private String cpf;
    private String email;

    // Construtor completo
    public Cliente(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    // Construtor com nome e email (cpf opcional)
    public Cliente(String nome, String email) {
        this.nome = nome;
        this.cpf = null;
        this.email = email;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Cliente{nome='" + nome + "', cpf='" + cpf + "', email='" + email + "'}";
    }
}
