package negocio;

import dados.ContaBancaria;
import dados.ContaCorrente;
import dados.ContaSalario;
import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private ContaBancaria[] contasBancarias;
    private int quantidade;

    public Sistema() {
        this.contasBancarias = new ContaBancaria[100];
        this.quantidade = 0;
    }

    public void cadastrarConta(ContaBancaria conta) {
        if (quantidade < contasBancarias.length) {
            contasBancarias[quantidade] = conta;
            quantidade++;
        }
    }

    public void realizarSaque(ContaBancaria conta, float valor) {
        if (conta != null) {
            conta.sacar(valor);
        }
    }

    public boolean realizarDeposito(ContaCorrente conta, float valor) {
        if (conta != null) {
            return conta.depositar(valor);
        }
        return false;
    }

    public boolean realizarDeposito(ContaSalario conta, float valor, int cnpj) {
        if (conta != null) {
            return conta.depositar(valor, cnpj);
        }
        return false;
    }

    public ContaBancaria[] getContas() {
        ContaBancaria[] resultado = new ContaBancaria[quantidade];
        System.arraycopy(contasBancarias, 0, resultado, 0, quantidade);
        return resultado;
    }

    public ContaCorrente[] getContasCorrentes() {
        List<ContaCorrente> lista = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            if (contasBancarias[i] instanceof ContaCorrente) {
                lista.add((ContaCorrente) contasBancarias[i]);
            }
        }
        return lista.toArray(new ContaCorrente[0]);
    }

    public ContaSalario[] getContasSalario() {
        List<ContaSalario> lista = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            if (contasBancarias[i] instanceof ContaSalario) {
                lista.add((ContaSalario) contasBancarias[i]);
            }
        }
        return lista.toArray(new ContaSalario[0]);
    }

    public String obterExtrato(ContaBancaria conta) {
        if (conta != null) {
            return conta.gerarExtrato();
        }
        return "";
    }
}