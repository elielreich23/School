import dados.Animal;
import dados.Aquario;
import dados.Peixe;
import dados.Viveiro;
import negocio.Zoologico;

import java.util.Scanner;

public class Principal {

    static Scanner teclado = new Scanner(System.in);

    static Zoologico zoologico = new Zoologico();

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("\n===== ZOOLÓGICO =====");
            System.out.println("1 - Cadastrar animal");
            System.out.println("2 - Cadastrar peixe");
            System.out.println("3 - Cadastrar viveiro");
            System.out.println("4 - Cadastrar aquário");
            System.out.println("5 - Alocar animal");
            System.out.println("6 - Mostrar animais");
            System.out.println("7 - Mostrar viveiros");
            System.out.println("8 - Mostrar aquários");
            System.out.println("0 - Sair");

            System.out.print("Escolha uma opção: ");
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {

                case 1:
                    cadastrarAnimal();
                    break;

                case 2:
                    cadastrarPeixe();
                    break;

                case 3:
                    cadastrarViveiro();
                    break;

                case 4:
                    cadastrarAquario();
                    break;

                case 5:
                    alocarAnimal();
                    break;

                case 6:
                    mostrarAnimais();
                    break;

                case 7:
                    mostrarViveiros();
                    break;

                case 8:
                    mostrarAquarios();
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        teclado.close();
    }

    public static void cadastrarAnimal() {

        System.out.println("\n--- CADASTRAR ANIMAL ---");

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Cor: ");
        String cor = teclado.nextLine();

        System.out.print("Espécie: ");
        String especie = teclado.nextLine();

        System.out.print("Idade: ");
        int idade = teclado.nextInt();

        System.out.print("Largura: ");
        float largura = teclado.nextFloat();

        System.out.print("Comprimento: ");
        float comprimento = teclado.nextFloat();

        System.out.print("Altura: ");
        float altura = teclado.nextFloat();

        teclado.nextLine();

        Animal animal = new Animal(
                nome,
                cor,
                especie,
                idade,
                largura,
                comprimento,
                altura
        );

        zoologico.cadastrarAnimal(animal);

        System.out.println("Animal cadastrado!");
    }

    public static void cadastrarPeixe() {

        System.out.println("\n--- CADASTRAR PEIXE ---");

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Cor: ");
        String cor = teclado.nextLine();

        System.out.print("Espécie: ");
        String especie = teclado.nextLine();

        System.out.print("Idade: ");
        int idade = teclado.nextInt();

        System.out.print("Largura: ");
        float largura = teclado.nextFloat();

        System.out.print("Comprimento: ");
        float comprimento = teclado.nextFloat();

        System.out.print("Altura: ");
        float altura = teclado.nextFloat();

        System.out.print("Temperatura ideal: ");
        float temperatura = teclado.nextFloat();

        teclado.nextLine();

        Peixe peixe = new Peixe(
                nome,
                cor,
                especie,
                idade,
                largura,
                comprimento,
                altura,
                temperatura
        );

        zoologico.cadastrarAnimal(peixe);

        System.out.println("Peixe cadastrado!");
    }

    public static void cadastrarViveiro() {

        System.out.println("\n--- CADASTRAR VIVEIRO ---");

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Comprimento: ");
        float comprimento = teclado.nextFloat();

        System.out.print("Largura: ");
        float largura = teclado.nextFloat();

        teclado.nextLine();

        Viveiro viveiro = new Viveiro(
                nome,
                comprimento,
                largura
        );

        zoologico.cadastrarViveiro(viveiro);

        System.out.println("Viveiro cadastrado!");
    }

    public static void cadastrarAquario() {

        System.out.println("\n--- CADASTRAR AQUÁRIO ---");

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Comprimento: ");
        float comprimento = teclado.nextFloat();

        System.out.print("Largura: ");
        float largura = teclado.nextFloat();

        System.out.print("Altura: ");
        float altura = teclado.nextFloat();

        System.out.print("Temperatura: ");
        float temperatura = teclado.nextFloat();

        teclado.nextLine();

        Aquario aquario = new Aquario(
                nome,
                comprimento,
                largura,
                altura,
                temperatura
        );

        zoologico.cadastrarViveiro(aquario);

        System.out.println("Aquário cadastrado!");
    }

