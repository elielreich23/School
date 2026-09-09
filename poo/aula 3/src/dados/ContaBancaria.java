package dados;

public abstract class ContaBancaria {
    private int cpf;
    private float saldo;

    public ContaBancaria(int cpf) {
        this.cpf = cpf;
        this.saldo = 0.0f;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float sacar(float valor) {
        if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
            return valor;
        }
        return 0;
    }

    public String gerarExtrato() {
        return "Saldo atual: R$ " + this.saldo;
    }
}