import java.util.Arrays;

/**
 * Classe que representa um registro de venda em uma livraria.
 * Contém o código da venda, o cliente que comprou, o funcionário que realizou a
 * venda e os livros vendidos. Fornece método para calcular o valor total da venda.
 */
public class RegistroVenda {
    private String idVenda;
    private Cliente cliente;
    private Funcionario funcionario;
    private Livro[] livros;

    /**
     * Construtor completo.
     *
     * @param idVenda    identificador da venda
     * @param cliente    cliente que realizou a compra
     * @param funcionario funcionário que realizou a venda
     * @param livros     array de livros vendidos
     */
    public RegistroVenda(String idVenda, Cliente cliente, Funcionario funcionario, Livro[] livros) {
        this.idVenda = idVenda;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.livros = livros != null ? livros.clone() : new Livro[0];
    }

    // Getters e setters
    public String getIdVenda() { return idVenda; }
    public void setIdVenda(String idVenda) { this.idVenda = idVenda; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public Livro[] getLivros() { return livros.clone(); }
    public void setLivros(Livro[] livros) { this.livros = livros != null ? livros.clone() : new Livro[0]; }

    /**
     * Calcula o valor total da venda somando o preço de todos os livros.
     *
     * @return total da venda
     */
    public double calcularValorVenda() {
        double total = 0.0;
        for (Livro l : livros) {
            if (l != null) {
                total += l.getPreco();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "RegistroVenda{" +
                "idVenda='" + idVenda + '\'' +
                ", cliente=" + cliente +
                ", funcionario=" + funcionario +
                ", livros=" + Arrays.toString(livros) +
                '}';
    }
}
