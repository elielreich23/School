package dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import excecoes.DataDeDevolucaoExcedidaException;
import excecoes.LimiteDeItensExcedidoException;

public class Emprestimo {
    private int idEmprestimo;
    private int qntRenovado;
    private LocalDate dataEmprestimo;
    private List<Item> itens;
    private Usuario usuario;

    public Emprestimo(int idEmprestimo, Usuario usuario) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.qntRenovado = 0;
        this.dataEmprestimo = LocalDate.now();
        this.itens = new ArrayList<>();
    }

    public void emprestar(Item item, int qtdAtual)
            throws LimiteDeItensExcedidoException {
        if (item.estaEmprestado()) {
            throw new IllegalStateException("Item ja esta emprestado.");
        }
        item.emprestar(usuario, qtdAtual);
        itens.add(item);
    }

    public void devolver(Item item)
            throws DataDeDevolucaoExcedidaException {
        if (!itens.contains(item)) {
            throw new IllegalArgumentException(
                "Este item nao pertence ao emprestimo."
            );
        }

        try {
            item.devolver();
        } finally {
            itens.remove(item);
        }
    }

    public int getIdEmprestimo() { return idEmprestimo; }
    public int getQntRenovado() { return qntRenovado; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public List<Item> getItens() { return itens; }
    public Usuario getUsuario() { return usuario; }

    public void incrementarRenovacao() {
        qntRenovado++;
    }
}
