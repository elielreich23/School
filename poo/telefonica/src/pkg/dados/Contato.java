package pkg.dados;

// Record drastically reduces boilerplate for data carrier classes
public record Contato(String nome, int telefone) {
    
    @Override
    public String toString() {
        return nome + ": " + telefone;
    }
}