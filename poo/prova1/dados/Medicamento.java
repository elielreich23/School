package dados;

public class Medicamento {
    private String nome;
    private String descricao;
    private float preco;

    public Medicamento() {
    }

    public Medicamento(String nome, String descricao, float preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Medicamento: " + nome + " | Descrição: " + descricao + String.format(" | Preço: R$ %.2f", preco);
    }
}
