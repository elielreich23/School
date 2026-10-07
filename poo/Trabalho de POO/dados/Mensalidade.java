package dados;

public class Mensalidade {
    private int numero;
    private double valor;
    private boolean paga;
    private Matricula matricula;
    private Pagamento pagamento;

    public Mensalidade(int numero, double valor, Matricula matricula) {
        this.numero = numero;
        this.valor = valor;
        this.paga = false;
        this.matricula = matricula;
    }

    public void registrarPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
        this.paga = true;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public String toString() {
        String status = paga ? "paga" : "pendente";
        return "Mensalidade " + numero + " | R$ " + valor + " | " + status;
    }
}
