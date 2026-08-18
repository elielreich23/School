package poo;

import java.util.Arrays;
import java.util.Scanner;

public class OrdenarPessoas {
    
    // Classe auxiliar representando uma Pessoa para melhor organização (POO)
    static class Pessoa implements Comparable<Pessoa> {
        String nome;
        int idade;

        public Pessoa(String nome, int idade) {
            this.nome = nome;
            this.idade = idade;
        }

        @Override
        public int compareTo(Pessoa outra) {
            // Retorna comparação invertida para ordenar de forma decrescente
            return Integer.compare(outra.idade, this.idade);
        }

        @Override
        public String toString() {
            return nome + " (" + idade + " anos)";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pessoa[] pessoas = new Pessoa[5];

        System.out.println("Digite o nome e a idade de 5 pessoas:");
        for (int i = 0; i < pessoas.length; i++) {
            System.out.println("\nDados da pessoa " + (i + 1) + ":");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha pendente no buffer
            
            pessoas[i] = new Pessoa(nome, idade);
        }

        // Ordena o array decrescentemente com base na idade
        Arrays.sort(pessoas);

        System.out.println("\nPessoas listadas em ordem decrescente de idade:");
        for (Pessoa p : pessoas) {
            System.out.println(p);
        }

        scanner.close();
    }
}
