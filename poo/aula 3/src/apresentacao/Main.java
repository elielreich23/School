package apresentacao;

import dados.ContaBancaria;
import dados.ContaCorrente;
import dados.ContaSalario;
import negocio.Sistema;
import java.util.Scanner;

public class Main {
    private static Sistema sistema = new Sistema();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU BANCO ---");
            System.out.println("1 - Cadastrar Conta Corrente");
            System.out.println("2 - Cadastrar Conta Salário");
            System.out.println("3 - Realizar Saque");
            System.out.println("4 - Realizar Depósito");
            System.out.println("5 - Ver Extrato");
            System.out.println("6 - Listar Todas as Contas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarContaCorrente();
                case 2 -> cadastrarContaSalario();
                case 3 -> realizarSaque();
                case 4 -> realizarDeposito();
                case 5 -> verExtrato();
                case 6 -> listarContas();
                case 0 -> System.out.println("Encerrando a aplicação...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarContaCorrente() {
        System.out.print("Informe o CPF: ");
        int cpf = Integer.parseInt(scanner.nextLine());
        ContaCorrente cc = new ContaCorrente(cpf);
        sistema.cadastrarConta(cc);
        System.out.println("Conta Corrente cadastrada com sucesso!");
    }

    private static void cadastrarContaSalario() {
        System.out.print("Informe o CPF: ");
        int cpf = Integer.parseInt(scanner.nextLine());
        System.out.print("Informe o CNPJ da Empresa: ");
        int cnpj = Integer.parseInt(scanner.nextLine());
        ContaSalario cs = new ContaSalario(cpf, cnpj);
        sistema.cadastrarConta(cs);
        System.out.println("Conta Salário cadastrada com sucesso!");
    }

    private static ContaBancaria buscarContaPorCpf(int cpf) {
        for (ContaBancaria c : sistema.getContas()) {
            if (c.getCpf() == cpf) {
                return c;
            }
        }
        return null;
    }

    private static void realizarSaque() {
        System.out.print("Informe o CPF da conta: ");
        int cpf = Integer.parseInt(scanner.nextLine());
        ContaBancaria conta = buscarContaPorCpf(cpf);

        if (conta != null) {
            System.out.print("Informe o valor do saque: ");
            float valor = Float.parseFloat(scanner.nextLine());
            sistema.realizarSaque(conta, valor);
            System.out.println("Operação de saque processada!");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void realizarDeposito() {
        System.out.print("Informe o CPF da conta: ");
        int cpf = Integer.parseInt(scanner.nextLine());
        ContaBancaria conta = buscarContaPorCpf(cpf);

        if (conta instanceof ContaCorrente cc) {
            System.out.print("Informe o valor do depósito: ");
            float valor = Float.parseFloat(scanner.nextLine());
            if (sistema.realizarDeposito(cc, valor)) {
                System.out.println("Depósito realizado com sucesso!");
            } else {
                System.out.println("Falha ao efetuar depósito.");
            }
        } else if (conta instanceof ContaSalario cs) {
            System.out.print("Informe o valor do depósito: ");
            float valor = Float.parseFloat(scanner.nextLine());
            System.out.print("Informe o CNPJ da Empresa pagadora: ");
            int cnpj = Integer.parseInt(scanner.nextLine());
            
            if (sistema.realizarDeposito(cs, valor, cnpj)) {
                System.out.println("Depósito realizado com sucesso!");
            } else {
                System.out.println("Falha ao efetuar depósito (CNPJ incompatível ou valor inválido).");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void verExtrato() {
        System.out.print("Informe o CPF da conta: ");
        int cpf = Integer.parseInt(scanner.nextLine());
        ContaBancaria conta = buscarContaPorCpf(cpf);

        if (conta != null) {
            System.out.println(sistema.obterExtrato(conta));
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void listarContas() {
        ContaBancaria[] contas = sistema.getContas();
        if (contas.length == 0) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        System.out.println("\n--- LISTA DE CONTAS ---");
        for (ContaBancaria c : contas) {
            String tipo = (c instanceof ContaCorrente) ? "Corrente" : "Salário";
            System.out.println("Tipo: " + tipo + " | CPF: " + c.getCpf() + " | " + c.gerarExtrato());
        }
    }
}