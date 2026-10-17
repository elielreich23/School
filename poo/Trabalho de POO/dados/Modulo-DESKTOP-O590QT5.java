package dados;

public class Modulo {
    private int numero;
    private String nome;
    private int cargaHoraria;

    public Modulo(int numero, String nome, int cargaHoraria) {
        this.numero = numero;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String toString() {
        return "Módulo " + numero + ": " + nome + " (" + cargaHoraria + "h)";
    }
}
