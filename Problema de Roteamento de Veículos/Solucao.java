import java.util.ArrayList;
import java.util.List;

/** Uma solução do VRP: um conjunto de rotas. */
public class Solucao {
    private final Instancia inst;
    private final List<Rota> rotas = new ArrayList<>();

    public Solucao(Instancia inst) {
        this.inst = inst;
    }

    public Instancia getInstancia() {
        return inst;
    }

    /** Lista (modificável) de rotas. */
    public List<Rota> getRotas() {
        return rotas;
    }

    public void adicionar(Rota r) {
        rotas.add(r);
    }

    /** Distância total percorrida por todos os veículos. */
    public double custo() {
        double total = 0;
        for (Rota r : rotas) {
            total += r.distancia();
        }
        return total;
    }

    /** Número de rotas não vazias (= veículos usados). */
    public int numeroDeRotas() {
        int k = 0;
        for (Rota r : rotas) {
            if (!r.vazia()) {
                k++;
            }
        }
        return k;
    }

    /** Remove as rotas que ficaram sem clientes. */
    public void removerRotasVazias() {
        rotas.removeIf(Rota::vazia);
    }

    /**
     * Confere a solução: cada cliente atendido exatamente uma vez e todas as rotas viáveis.
     * Retorna null se estiver tudo certo, ou uma mensagem descrevendo o primeiro problema.
     */
    public String validar() {
        int[] vezes = new int[inst.nClientes() + 1];
        for (int i = 0; i < rotas.size(); i++) {
            Rota r = rotas.get(i);
            for (int c : r.getClientes()) {
                if (c < 1 || c > inst.nClientes()) {
                    return "rota " + i + " contém o vértice inválido " + c;
                }
                vezes[c]++;
            }
            if (r.carga() > inst.Q()) {
                return String.format("rota %d excede a capacidade: %d > %d", i, r.carga(), inst.Q());
            }
            if (r.distancia() > inst.L() + 1e-6) {
                return String.format("rota %d excede a distância máxima: %.1f > %.1f", i, r.distancia(), inst.L());
            }
        }
        for (int c = 1; c <= inst.nClientes(); c++) {
            if (vezes[c] != 1) {
                return "cliente " + c + " atendido " + vezes[c] + " vezes";
            }
        }
        return null;
    }

    public Solucao copia() {
        Solucao s = new Solucao(inst);
        for (Rota r : rotas) {
            s.adicionar(r.copia());
        }
        return s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rotas.size(); i++) {
            Rota r = rotas.get(i);
            sb.append(String.format("  R%-2d carga=%3d dist=%7.1f %s%n", i, r.carga(), r.distancia(), r));
        }
        return sb.toString();
    }
}
