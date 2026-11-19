package apresentacao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import dados.*;
import negocio.Biblioteca;
import excecoes.DataDeDevolucaoExcedidaException;
import excecoes.LimiteDeItensExcedidoException;

public class Principal {
    private static final Biblioteca biblioteca = new Biblioteca();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            menu();
            System.out.print("Escolha uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: adicionarUsuario(); break;
                case 2: adicionarItem(); break;
                case 3: mostrarItens(); break;
                case 4: emprestarItem(); break;
                case 5: devolverItem(); break;
                case 6: renovarEmprestimo(); break;
                case 7: mostrarUsuariosComItens(); break;
                case 8: mostrarItensComUsuarios(); break;
                case 9: verificarAtraso(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
        sc.close();
    }

    private static void menu() {
        System.out.println("\\n===== BIBLIOTECA =====");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Cadastrar item");
        System.out.println("3 - Mostrar itens");
        System.out.println("4 - Emprestar item");
        System.out.println("5 - Devolver item");
        System.out.println("6 - Renovar emprestimo");
        System.out.println("7 - Mostrar usuarios e itens emprestados");
        System.out.println("8 - Mostrar itens e usuarios");
        System.out.println("9 - Verificar atrasos");
        System.out.println("0 - Sair");
    }

    private static void adicionarUsuario() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        System.out.println("1 - Aluno | 2 - Professor");
        int tipo = sc.nextInt();
        sc.nextLine();

        Usuario usuario;
        if (tipo == 1) {
            usuario = new Aluno(login, senha);
        } else if (tipo == 2) {
            usuario = new Professor(login, senha);
        } else {
            System.out.println("Tipo invalido.");
            return;
        }

        biblioteca.cadastrarUsuario(usuario);
        System.out.println("Usuario cadastrado.");
    }

    private static void adicionarItem() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Titulo: ");
        String titulo = sc.nextLine();
        System.out.print("Autor: ");
        String autor = sc.nextLine();
        System.out.print("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine();

        System.out.println("1 - Livro | 2 - Monografia");
        int tipo = sc.nextInt();
        sc.nextLine();

        Item item;
        if (tipo == 1) {
            item = new Livro(id, titulo, autor, ano);
        } else if (tipo == 2) {
            item = new Monografia(id, titulo, autor, ano);
        } else {
            System.out.println("Tipo invalido.");
            return;
        }

        biblioteca.cadastrarItem(item);
        System.out.println("Item cadastrado.");
    }

    private static void mostrarItens() {
        for (Item item : biblioteca.getItens()) {
            System.out.println(item.getIdItem() + " - " + item.getTitulo()
                + " - " + (item.estaEmprestado() ? "Emprestado" : "Disponivel"));
        }
    }

    private static Usuario buscarUsuarioPorLogin(String login) {
        for (Usuario u : biblioteca.getUsuarios()) {
            if (u.getLogin().equals(login)) return u;
        }
        return null;
    }

    private static Item buscarItemPorId(int id) {
        for (Item item : biblioteca.getItens()) {
            if (item.getIdItem() == id) return item;
        }
        return null;
    }

    private static Emprestimo buscarEmprestimoPorId(int id) {
        for (Emprestimo e : biblioteca.getEmprestimos()) {
            if (e.getIdEmprestimo() == id) return e;
        }
        return null;
    }

    private static void emprestarItem() {
        System.out.print("Login do usuario: ");
        Usuario usuario = buscarUsuarioPorLogin(sc.nextLine());
        if (usuario == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }

        System.out.print("ID do item: ");
        Item item = buscarItemPorId(sc.nextInt());
        sc.nextLine();
        if (item == null) {
            System.out.println("Item nao encontrado.");
            return;
        }

        try {
            if (biblioteca.emprestar(usuario, item)) {
                System.out.println("Emprestimo realizado.");
            } else {
                System.out.println("Item ja esta emprestado.");
            }
        } catch (LimiteDeItensExcedidoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void devolverItem() {
        System.out.print("ID do item: ");
        Item item = buscarItemPorId(sc.nextInt());
        sc.nextLine();
        if (item == null) {
            System.out.println("Item nao encontrado.");
            return;
        }

        for (Emprestimo e : biblioteca.getEmprestimosItem(item)) {
            try {
                biblioteca.devolver(e, item);
                System.out.println("Devolucao realizada.");
            } catch (DataDeDevolucaoExcedidaException ex) {
                System.out.println(ex.getMessage());
            }
            return;
        }
        System.out.println("Este item nao possui emprestimo ativo.");
    }

    private static void renovarEmprestimo() {
        System.out.print("ID do emprestimo: ");
        int id = sc.nextInt();
        sc.nextLine();

        Emprestimo e = buscarEmprestimoPorId(id);
        if (e == null) {
            System.out.println("Emprestimo nao encontrado.");
            return;
        }

        biblioteca.renovar(e);
        System.out.println("Emprestimo renovado.");
    }

    private static void mostrarUsuariosComItens() {
        for (Usuario u : biblioteca.getUsuarios()) {
            System.out.println("\\nUsuario: " + u.getLogin());
            for (Emprestimo e : biblioteca.getEmprestimosUsuario(u)) {
                for (Item item : e.getItens()) {
                    System.out.println(" - " + item.getTitulo());
                }
            }
        }
    }

    private static void mostrarItensComUsuarios() {
        for (Item item : biblioteca.getItensEmprestados()) {
            System.out.println("\\nItem: " + item.getTitulo());
            for (Emprestimo e : biblioteca.getEmprestimosItem(item)) {
                System.out.println("Emprestado para: " + e.getUsuario().getLogin());
            }
        }
    }

    private static void verificarAtraso() {
        LocalDate hoje = LocalDate.now();

        for (Item item : biblioteca.getItensEmprestados()) {
            LocalDate prevista = item.getDataDevolucao();

            if (hoje.isAfter(prevista)) {
                LocalDate inicio = item.getDataUltimaPenalidade() == null
                    ? prevista : item.getDataUltimaPenalidade();

                long dias = ChronoUnit.DAYS.between(inicio, hoje);
                if (dias > 0) {
                    for (Emprestimo e : biblioteca.getEmprestimosItem(item)) {
                        e.getUsuario().acrescentarPenalidade((int) dias);
                    }
                    item.setDataUltimaPenalidade(hoje);
                }
            }
        }
        System.out.println("Penalidades atualizadas.");
    }
}
