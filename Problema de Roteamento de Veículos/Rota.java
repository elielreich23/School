import java.util.ArrayList;
import java.util.List;

/**
 * Uma rota de um veículo. A lista guarda apenas os clientes:
 * o depósito (0) no início e no fim é implícito.
 *
 * Exemplo: clientes = [3, 7, 1] representa 0 -> 3 -> 7 -> 1 -> 0.
 *
 * Obs.: para simplificar, carga() e distancia() são recalculadas a cada chamada (O(n)).
 * Uma implementação mais eficiente guardaria esses valores em atributos e os atualizaria a cada movimento.
 */
public class Rota {
    private final Instancia inst;
    private final List<Integer> clientes;

    public Rota(Instancia inst) {
        this(inst, new ArrayList<>());
    }

    public Rota(Instancia inst, List<Integer> clientes) {
        this.inst = inst;
        this.clientes = new ArrayList<>(clientes);
    }

    /** Lista (modificável) de clientes, na ordem de visita. */
    public List<Integer> getClientes() {
        return clientes;
    }

    public int tamanho() {
        return clientes.size();
    }

    public boolean vazia() {
        return clientes.isEmpty();
    }

    /** Cliente na posição pos da rota. */
    public int get(int pos) {
        return clientes.get(pos);
    }

    public void adicionar(int cliente) {
        clientes.add(cliente);
    }

    /** TODO (Tarefa 1): soma das demandas dos clientes da rota. Use inst.q(c). */
    public int carga() {
        throw new UnsupportedOperationException("Tarefa 1: implemente Rota.carga()");
    }

    /**
     * TODO (Tarefa 1): distância total da rota, saindo do depósito (0),
     * visitando os clientes na ordem e voltando ao depósito. Rota vazia tem distância 0.
     */
    public double distancia() {
        throw new UnsupportedOperationException("Tarefa 1: implemente Rota.distancia()");
    }

    /**
     * TODO (Tarefa 1): a rota é viável se respeita a capacidade (carga <= inst.Q())
     * e a distância máxima (distancia <= inst.L()).
     */
    public boolean ehViavel() {
        throw new UnsupportedOperationException("Tarefa 1: implemente Rota.ehViavel()");
    }

    public Rota copia() {
        return new Rota(inst, clientes);
    }

    @Override
    public String toString() {
        return clientes.toString();
    }
}
