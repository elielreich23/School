package dados;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venda {
    private String numero;
    private Aluno aluno;
    private LocalDate data;
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda() {
    }

    public Venda(String numero, Aluno aluno, LocalDate data) {
        this.numero = numero;
        this.aluno = aluno;
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemVenda item) {
        if (item != null) {
            itens.add(item);
        }
    }

    public BigDecimal getValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda venda)) return false;
        return Objects.equals(numero, venda.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Venda #" + numero + " - Aluno: " + (aluno != null ? aluno.getNome() : "N/A") +
                " em " + data + " - Total: R$ " + getValorTotal() + " (" + itens.size() + " itens)";
    }
}
