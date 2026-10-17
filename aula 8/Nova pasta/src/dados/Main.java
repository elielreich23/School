package dados;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ListaTelefonica lista = new ListaTelefonica();

        int opcao;

        do {
            System.out.println("\n=== LISTA TELEFÔNICA ===");
            System.out.println("1 - Adicionar contato");
            System.out.println("2 - Exibir contatos");
            System.out.println("3 - Remover contato");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Digite um número válido: ");
                scanner.next();
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();

                    lista.adicionarContato(
                            new Contato(nome, telefone));

                    System.out.println("Contato salvo!");
                    break;

                case 2:
                    lista.exibirContatos();
                    break;

                case 3:
                    System.out.print("Nome do contato: ");
                    String nomeRemover = scanner.nextLine();

                    System.out.print("Telefone do contato: ");
                    String telefoneRemover = scanner.nextLine();

                    lista.removerContato(
                            new Contato(nomeRemover, telefoneRemover));

                    System.out.println("Operação de remoção concluída.");
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
}