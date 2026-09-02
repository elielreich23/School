package pkg.apresentacao;

import pkg.dados.Contato;
import pkg.negocio.ListaTelefonica;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final ListaTelefonica LISTA_TELEFONICA = new ListaTelefonica();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            exibirMenu();
            String opcao = SCANNER.nextLine().trim();

            switch (opcao) {
                case "1" -> adicionarContato();
                case "2" -> removerContato();
                case "3" -> exibirContatos();
                case "4" -> solicitarEExibirPorLetra();
                case "0" -> {
                    System.out.println("Encerrando aplicação...");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static Contato novoContato() {
        System.out.print("Nome: ");
        String nome = SCANNER.nextLine().trim();
        System.out.print("Telefone: ");
        int telefone = Integer.parseInt(SCANNER.nextLine().trim());
        return new Contato(nome, telefone);
    }

    public static void adicionarContato() {
        Contato contato = novoContato();
        LISTA_TELEFONICA.adicionarContato(contato);
        System.out.println("Contato adicionado com sucesso!");
    }

    public static void removerContato() {
        System.out.print("Digite a inicial do contato: ");
        char inicial = lerLetra();
        List<Contato> encontrados = LISTA_TELEFONICA.buscarContatos(inicial);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum contato encontrado com a letra '" + inicial + "'.");
            return;
        }

        for (int i = 0; i < encontrados.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, encontrados.get(i));
        }

        System.out.print("Escolha o número do contato a remover: ");
        int indice = Integer.parseInt(SCANNER.nextLine().trim()) - 1;

        if (indice >= 0 && indice < encontrados.size()) {
            LISTA_TELEFONICA.removerContato(encontrados.get(indice));
            System.out.println("Contato removido!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public static void exibirContatos() {
        LISTA_TELEFONICA.buscarContatos().forEach((letra, lista) -> {
            System.out.println(letra + ":");
            lista.forEach(c -> System.out.println("- " + c.nome() + ": " + c.telefone()));
        });
    }

    public static void exibirContatos(char letra) {
        char letraUpper = Character.toUpperCase(letra);
        System.out.println(letraUpper + ":");
        LISTA_TELEFONICA.buscarContatos(letraUpper)
            .forEach(c -> System.out.println("- " + c.nome() + ": " + c.telefone()));
    }

    private static void solicitarEExibirPorLetra() {
        System.out.print("Informe a letra: ");
        exibirContatos(lerLetra());
    }

    private static char lerLetra() {
        String input = SCANNER.nextLine().trim();
        return input.isEmpty() ? ' ' : Character.toUpperCase(input.charAt(0));
    }

    private static void exibirMenu() {
        System.out.println("\n--- LISTA TELEFÔNICA ---");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Exibir Todos os Contatos");
        System.out.println("4 - Exibir Contatos por Letra");
        System.out.println("0 - Sair");
        System.out.print("Opção: ");
    }
}