    public static void alocarAnimal() {

        System.out.println("\n--- ALOCAR ANIMAL ---");

        Animal[] animais = zoologico.getAnimais();

        if (animais.length == 0) {
            System.out.println("Não existem animais cadastrados.");
            return;
        }

        System.out.println("\nAnimais:");

        for (int i = 0; i < animais.length; i++) {
            System.out.println(i + " - " + animais[i].getNome());
        }

        System.out.print("Escolha o animal: ");
        int animalIndice = teclado.nextInt();

        Viveiro[] viveiros = zoologico.getViveiros();

        if (viveiros.length == 0) {
            System.out.println("Não existem viveiros cadastrados.");
            return;
        }

        System.out.println("\nViveiros:");

        for (int i = 0; i < viveiros.length; i++) {
            System.out.println(i + " - " + viveiros[i].getNome());
        }

        System.out.print("Escolha o viveiro: ");
        int viveiroIndice = teclado.nextInt();

        teclado.nextLine();

        Animal animal = animais[animalIndice];
        Viveiro viveiro = viveiros[viveiroIndice];

        boolean sucesso =
                zoologico.alocarAnimal(animal, viveiro);

        if (sucesso) {

            System.out.println(
                    "Animal alocado com sucesso!"
            );

        } else {

            System.out.println(
                    "Não foi possível alocar o animal."
            );
        }
    }

    public static void mostrarAnimais() {

        System.out.println("\n--- ANIMAIS DO ZOOLÓGICO ---");

        Viveiro[] viveiros = zoologico.getViveiros();

        boolean encontrou = false;

        for (Viveiro viveiro : viveiros) {

            if (!viveiro.getAnimais().isEmpty()) {

                encontrou = true;

                System.out.println(
                        "\nViveiro: " + viveiro.getNome()
                );

                for (Animal animal : viveiro.getAnimais()) {

                    System.out.println("--------------------");
                    System.out.println(
                            "Nome: " + animal.getNome()
                    );
                    System.out.println(
                            "Cor: " + animal.getCor()
                    );
                    System.out.println(
                            "Espécie: " + animal.getEspecie()
                    );

                    if (animal instanceof Peixe) {

                        Peixe peixe = (Peixe) animal;

                        System.out.println(
                                "Temperatura ideal: "
                                + peixe.getTemperaturaIdeal()
                        );
                    }
                }
            }
        }

        if (!encontrou) {

            System.out.println(
                    "O zoológico não possui animais alocados."
            );
        }
    }

    public static void mostrarViveiros() {

        System.out.println("\n--- VIVEIROS ---");

        Viveiro[] viveiros = zoologico.getViveiros();

        if (viveiros.length == 0) {

            System.out.println(
                    "Nenhum viveiro cadastrado."
            );

            return;
        }

        for (Viveiro viveiro : viveiros) {

            System.out.println("--------------------");
            System.out.println(
                    "Nome: " + viveiro.getNome()
            );
            System.out.println(
                    "Espaço: " + viveiro.calculaEspaco()
            );
            System.out.println(
                    "Espaço ocupado: "
                    + viveiro.espacoOcupado()
            );
        }
    }

    public static void mostrarAquarios() {

        System.out.println("\n--- AQUÁRIOS ---");

        Aquario[] aquarios = zoologico.getAquarios();

        if (aquarios.length == 0) {

            System.out.println(
                    "Nenhum aquário cadastrado."
            );

            return;
        }

        for (Aquario aquario : aquarios) {

            System.out.println("--------------------");
            System.out.println(
                    "Nome: " + aquario.getNome()
            );
            System.out.println(
                    "Volume: " + aquario.calculaEspaco()
            );
            System.out.println(
                    "Temperatura: "
                    + aquario.getTemperatura()
            );
        }
    }
}