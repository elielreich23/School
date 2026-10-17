package dados;

public class Encontro {
    private int numero;
    private Modulo modulo;
    private String data;

    public Encontro(int numero, Modulo modulo, String data) {
        this.numero = numero;
        this.modulo = modulo;
        this.data = data;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String nomeModulo = modulo != null ? modulo.getNome() : "sem módulo";
        return "Encontro " + numero + " | Data: " + data + " | Módulo: " + nomeModulo;
    }
}
