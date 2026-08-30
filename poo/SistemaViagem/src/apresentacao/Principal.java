package apresentacao;

import dados.Cidade;
import dados.Cliente;
import dados.Reserva;
import negocio.ReservaPassagem;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Principal {

    private static Scanner teclado = new Scanner(System.in);

    private static ReservaPassagem reservaPassagem = new ReservaPassagem();

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("\n===== SISTEMA DE VIAGEM =====");
            System.out.println("1 - Cadastrar cidade");
            System.out.println("2 - Cadastrar cliente");
            System.out.println("3 - Fazer reserva");
            System.out.println("4 - Mostrar reservas");
            System.out.println("5 - Mostrar cidades");
            System.out.println("6 - Mostrar clientes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {

                case 1:
                    cadastrarCidade();
                    break;

                case 2:
                    cadastrarCliente();
                    break;

                case 3:
                    fazerReserva();
                    break;

                case 4:
                    mostrarReservas();
                    break;

                case 5:
                    mostrarCidades();
                    break;

                case 6:
                    mostrarClientes();
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

    public static void cadastrarCidade() {

        System.out.println("\n--- CADASTRO DE CIDADE ---");

        System.out.print("Nome da cidade: ");
        String nome = teclado.nextLine();

        System.out.print("Estado: ");
        String estado = teclado.nextLine();

        Cidade cidade = new Cidade(nome, estado);

        reservaPassagem.cadastrarCidade(cidade);

        System.out.println("Cidade cadastrada com sucesso!");
    }

    public static void cadastrarCliente() {

        System.out.println("\n--- CADASTRO DE CLIENTE ---");

        System.out.print("CPF: ");
        long cpf = lerCpf();

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Endereço: ");
        String endereco = teclado.nextLine();

        System.out.print("Telefone: ");
        long telefone = lerTelefone();

        Cliente cliente = new Cliente(cpf, nome, endereco, telefone);

        reservaPassagem.cadastrarCliente(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void fazerReserva() {

        System.out.println("\n--- FAZER RESERVA ---");

        System.out.print("CPF do cliente: ");
        long cpf = lerCpf();

        Cliente clienteEncontrado = null;

        for (Cliente cliente : reservaPassagem.mostrarClientes()) {

            if (cliente.getCpf() == cpf) {
                clienteEncontrado = cliente;
                break;
            }
        }

        if (clienteEncontrado == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Número da reserva: ");
        int numero = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Data do voo: ");
        String data = teclado.nextLine();

        System.out.print("Hora do voo: ");
        String hora = teclado.nextLine();

        System.out.print("Preço: ");
        float preco = teclado.nextFloat();
        teclado.nextLine();

        System.out.print("Classe do voo: ");
        String classe = teclado.nextLine();

        System.out.print("Número da poltrona: ");
        int poltrona = teclado.nextInt();
        teclado.nextLine();

        System.out.println("\n--- CIDADES CADASTRADAS ---");

        Cidade[] cidades = reservaPassagem.mostrarCidades();

        for (int i = 0; i < cidades.length; i++) {
            System.out.println(i + " - " + cidades[i]);
        }

        if (cidades.length < 2) {
            System.out.println(
                    "É necessário cadastrar pelo menos duas cidades.");
            return;
        }

        System.out.print("Escolha a cidade de origem: ");
        int origemIndice = teclado.nextInt();

        System.out.print("Escolha a cidade de destino: ");
        int destinoIndice = teclado.nextInt();

        teclado.nextLine();

        Cidade origem = cidades[origemIndice];
        Cidade destino = cidades[destinoIndice];

        System.out.print("A viagem possui volta? (s/n): ");
        String resposta = teclado.nextLine();

        boolean idaEVolta = resposta.equalsIgnoreCase("s");

        Reserva ida = new Reserva(
                numero,
                data,
                hora,
                preco,
                classe,
                idaEVolta,
                poltrona,
                origem,
                destino);

        if (!idaEVolta) {

            reservaPassagem.reservarIda(
                    clienteEncontrado,
                    ida);

            System.out.println("Reserva realizada com sucesso!");

        } else {

            System.out.println("\n--- DADOS DA VOLTA ---");

            System.out.print("Número da reserva da volta: ");
            int numeroVolta = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Data da volta: ");
            String dataVolta = teclado.nextLine();

            System.out.print("Hora da volta: ");
            String horaVolta = teclado.nextLine();

            System.out.print("Preço da volta: ");
            float precoVolta = teclado.nextFloat();
            teclado.nextLine();

            System.out.print("Classe da volta: ");
            String classeVolta = teclado.nextLine();

            System.out.print("Poltrona da volta: ");
            int poltronaVolta = teclado.nextInt();
            teclado.nextLine();

            Reserva volta = new Reserva(
                    numeroVolta,
                    dataVolta,
                    horaVolta,
                    precoVolta,
                    classeVolta,
                    true,
                    poltronaVolta,
                    destino,
                    origem);

            reservaPassagem.reservarVolta(
                    clienteEncontrado,
                    ida,
                    volta);

            System.out.println(
                    "Reserva de ida e volta realizada com sucesso!");
        }
    }

    public static void mostrarReservas() {

        System.out.println("\n--- MOSTRAR RESERVAS ---");

        System.out.print("Digite o CPF do cliente: ");
        long cpf = lerCpf();

        Reserva[] reservas = reservaPassagem.mostrarReservas(cpf);

        if (reservas.length == 0) {

            System.out.println(
                    "Nenhuma reserva encontrada.");

        } else {

            for (Reserva reserva : reservas) {

                System.out.println("\n-------------------------");
                System.out.println(reserva);
            }
        }
    }

    public static void mostrarClientes() {

        System.out.println("\n--- CLIENTES CADASTRADOS ---");

        Cliente[] clientes = reservaPassagem.mostrarClientes();

        if (clientes.length == 0) {

            System.out.println("Nenhum cliente cadastrado.");

        } else {

            for (Cliente cliente : clientes) {

                System.out.println("\n-------------------------");
                System.out.println(cliente);
            }
        }
    }

    public static void mostrarCidades() {

        System.out.println("\n--- CIDADES CADASTRADAS ---");

        Cidade[] cidades = reservaPassagem.mostrarCidades();

        if (cidades.length == 0) {

            System.out.println("Nenhuma cidade cadastrada.");

        } else {

            for (Cidade cidade : cidades) {

                System.out.println(cidade);
            }
        }
    }
    private static long lerCpf() {
        while (true) {
            String entrada = teclado.nextLine();
            if (Pattern.matches("^\\d{11}$", entrada)) {
                return Long.parseLong(entrada);
            } else {
                System.out.println("CPF inválido. Digite um CPF com 11 dígitos numéricos.");
                System.out.print("CPF: ");
            }
        }
    }

    private static long lerTelefone() {
        while (true) {
            String entrada = teclado.nextLine();
            if (Pattern.matches("^\\d+$", entrada)) {
                return Long.parseLong(entrada);
            } else {
                System.out.println("Telefone inválido. Digite apenas números.");
                System.out.print("Telefone: ");
            }
        }
    }
}