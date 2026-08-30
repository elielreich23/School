package poo;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class ExercicioImc {

    // Classe representando uma Pessoa
    public static class Pessoa implements Comparable<Pessoa> {
        private String nome;
        private int idade;
        private double altura;
        private double massa;

        public Pessoa(String nome, int idade, double altura, double massa) {
            this.nome = nome;
            this.idade = idade;
            this.altura = altura;
            this.massa = massa;
        }

        // Método para calcular o IMC (massa / altura^2)
        public double calcularIMC() {
            return massa / (altura * altura);
        }

        @Override
        public int compareTo(Pessoa outra) {
            // Ordenação decrescente de IMC
            return Double.compare(outra.calcularIMC(), this.calcularIMC());
        }

        @Override
        public String toString() {
            return String.format("%s (Idade: %d, Altura: %.2fm, Peso: %.1fkg) - IMC: %.2f", 
                    nome, idade, altura, massa, calcularIMC());
        }
    }

    // Classe representando o Grupo de pessoas com número fixo
    public static class Grupo {
        private Pessoa[] pessoas;
        private int quantidadeAtual;

        public Grupo(int tamanho) {
            this.pessoas = new Pessoa[tamanho];
            this.quantidadeAtual = 0;
        }

        // Método para adicionar uma pessoa no grupo
        public boolean adicionarPessoa(Pessoa p) {
            if (quantidadeAtual < pessoas.length) {
                pessoas[quantidadeAtual] = p;
                quantidadeAtual++;
                return true;
            }
            return false;
        }

        // Método para exibir as pessoas em ordem decrescente de IMC
        public void exibirOrdenadoPorIMC() {
            // Cria uma cópia com a quantidade atual de pessoas adicionadas
            Pessoa[] copia = Arrays.copyOf(pessoas, quantidadeAtual);
            Arrays.sort(copia);

            System.out.println("\n--- Pessoas do Grupo em Ordem Decrescente de IMC ---");
            for (Pessoa p : copia) {
                System.out.println(p);
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        // Definindo o tamanho fixo do grupo como 3 pessoas
        int tamanhoGrupo = 3;
        Grupo grupo = new Grupo(tamanhoGrupo);

        System.out.println("Cadastro de " + tamanhoGrupo + " pessoas para o grupo:");
        for (int i = 0; i < tamanhoGrupo; i++) {
            System.out.println("\nPessoa " + (i + 1) + ":");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            
            System.out.print("Idade (anos): ");
            int idade = scanner.nextInt();
            
            System.out.print("Altura (metros, ex: 1.75): ");
            double altura = scanner.nextDouble();
            
            System.out.print("Massa (kg, ex: 70.5): ");
            double massa = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer do scanner

            Pessoa p = new Pessoa(nome, idade, altura, massa);
            grupo.adicionarPessoa(p);
        }

        // Exibir resultado
        grupo.exibirOrdenadoPorIMC();

        scanner.close();
    }
}
