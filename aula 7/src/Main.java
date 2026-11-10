import dados.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.ToDoubleFunction;

public class Main {
    private static final String[] NOMES = {
        "Naturais", "Abundantes", "Fibonacci", "Fatoriais",
        "Números primos", "Quadrados", "Perfeitos"
    };

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                int opcao = lerInteiro(scanner, "Escolha uma opção: ");

                if (opcao == 0) {
                    continuar = false;
                } else if (opcao >= 1 && opcao <= 7) {
                    Gerador gerador = criarGerador(opcao);
                    int quantidade = lerInteiro(scanner, "Quantos termos deseja gerar? ");
                    if (quantidade < 1) {
                        System.out.println("Informe uma quantidade maior que zero.");
                        continue;
                    }
                    gerador.gerar(quantidade);
                    mostrarSequencia(NOMES[opcao - 1], gerador);
                } else if (opcao == 8) {
                    int quantidade = lerInteiro(scanner, "Quantos termos por sequência? ");
                    if (quantidade < 1) {
                        System.out.println("Informe uma quantidade maior que zero.");
                        continue;
                    }
                    compararTodas(quantidade);
                } else {
                    System.out.println("Opção inválida. Escolha um número de 0 a 8.");
                }
            }
            System.out.println("Programa encerrado.");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== GERADOR DE SEQUÊNCIAS =====");
        for (int i = 0; i < NOMES.length; i++) System.out.printf("%d - %s%n", i + 1, NOMES[i]);
        System.out.println("8 - Comparar todas as sequências");
        System.out.println("0 - Sair");
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static Gerador criarGerador(int opcao) {
        return switch (opcao) {
            case 1 -> new Naturais();
            case 2 -> new Abundantes();
            case 3 -> new Fibonacci();
            case 4 -> new Fatoriais();
            case 5 -> new NumeroPrimos();
            case 6 -> new Quadrados();
            case 7 -> new Perfeitos();
            default -> throw new IllegalArgumentException("Opção de gerador inválida.");
        };
    }

    private static void mostrarSequencia(String nome, Gerador gerador) {
        System.out.println("\nSequência: " + nome);
        System.out.println("Termos gerados (" + gerador.getSequencia().size() + "): " + gerador.getSequencia());
        if (gerador.getSequencia().isEmpty()) return;
        System.out.println("Termo sorteado: " + gerador.sortear());
        System.out.println("Somatório: " + gerador.somatorio());
        System.out.printf("Média aritmética: %.4f%n", gerador.mediaAritmetica());
        System.out.printf("Média geométrica: %.4f%n", gerador.mediaGeometrica());
        System.out.printf("Variância: %.4f%n", gerador.variancia());
        System.out.printf("Desvio padrão: %.4f%n", gerador.desvioPadrao());
        System.out.println("Amplitude: " + gerador.amplitude());
        if (gerador instanceof Perfeitos && gerador.getSequencia().size() < 10) {
            System.out.println("Aviso: o tipo Integer do diagrama comporta somente cinco números perfeitos positivos.");
        }
    }

    private static void compararTodas(int quantidade) {
        Map<String, Gerador> geradores = new LinkedHashMap<>();
        for (int i = 1; i <= NOMES.length; i++) {
            Gerador gerador = criarGerador(i);
            gerador.gerar(quantidade);
            geradores.put(NOMES[i - 1], gerador);
            System.out.printf("%s (%d termos): %s%n", NOMES[i - 1], gerador.getSequencia().size(), gerador.getSequencia());
        }
        System.out.println("\n===== MAIORES VALORES =====");
        comparar(geradores, "somatório", Gerador::somatorio);
        comparar(geradores, "média aritmética", Gerador::mediaAritmetica);
        comparar(geradores, "média geométrica", Gerador::mediaGeometrica);
        comparar(geradores, "variância", Gerador::variancia);
        comparar(geradores, "desvio padrão", Gerador::desvioPadrao);
        comparar(geradores, "amplitude", Gerador::amplitude);
        if (geradores.get("Perfeitos").getSequencia().size() < quantidade) {
            System.out.println("Nota: Perfeitos tem menos termos que o solicitado porque Integer não representa os demais valores.");
        }
    }

    private static void comparar(Map<String, Gerador> geradores, String criterio,
                                 ToDoubleFunction<Gerador> medida) {
        String nomeMaximo = "";
        double maximo = Double.NEGATIVE_INFINITY;
        for (Map.Entry<String, Gerador> item : geradores.entrySet()) {
            double valor = medida.applyAsDouble(item.getValue());
            if (valor > maximo) {
                maximo = valor;
                nomeMaximo = item.getKey();
            }
        }
        System.out.printf("Maior %s: %s (%.4f)%n", criterio, nomeMaximo, maximo);
    }
}
