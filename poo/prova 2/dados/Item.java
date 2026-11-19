package dados;

import java.time.LocalDate;
import excecoes.DataDeDevolucaoExcedidaException;
import excecoes.LimiteDeItensExcedidoException;

public abstract class Item {
    private int idItem;
    private String titulo;
    private String nomeAutor;
    private int ano;

    protected boolean emprestado;
    protected LocalDate dataEmprestimo;
    protected LocalDate dataDevolucao;
    private LocalDate dataUltimaPenalidade;

    public Item(int idItem, String titulo, String nomeAutor, int ano) {
        this.idItem = idItem;
        this.titulo = titulo;
        this.nomeAutor = nomeAutor;
        this.ano = ano;
        this.emprestado = false;
    }

    public abstract void emprestar(Usuario usuario, int qtdAtual)
            throws LimiteDeItensExcedidoException;

    public void devolver() throws DataDeDevolucaoExcedidaException {
        try {
            if (dataDevolucao != null &&
                LocalDate.now().isAfter(dataDevolucao)) {
                throw new DataDeDevolucaoExcedidaException(
                    "Devolucao realizada com atraso."
                );
            }
        } finally {
            emprestado = false;
        }
    }

    public boolean estaEmprestado() { return emprestado; }
    public int getIdItem() { return idItem; }
    public String getTitulo() { return titulo; }
    public String getNomeAutor() { return nomeAutor; }
    public int getAno() { return ano; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public LocalDate getDataUltimaPenalidade() { return dataUltimaPenalidade; }

    public void setDataDevolucao(LocalDate data) {
        this.dataDevolucao = data;
    }

    public void setDataUltimaPenalidade(LocalDate data) {
        this.dataUltimaPenalidade = data;
    }
}
