package dados;

public class Reserva {

    private int numReserva;
    private String dataVoo;
    private String horaVoo;
    private float preco;
    private String classeVoo;
    private boolean idaEVolta;
    private int poltrona;

    private Cidade origem;
    private Cidade destino;

    private Reserva volta;

    public Reserva(int numReserva, String dataVoo, String horaVoo,
            float preco, String classeVoo,
            boolean idaEVolta, int poltrona,
            Cidade origem, Cidade destino) {

        this.numReserva = numReserva;
        this.dataVoo = dataVoo;
        this.horaVoo = horaVoo;
        this.preco = preco;
        this.classeVoo = classeVoo;
        this.idaEVolta = idaEVolta;
        this.poltrona = poltrona;
        this.origem = origem;
        this.destino = destino;
    }

    public void setVolta(Reserva volta) {
        this.volta = volta;
    }

    public Reserva getVolta() {
        return volta;
    }

    @Override
    public String toString() {

        String texto = "Número da reserva: " + numReserva +
                "\nData do voo: " + dataVoo +
                "\nHora do voo: " + horaVoo +
                "\nPreço: R$ " + preco +
                "\nClasse do voo: " + classeVoo +
                "\nIda e volta: " + idaEVolta +
                "\nPoltrona: " + poltrona +
                "\nOrigem: " + origem +
                "\nDestino: " + destino;

        if (volta != null) {
            texto += "\n--- Reserva de volta ---\n" + volta;
        }

        return texto;
    }
}