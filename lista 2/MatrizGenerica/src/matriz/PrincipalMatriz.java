package matriz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PrincipalMatriz {
    private static final int ORDEM = 5;

    public static void main(String[] args) {
        Matriz<Integer> matriz = new Matriz<>(ORDEM, ORDEM);
        List<Integer> valores = new ArrayList<>();

        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("Digite os 25 valores inteiros da matriz 5 x 5:");
            for (int i = 0; i < ORDEM; i++) {
                for (int j = 0; j < ORDEM; j++) {
                    System.out.printf("Elemento [%d][%d]: ", i, j);
                    while (!entrada.hasNextInt()) {
                        System.out.print("Valor inválido. Digite um número inteiro: ");
                        entrada.next();
                    }
                    matriz.set(entrada.nextInt(), i, j);
                }
            }
        }

        // Coleta todos os elementos usando get(), como solicitado.
        for (int i = 0; i < ORDEM; i++) {
            for (int j = 0; j < ORDEM; j++) {
                valores.add(matriz.get(i, j));
            }
        }

        Collections.sort(valores);

        // Reinsere em ordem da esquerda para a direita e de cima para baixo.
        int indice = 0;
        for (int i = 0; i < ORDEM; i++) {
            for (int j = 0; j < ORDEM; j++) {
                matriz.set(valores.get(indice++), i, j);
            }
        }

        System.out.println("\nMatriz ordenada:");
        for (int i = 0; i < ORDEM; i++) {
            for (int j = 0; j < ORDEM; j++) {
                System.out.print(matriz.get(i, j));
                if (j < ORDEM - 1) System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("\nLinhas:");
        for (int i = 0; i < ORDEM; i++) {
            System.out.println("Linha " + i + ": " + matriz.getLinha(i));
        }

        System.out.println("\nColunas:");
        for (int j = 0; j < ORDEM; j++) {
            System.out.println("Coluna " + j + ": " + matriz.getColuna(j));
        }

        System.out.println("\nDiagonal principal:");
        System.out.println(matriz.getDiagonalPrincipal());
    }
}
