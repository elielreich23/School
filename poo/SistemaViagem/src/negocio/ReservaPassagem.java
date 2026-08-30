package negocio;

import dados.Cidade;
import dados.Cliente;
import dados.Reserva;

import java.util.ArrayList;

public class ReservaPassagem {

    private ArrayList<Cidade> listaDeCidades;
    private ArrayList<Cliente> listaDeClientes;

    public ReservaPassagem() {
        listaDeCidades = new ArrayList<>();
        listaDeClientes = new ArrayList<>();
    }

    public void cadastrarCidade(Cidade cidade) {
        listaDeCidades.add(cidade);
    }

    public void cadastrarCliente(Cliente cliente) {
        listaDeClientes.add(cliente);
    }

    public void reservarIda(Cliente cliente, Reserva reserva) {
        cliente.reservarIda(reserva);
    }

    public void reservarVolta(Cliente cliente, Reserva ida, Reserva volta) {
        cliente.reservarVolta(ida, volta);
    }

    public Reserva[] mostrarReservas(long cpfCliente) {

        for (Cliente cliente : listaDeClientes) {

            if (cliente.getCpf() == cpfCliente) {

                ArrayList<Reserva> reservas = cliente.getReservas();

                return reservas.toArray(new Reserva[reservas.size()]);
            }
        }

        return new Reserva[0];
    }

    public Cliente[] mostrarClientes() {
        return listaDeClientes.toArray(
                new Cliente[listaDeClientes.size()]);
    }

    public Cidade[] mostrarCidades() {
        return listaDeCidades.toArray(
                new Cidade[listaDeCidades.size()]);
    }
}