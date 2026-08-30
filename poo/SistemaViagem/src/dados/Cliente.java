package dados;

import java.util.ArrayList;

public class Cliente {

    private long cpf;
    private String nome;
    private String endereco;
    private long telefone;

    private ArrayList<Reserva> reservas;

    public Cliente(long cpf, String nome, String endereco, long telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;

        reservas = new ArrayList<>();
    }

    public void reservarIda(Reserva reserva) {
        reservas.add(reserva);
    }

    public void reservarVolta(Reserva ida, Reserva volta) {
        reservas.add(ida);
        reservas.add(volta);

        ida.setVolta(volta);
    }

    public long getCpf() {
        return cpf;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    @Override
    public String toString() {
        return "CPF: " + cpf +
                "\nNome: " + nome +
                "\nEndereço: " + endereco +
                "\nTelefone: " + telefone;
    }
}