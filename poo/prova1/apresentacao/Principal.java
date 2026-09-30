package apresentacao;

import dados.*;
import negocio.Clinica;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private static Clinica clinica = new Clinica();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== SISTEMA CLÍNICA VETERINÁRIA ===");
            System.out.println("1 - Cadastrar Tutor");
            System.out.println("2 - Cadastrar Veterinário");
            System.out.println("3 - Cadastrar Animal");
            System.out.println("4 - Cadastrar Atendimento");
            System.out.println("5 - Ver Todas as Entidades");
            System.out.println("6 - Atendimentos por Animal");
            System.out.println("7 - Atendimentos por Tutor");
            System.out.println("8 - Atendimentos da Clínica (Total)");
            System.out.println("9 - Carregar Dados de Teste (Exemplo)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println();

            switch (opcao) {
                case 1:
                    cadastrarTutor();
                    break;
                case 2:
                    cadastrarVeterinario();
                    break;
                case 3:
                    cadastrarAnimal();
                    break;
                case 4:
                    cadastrarAtendimento();
                    break;
                case 5:
                    verTodasEntidades();
                    break;
                case 6:
                    mostrarAtendimentosPorAnimal();
                    break;
                case 7:
                    mostrarAtendimentosPorTutor();
                    break;
                case 8:
                    mostrarAtendimentosClinica();
                    break;
                case 9:
                    carregarDadosTeste();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarTutor() {
        System.out.println("--- Cadastrar Tutor ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        long cpf = Long.parseLong(scanner.nextLine());
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Tutor tutor = new Tutor(nome, cpf, telefone);
        clinica.adicionarTutor(tutor);
        System.out.println("Tutor cadastrado com sucesso!");
    }

    private static void cadastrarVeterinario() {
        System.out.println("--- Cadastrar Veterinário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        long cpf = Long.parseLong(scanner.nextLine());
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        Veterinario vet = new Veterinario(nome, cpf, telefone, especialidade);
        clinica.adicionarVeterinario(vet);
        System.out.println("Veterinário cadastrado com sucesso!");
    }

    private static void cadastrarAnimal() {
        System.out.println("--- Cadastrar Animal ---");
        List<Tutor> tutores = clinica.getTutores();
        if (tutores.isEmpty()) {
            System.out.println("Cadastre um tutor primeiro!");
            return;
        }

        System.out.println("Escolha o Tutor:");
        for (int i = 0; i < tutores.size(); i++) {
            System.out.println(i + " - " + tutores.get(i).getNome());
        }
        System.out.print("Índice do tutor: ");
        int idx = Integer.parseInt(scanner.nextLine());
        Tutor tutor = tutores.get(idx);

        System.out.print("Nome do Animal: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie: ");
        String especie = scanner.nextLine();
        System.out.print("Raça: ");
        String raca = scanner.nextLine();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Ano de Nascimento: ");
        int ano = Integer.parseInt(scanner.nextLine());

        Animal animal = new Animal(nome, especie, raca, cor, ano);
        clinica.adicionarAnimal(tutor, animal);
        System.out.println("Animal cadastrado com sucesso!");
    }

    private static void cadastrarAtendimento() {
        System.out.println("--- Cadastrar Atendimento ---");
        List<Animal> animais = clinica.getTodosAnimais();
        List<Veterinario> vets = clinica.getVeterinarios();

        if (animais.isEmpty() || vets.isEmpty()) {
            System.out.println("É necessário ter pelo menos 1 animal e 1 veterinário cadastrados!");
            return;
        }

        System.out.println("Escolha o Animal:");
        for (int i = 0; i < animais.size(); i++) {
            System.out.println(i + " - " + animais.get(i).getNome() + " (Tutor: " + animais.get(i).getTutor().getNome() + ")");
        }
        System.out.print("Índice do animal: ");
        int idxAnimal = Integer.parseInt(scanner.nextLine());
        Animal animal = animais.get(idxAnimal);

        System.out.println("Escolha o Veterinário:");
        for (int i = 0; i < vets.size(); i++) {
            System.out.println(i + " - Dr(a). " + vets.get(i).getNome());
        }
        System.out.print("Índice do veterinário: ");
        int idxVet = Integer.parseInt(scanner.nextLine());
        Veterinario vet = vets.get(idxVet);

        System.out.print("Data de Entrada: ");
        String entrada = scanner.nextLine();

        Atendimento atendimento = new Atendimento(entrada, vet);

        System.out.print("Quantos medicamentos foram usados? ");
        int qtdMeds = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdMeds; i++) {
            System.out.println("Medicamento " + (i + 1) + ":");
            System.out.print("Nome: ");
            String nomeMed = scanner.nextLine();
            System.out.print("Descrição: ");
            String descMed = scanner.nextLine();
            System.out.print("Preço: ");
            float precoMed = Float.parseFloat(scanner.nextLine().replace(",", "."));
            System.out.print("Quantidade de unidades deste medicamento: ");
            int unidades = Integer.parseInt(scanner.nextLine());

            Medicamento m = new Medicamento(nomeMed, descMed, precoMed);
            clinica.adicionarMedicamento(m);

            for (int u = 0; u < unidades; u++) {
                atendimento.adicionaMedicamento(m);
            }
        }

        System.out.print("O atendimento já foi finalizado? (s/n): ");
        String finalizado = scanner.nextLine();
        if (finalizado.equalsIgnoreCase("s")) {
            System.out.print("Data de Saída: ");
            String saida = scanner.nextLine();
            atendimento.finalizaAtendimento(saida);
        }

        clinica.adicionarAtendimento(animal, atendimento);
        System.out.printf("Atendimento cadastrado! Valor total: R$ %.2f\n", atendimento.calculaValor());
    }

    private static void verTodasEntidades() {
        System.out.println("=== TUTORES ===");
        for (Tutor t : clinica.getTutores()) {
            System.out.println(t);
        }

        System.out.println("\n=== VETERINÁRIOS ===");
        for (Veterinario v : clinica.getVeterinarios()) {
            System.out.println(v);
        }

        System.out.println("\n=== ANIMAIS ===");
        for (Animal a : clinica.getTodosAnimais()) {
            System.out.println(a);
        }

        System.out.println("\n=== TODOS OS ATENDIMENTOS ===");
        for (Animal a : clinica.getTodosAnimais()) {
            for (Atendimento at : a.getAtendimentos()) {
                System.out.println("Animal: " + a.getNome() + " | " + at);
            }
        }
    }

    private static void mostrarAtendimentosPorAnimal() {
        List<Animal> animais = clinica.getTodosAnimais();
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }

        System.out.println("Escolha o Animal:");
        for (int i = 0; i < animais.size(); i++) {
            System.out.println(i + " - " + animais.get(i).getNome());
        }
        System.out.print("Índice: ");
        int idx = Integer.parseInt(scanner.nextLine());
        Animal animal = animais.get(idx);

        System.out.println("\nAtendimentos realizados para " + animal.getNome() + ":");
        for (Atendimento at : animal.getAtendimentos()) {
            if (at.isFinalizado()) {
                System.out.println(at);
            }
        }
        System.out.printf("Total gasto com este animal: R$ %.2f\n", animal.calculaAtendimentos());
    }

    private static void mostrarAtendimentosPorTutor() {
        List<Tutor> tutores = clinica.getTutores();
        if (tutores.isEmpty()) {
            System.out.println("Nenhum tutor cadastrado.");
            return;
        }

        System.out.println("Escolha o Tutor:");
        for (int i = 0; i < tutores.size(); i++) {
            System.out.println(i + " - " + tutores.get(i).getNome());
        }
        System.out.print("Índice: ");
        int idx = Integer.parseInt(scanner.nextLine());
        Tutor tutor = tutores.get(idx);

        System.out.println("\nAtendimentos realizados para animais do tutor " + tutor.getNome() + ":");
        for (Animal a : tutor.getAnimais()) {
            System.out.println("Animal: " + a.getNome());
            for (Atendimento at : a.getAtendimentos()) {
                if (at.isFinalizado()) {
                    System.out.println("  " + at);
                }
            }
        }
        System.out.printf("Total gasto pelo tutor: R$ %.2f\n", tutor.calculaGastoAnimais());
    }

    private static void mostrarAtendimentosClinica() {
        System.out.println("=== ATENDIMENTOS REALIZADOS NA CLÍNICA ===");
        for (Animal a : clinica.getTodosAnimais()) {
            for (Atendimento at : a.getAtendimentos()) {
                if (at.isFinalizado()) {
                    System.out.println("Animal: " + a.getNome() + " (Tutor: " + a.getTutor().getNome() + ")");
                    System.out.println("  " + at);
                }
            }
        }
        System.out.printf("\nSOMATÓRIO TOTAL DA CLÍNICA: R$ %.2f\n", clinica.calculaTotalAtendimentos());
    }

    private static void carregarDadosTeste() {
        Tutor t1 = new Tutor("Ana", 11122233344L, "9999-1111");
        Tutor t2 = new Tutor("Bruno", 55566677788L, "9999-2222");
        clinica.adicionarTutor(t1);
        clinica.adicionarTutor(t2);

        Veterinario v1 = new Veterinario("Dr. Carlos", 12345678900L, "9888-3333", "Cirurgia");
        clinica.adicionarVeterinario(v1);

        Medicamento m1 = new Medicamento("Vacina", "Preventiva", 80.00f);
        Medicamento m2 = new Medicamento("Antibiotico", "50mg", 50.00f);

        Animal a1 = new Animal("Rex", "Cachorro", "Golden", "Caramelo", 2021);
        Animal a2 = new Animal("Thor", "Cachorro", "Bulldog", "Preto", 2022);
        clinica.adicionarAnimal(t1, a1);
        clinica.adicionarAnimal(t2, a2);

        // Atendimento Rex (Finalizado): 1 Vacina (80) + 2 Antibióticos (50+50) = 180
        Atendimento at1 = new Atendimento("01/09/2026", v1);
        at1.adicionaMedicamento(m1);
        at1.adicionaMedicamento(m2);
        at1.adicionaMedicamento(m2);
        at1.finalizaAtendimento("01/09/2026");
        clinica.adicionarAtendimento(a1, at1);

        // Atendimento Thor (Finalizado): 1 Vacina (80) = 80
        Atendimento at2 = new Atendimento("02/09/2026", v1);
        at2.adicionaMedicamento(m1);
        at2.finalizaAtendimento("02/09/2026");
        clinica.adicionarAtendimento(a2, at2);

        System.out.println("Dados de teste carregados com sucesso!");
    }
}
