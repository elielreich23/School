package poo;

import java.util.Locale;
import java.util.Scanner;

public class MediaValores {
    public static void main(String[] args) {
        // Define o locale como US para aceitar ponto como separador decimal
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        double[] valores = new double[5];
        double soma = 0;

        System.out.println("Digite 5 valores:");
        for (int i = 0; i < valores.length; i++) {
            System.out.print("Valor " + (i + 1) + ": ");
            valores[i] = scanner.nextDouble();
            soma += valores[i];
        }

        double media = soma / valores.length;
        System.out.printf("A média dos valores é: %.2f%n", media);

        scanner.close();
    }
}
