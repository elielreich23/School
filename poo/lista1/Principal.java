

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal da Livraria – versão interativa (Exercício 1).
 *
 * Permite cadastrar clientes, funcionários, livros e criar registros de venda,
 * além de listar e calcular valores via um menu de texto.
 */
public class Principal {

    // Coleções em memória
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Funcionario> funcionarios = new ArrayList<>();
    private static final List<Livro> livros = new ArrayList<>();
    private static final List<RegistroVenda> vendas = new ArrayList<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            System.out.println(); // espaçamento
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarFuncionario();
                case 3 -> cadastrarLivro();
                case 4 -> criarVenda();
                case 5 -> listarClientes();
                case 6 -> listarFuncionarios();
                case 7 -> listarLivros();
                case 8 -> listarVendas();
                case 9 -> calcularValorVenda();
                case 0 -> System.out.println("Encerrando o sistema...");
                case 10 -> verificarLivrosRepetidos();
                case 11 -> calcularValorFinalVenda();
                case 12 -> consultaCompletaVenda();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println(); // espaçamento entre execuções
        } while (opcao != 0);
        scanner.close();
    }

    /*
     * -------------------------------------------------------------
     * MENU
     * -------------------------------------------------------------
     */
    private static void exibirMenu() {
        System.out.println("========================================");
        System.out.println("SISTEMA DA LIVRARIA");
        System.out.println("===================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar funcionário");
        System.out.println("3 - Cadastrar livro");
        System.out.println("4 - Criar registro de venda");
        System.out.println("5 - Listar clientes");
        System.out.println("6 - Listar funcionários");
        System.out.println("7 - Listar livros");
        System.out.println("8 - Listar vendas");
        System.out.println("9 - Calcular valor de uma venda");
        System.out.println("10 - Verificar livros repetidos em uma venda");
        System.out.println("11 - Calcular valor final (desconto) de uma venda");
        System.out.println("12 - Consulta completa de venda");
        System.out.println("0 - Sair");
        System.out.println("========================================");
    }

    /*
     * -------------------------------------------------------------
     * OPÇÕES DO MENU
     * -------------------------------------------------------------
     */
    private static void cadastrarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF (ou deixe vazio): ");
        String cpf = scanner.nextLine();
        cpf = cpf.isBlank() ? null : cpf;

        System.out.print("Digite o e‑mail: ");
        String email = scanner.nextLine();

        Cliente cliente;
        if (cpf != null && !cpf.isBlank()) {
            cliente = new Cliente(nome, cpf, email);
        } else {
            cliente = new Cliente(nome, email); // construtor com cpf opcional
        }

        clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
        System.out.println(cliente);
    }

    private static void cadastrarFuncionario() {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF (ou deixe vazio): ");
        String cpf = scanner.nextLine();
        cpf = cpf.isBlank() ? null : cpf;

        System.out.print("Digite o cargo: ");
        String cargo = scanner.nextLine();

        Funcionario funcionario;
        if (cpf != null && !cpf.isBlank()) {
            funcionario = new Funcionario(nome, cpf, cargo);
        } else {
            funcionario = new Funcionario(nome, cargo); // construtor com cpf opcional
        }

        funcionarios.add(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!");
        System.out.println(funcionario);
    }

    private static void cadastrarLivro() {
        System.out.print("Digite o ISBN do livro: ");
        String isbn = scanner.nextLine();

        System.out.print("Digite o título: ");
        String titulo = scanner.nextLine();

        double preco = lerDouble("Digite o preço: ");

        // Autor opcional – usamos o construtor completo com autor null
        Livro livro = new Livro(titulo, isbn, preco);
        livros.add(livro);
        System.out.println("Livro cadastrado com sucesso!");
        System.out.println(livro);
    }

    private static void criarVenda() {
        if (clientes.isEmpty() || funcionarios.isEmpty() || livros.isEmpty()) {
            System.out.println("É necessário ter ao menos um cliente, um funcionário e um livro para criar uma venda.");
            return;
        }

        System.out.print("Digite o código da venda: ");
        String codigo = scanner.nextLine();

        // Selecionar cliente
        System.out.println("Clientes cadastrados:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, clientes.get(i).getNome());
        }
        int idxCliente = lerInteiro("Escolha o cliente (número): ") - 1;
        if (idxCliente < 0 || idxCliente >= clientes.size()) {
            System.out.println("Cliente inexistente.");
            return;
        }
        Cliente cliente = clientes.get(idxCliente);

        // Selecionar funcionário
        System.out.println("Funcionários cadastrados:");
        for (int i = 0; i < funcionarios.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, funcionarios.get(i).getNome());
        }
        int idxFuncionario = lerInteiro("Escolha o funcionário (número): ") - 1;
        if (idxFuncionario < 0 || idxFuncionario >= funcionarios.size()) {
            System.out.println("Funcionário inexistente.");
            return;
        }
        Funcionario funcionario = funcionarios.get(idxFuncionario);

        // Quantidade de livros
        int qtdLivros = lerInteiro("Quantidade de livros na venda: ");
        if (qtdLivros <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        List<Livro> livrosSelecionados = new ArrayList<>();
        for (int i = 0; i < qtdLivros; i++) {
            System.out.println("Livros disponíveis:");
            for (int j = 0; j < livros.size(); j++) {
                Livro l = livros.get(j);
                System.out.printf("%d - ISBN: %s | Título: %s | Preço: R$ %.2f%n",
                        j + 1, l.getIsbn(), l.getTitulo(), l.getPreco());
            }
            int escolha = lerInteiro(String.format("Escolha o livro %d (número): ", i + 1)) - 1;
            if (escolha < 0 || escolha >= livros.size()) {
                System.out.println("Livro inexistente. Operação abortada.");
                return;
            }
            livrosSelecionados.add(livros.get(escolha));
        }

        // Cria o registro de venda (construtor completo)
        RegistroVenda venda = new RegistroVenda(codigo, cliente, funcionario,
                livrosSelecionados.toArray(new Livro[0]));
        vendas.add(venda);

        System.out.println("Venda criada com sucesso!");
        System.out.println(venda);
        System.out.println("Valor total da venda: " + String.format("R$ %.2f", venda.calcularValorVenda()));
    }

    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("=== Clientes ===");
        clientes.forEach(c -> System.out.println(c));
    }

    private static void listarFuncionarios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.println("=== Funcionários ===");
        funcionarios.forEach(f -> System.out.println(f));
    }

    private static void listarLivros() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        System.out.println("=== Livros ===");
        livros.forEach(l -> System.out.println(l));
    }

    private static void listarVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }
        System.out.println("=== Vendas ===");
        vendas.forEach(v -> System.out.println(v));
    }

    private static void calcularValorVenda() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }
        System.out.println("Vendas cadastradas:");
        for (int i = 0; i < vendas.size(); i++) {
            RegistroVenda v = vendas.get(i);
            System.out.printf("%d - Código: %s | Cliente: %s%n",
                    i + 1, v.getIdVenda(), v.getCliente().getNome());
        }
        int escolha = lerInteiro("Digite o número da venda que deseja consultar: ") - 1;
        if (escolha < 0 || escolha >= vendas.size()) {
            System.out.println("Venda inexistente.");
            return;
        }
        RegistroVenda v = vendas.get(escolha);
        double total = v.calcularValorVenda();
        System.out.println("Valor total da venda: " + String.format("R$ %.2f", total));
    }

    // New option 10
    private static void verificarLivrosRepetidos() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }
        int escolha = selecionarVenda();
        if (escolha == -1) return;
        RegistroVenda v = vendas.get(escolha);
        boolean temRepetidos = v.possuiLivrosRepetidos();
        System.out.println(temRepetidos ? "Existem livros repetidos nesta venda." : "Não há livros repetidos nesta venda.");
    }

    // New option 11
    private static void calcularValorFinalVenda() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }
        int escolha = selecionarVenda();
        if (escolha == -1) return;
        RegistroVenda v = vendas.get(escolha);
        v.calcularValorFinal();
        System.out.println("Valor final (com desconto, se houver) = " + String.format("R$ %.2f", v.getValorFinal()));
    }

    // New option 12
    private static void consultaCompletaVenda() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }
        int escolha = selecionarVenda();
        if (escolha == -1) return;
        RegistroVenda v = vendas.get(escolha);
        v.calcularValorFinal(); // ensure valorFinal is set
        System.out.println(v);
    }

    // Helper to select a sale index
    private static int selecionarVenda() {
        System.out.println("Vendas cadastradas:");
        for (int i = 0; i < vendas.size(); i++) {
            RegistroVenda v = vendas.get(i);
            System.out.printf("%d - Código: %s | Cliente: %s%n", i + 1, v.getIdVenda(), v.getCliente().getNome());
        }
        int idx = lerInteiro("Escolha o número da venda: ") - 1;
        if (idx < 0 || idx >= vendas.size()) {
            System.out.println("Venda inexistente.");
            return -1;
        }
        return idx;
    }

    /*
     * -------------------------------------------------------------
     * FUNÇÕES AUXILIARES DE ENTRADA
     * -------------------------------------------------------------
     */
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (valor < 0)
                    throw new NumberFormatException();
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número decimal positivo.");
            }
        }
    }
}
