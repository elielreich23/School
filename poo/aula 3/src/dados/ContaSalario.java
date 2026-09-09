package dados;

public class ContaSalario extends ContaBancaria {
    private int cnpjEmpresa;

    public ContaSalario(int cpf, int cnpjEmpresa) {
        super(cpf);
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public int getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(int cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public boolean depositar(float valor, int cnpj) {
        if (valor > 0 && this.cnpjEmpresa == cnpj) {
            setSaldo(getSaldo() + valor);
            return true;
        }
        return false;
    }

    @Override
    public String gerarExtrato() {
        return super.gerarExtrato() + " | CNPJ da Empresa: " + this.cnpjEmpresa;
    }
}