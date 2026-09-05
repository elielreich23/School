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
    private double valorFinal; // novo atributo

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

    /**
     * Construtor que cria um RegistroVenda com array de livros vazio.
     *
     * @param idVenda    identificador da venda
     * @param cliente    cliente que realizou a compra
     * @param funcionario funcionário que realizou a venda
     */
    public RegistroVenda(String idVenda, Cliente cliente, Funcionario funcionario) {
        this(idVenda, cliente, funcionario, new Livro[0]);
    }

    // Getters e setters
    public String getIdVenda() { return idVenda; }
    public double getValorFinal() { return valorFinal; }
    public void setValorFinal(double valorFinal) { this.valorFinal = valorFinal; }
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

    /**
     * Verifica se há livros repetidos na venda usando equals().
     */
    public boolean possuiLivrosRepetidos() {
        for (int i = 0; i < livros.length; i++) {
            for (int j = i + 1; j < livros.length; j++) {
                if (livros[i] != null && livros[i].equals(livros[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calcula o valor final aplicando 20% de desconto se houver livros repetidos.
     */
    public void calcularValorFinal() {
        double total = calcularValorVenda();
        if (possuiLivrosRepetidos()) {
            valorFinal = total * 0.80; // 20% de desconto
        } else {
            valorFinal = total;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RegistroVenda{");
        sb.append("idVenda='").append(idVenda).append('\'');
        sb.append(", cliente=").append(cliente);
        sb.append(", funcionario=").append(funcionario);
        sb.append(", livros=").append(Arrays.toString(livros));
        sb.append(", valorTotal=").append(String.format("R$ %.2f", calcularValorVenda()));
        if (valorFinal > 0) {
            sb.append(", valorFinal=").append(String.format("R$ %.2f", valorFinal));
        }
        sb.append('}');
        return sb.toString();
    }
}
