import dados.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n========== GERADOR DE SEQUÊNCIAS ==========");
                System.out.println("1 - Naturais");
                System.out.println("2 - Abundantes");
                System.out.println("3 - Fibonacci");
                System.out.println("4 - Fatoriais");
                System.out.println("5 - Números Primos");
                System.out.println("6 - Quadrados");
                System.out.println("7 - Perfeitos");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                int opcao;
                try {
                    opcao = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida! Digite um número.");
                    continue;
                }

                if (opcao == 0) {
                    System.out.println("Encerrando o programa...");
                    break;
                }

                Gerador gerador;
                String nomeSequencia;

                switch (opcao) {
                    case 1 -> {
                        gerador = new Naturais();
                        nomeSequencia = "Naturais";
                    }
                    case 2 -> {
                        gerador = new Abundantes();
                        nomeSequencia = "Abundantes";
                    }
                    case 3 -> {
                        gerador = new Fibonacci();
                        nomeSequencia = "Fibonacci";
                    }
                    case 4 -> {
                        gerador = new Fatoriais();
                        nomeSequencia = "Fatoriais";
                    }
                    case 5 -> {
                        gerador = new NumeroPrimos();
                        nomeSequencia = "Números Primos";
                    }
                    case 6 -> {
                        gerador = new Quadrados();
                        nomeSequencia = "Quadrados";
                    }
                    case 7 -> {
                        gerador = new Perfeitos();
                        nomeSequencia = "Perfeitos";
                    }
                    default -> {
                        System.out.println("Opção inválida! Escolha um número de 0 a 7.");
                        continue;
                    }
                }

                System.out.print("Informe a quantidade de números a gerar: ");
                int quantidade;
                try {
                    quantidade = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Quantidade inválida! Digite um número inteiro.");
                    continue;
                }

                if (quantidade < 0) {
                    System.out.println("A quantidade não pode ser negativa.");
                    continue;
                }

                gerador.gerar(quantidade);
                System.out.println("Sequência de " + nomeSequencia + " (" + quantidade + " elementos):");
                System.out.println(gerador.getSequencia());
            }
        }
    }
}
