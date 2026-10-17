package Sistema;

import dados.Animal;
import dados.Consulta;
import dados.Dono;
import dados.Veterinario;
import negocio.Clinica;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    private static final Scanner entrada = new Scanner(System.in);
    private static final Clinica clinica = new Clinica();
    private static int proximoCodigo = 1;

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== Clínica Veterinária ===");
            System.out.println("1. Cadastrar dono\n2. Cadastrar animal\n3. Cadastrar veterinário\n4. Cadastrar consulta\n5. Listar consultas por data\n0. Sair");
            opcao = lerInt("Opção: ");
            try {
                switch (opcao) {
                    case 1: cadastrarDono(); break;
                    case 2: cadastrarAnimal(); break;
                    case 3: cadastrarVeterinario(); break;
                    case 4: cadastrarConsulta(); break;
                    case 5: listarConsultas(); break;
                    case 0: System.out.println("Programa encerrado."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Não foi possível concluir: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void cadastrarDono() {
        clinica.cadastrarDono(new Dono(lerTexto("Nome do dono: "), lerTexto("CPF: ")));
        System.out.println("Dono cadastrado.");
    }
    private static void cadastrarAnimal() {
        if (clinica.getDonos().isEmpty()) { System.out.println("Cadastre um dono antes do animal."); return; }
        Animal animal = new Animal(lerTexto("Nome do animal: "), lerTexto("Espécie: "), lerInt("Idade: "));
        Dono dono = selecionar("dono", clinica.getDonos());
        if (dono == null) return;
        dono.cadastrarAnimal(animal);
        clinica.cadastrarAnimal(animal);
        System.out.println("Animal cadastrado.");
    }
    private static void cadastrarVeterinario() {
        Veterinario vet = new Veterinario(lerTexto("Nome do veterinário: "), lerTexto("CPF: "), lerDouble("Salário: "));
        clinica.cadastrarVeterinario(vet);
        System.out.println("Veterinário cadastrado.");
    }
    private static void cadastrarConsulta() {
        if (clinica.getAnimais().isEmpty() || clinica.getVeterinarios().isEmpty()) {
            System.out.println("Cadastre ao menos um animal e um veterinário antes da consulta."); return;
        }
        LocalDate data = LocalDate.parse(lerTexto("Data (AAAA-MM-DD): "));
        LocalTime hora = LocalTime.parse(lerTexto("Horário (HH:MM): "));
        Animal animal = selecionar("animal", clinica.getAnimais());
        Veterinario vet = selecionar("veterinário", clinica.getVeterinarios());
        if (animal == null || vet == null) return;
        Consulta consulta = new Consulta(proximoCodigo++, data, hora);
        consulta.alocarAnimal(animal); consulta.alocarVeterinario(vet); clinica.marcarConsulta(consulta);
        System.out.println("Consulta cadastrada.");
    }
    private static void listarConsultas() {
        LocalDate data = LocalDate.parse(lerTexto("Data (AAAA-MM-DD): "));
        ArrayList<Consulta> consultas = clinica.listarConsultasPorData(data);
        if (consultas.isEmpty()) System.out.println("Nenhuma consulta nessa data.");
        else for (Consulta consulta : consultas) System.out.println(consulta);
    }
    private static <T> T selecionar(String tipo, ArrayList<T> itens) {
        System.out.println("Selecione " + tipo + ":");
        for (int i = 0; i < itens.size(); i++) System.out.println((i + 1) + ". " + itens.get(i));
        int indice = lerInt("Número: ") - 1;
        if (indice < 0 || indice >= itens.size()) { System.out.println("Seleção inválida."); return null; }
        return itens.get(indice);
    }
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
