package apresentacao;

import dados.Contato;
import negocio.ListaTelefonica;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static ListaTelefonica listaTelefonica =
            new ListaTelefonica();

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println();
            System.out.println("===== LISTA TELEFÔNICA =====");
            System.out.println("1 - Adicionar contato");
            System.out.println("2 - Remover contato");
            System.out.println("3 - Exibir contatos");
            System.out.println("4 - Buscar contatos");
            System.out.println("0 - Sair");
            System.out.println("============================");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    adicionarContato();
                    break;

                case 2:
                    removerContato();
                    break;

                case 3:
                    exibirContatos();
                    break;

                case 4:
                    buscarContatos();
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    public static void novoContato() {

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
    
        System.out.print("Digite o telefone: ");
        long telefone = scanner.nextLong();
        scanner.nextLine();
    
        Contato contato = new Contato(nome, telefone);
    
        listaTelefonica.adicionarContato(contato);
    
        System.out.println("Contato adicionado!");
    }

    public static void adicionarContato() {
        novoContato();
    }

    public static void removerContato() {

        System.out.print("Digite a inicial do contato: ");
        char inicial = scanner.nextLine().toUpperCase().charAt(0);

        List<Contato> contatos =
                listaTelefonica.buscarContatos(inicial);

        if (contatos.isEmpty()) {

            System.out.println(
                    "Nenhum contato encontrado com essa inicial."
            );

            return;
        }

        System.out.println();
        System.out.println("Contatos encontrados:");

        for (int i = 0; i < contatos.size(); i++) {

            System.out.println(
                    (i + 1) + " - " + contatos.get(i)
            );
        }

        System.out.print("Escolha o contato para remover: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > contatos.size()) {

            System.out.println("Opção inválida.");

            return;
        }

        Contato contato = contatos.get(escolha - 1);

        listaTelefonica.removerContato(contato);

        System.out.println("Contato removido!");
    }

    public static void exibirContatos() {

        for (char letra = 'A'; letra <= 'Z'; letra++) {

            List<Contato> contatos =
                    listaTelefonica.buscarContatos(letra);

            System.out.println(letra + ":");

            for (Contato contato : contatos) {

                System.out.println(
                        "- " + contato.getNome()
                        + ": " + contato.getTelefone()
                );
            }
        }
    }

    public static void buscarContatos() {

        System.out.print("Digite a inicial: ");

        char inicial =
                scanner.nextLine().toUpperCase().charAt(0);

        List<Contato> contatos =
                listaTelefonica.buscarContatos(inicial);

        System.out.println();
        System.out.println(inicial + ":");

        if (contatos.isEmpty()) {

            System.out.println(
                    "- Nenhum contato encontrado."
            );

            return;
        }

        for (Contato contato : contatos) {

            System.out.println(
                    "- " + contato.getNome()
                    + ": " + contato.getTelefone()
            );
        }
    }
}