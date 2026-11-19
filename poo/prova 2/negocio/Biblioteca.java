package negocio;

import java.util.ArrayList;
import java.util.List;
import dados.*;
import excecoes.DataDeDevolucaoExcedidaException;
import excecoes.LimiteDeItensExcedidoException;

public class Biblioteca {
    private List<Usuario> usuarios;
    private List<Item> itens;
    private List<Emprestimo> emprestimos;
    private int proximoIdEmprestimo = 1;

    public Biblioteca() {
        usuarios = new ArrayList<>();
        itens = new ArrayList<>();
        emprestimos = new ArrayList<>();
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void cadastrarItem(Item item) {
        itens.add(item);
    }

    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Item> getItens() { return itens; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }

    public List<Emprestimo> getEmprestimosUsuario(Usuario usuario) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getUsuario().equals(usuario) && !e.getItens().isEmpty()) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public List<Emprestimo> getEmprestimosItem(Item item) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getItens().contains(item)) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public List<Item> getItensEmprestados() {
        List<Item> resultado = new ArrayList<>();
        for (Item item : itens) {
            if (item.estaEmprestado()) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    private int contarAtivos(Usuario usuario, Class<?> tipo) {
        int quantidade = 0;
        for (Emprestimo e : emprestimos) {
            if (e.getUsuario().equals(usuario)) {
                for (Item item : e.getItens()) {
                    if (tipo.isInstance(item)) {
                        quantidade++;
                    }
                }
            }
        }
        return quantidade;
    }

    private Emprestimo buscarEmprestimoAtivo(Usuario usuario) {
        for (Emprestimo e : emprestimos) {
            if (e.getUsuario().equals(usuario) && !e.getItens().isEmpty()) {
                return e;
            }
        }
        return null;
    }

    public boolean emprestar(Usuario usuario, Item item)
            throws LimiteDeItensExcedidoException {
        if (!usuarios.contains(usuario)) {
            throw new IllegalArgumentException("Usuario nao cadastrado.");
        }
        if (!itens.contains(item)) {
            throw new IllegalArgumentException("Item nao cadastrado.");
        }
        if (item.estaEmprestado()) {
            return false;
        }

        Class<?> tipo = (item instanceof Livro) ? Livro.class : Monografia.class;
        int qtdAtual = contarAtivos(usuario, tipo);

        Emprestimo emprestimo = buscarEmprestimoAtivo(usuario);
        boolean novoEmprestimo = (emprestimo == null);

        if (novoEmprestimo) {
            emprestimo = new Emprestimo(proximoIdEmprestimo, usuario);
        }

        // O item so e registrado depois que a validacao e o emprestimo funcionam.
        emprestimo.emprestar(item, qtdAtual);

        if (novoEmprestimo) {
            emprestimos.add(emprestimo);
            proximoIdEmprestimo++;
        }
        return true;
    }

    public void devolver(Emprestimo emprestimo, Item item)
            throws DataDeDevolucaoExcedidaException {
        if (!emprestimos.contains(emprestimo)) {
            throw new IllegalArgumentException("Emprestimo nao encontrado.");
        }
        emprestimo.devolver(item);
    }

    public void renovar(Emprestimo emprestimo) {
        if (!emprestimos.contains(emprestimo) || emprestimo.getItens().isEmpty()) {
            throw new IllegalArgumentException("Emprestimo invalido.");
        }

        for (Item item : emprestimo.getItens()) {
            int dias = (item instanceof Livro) ? 15 : 30;
            item.setDataDevolucao(item.getDataDevolucao().plusDays(dias));
            item.setDataUltimaPenalidade(null);
        }
        emprestimo.incrementarRenovacao();
    }
}
