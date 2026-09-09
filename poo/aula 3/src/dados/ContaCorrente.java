package dados;

public class ContaCorrente extends ContaBancaria {

    public ContaCorrente(int cpf) {
        super(cpf);
    }

    public boolean depositar(float valor) {
        if (valor > 0) {
            setSaldo(getSaldo() + valor);
            return true;
        }
        return false;
    }
}