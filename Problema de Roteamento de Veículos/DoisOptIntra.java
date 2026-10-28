import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 2-opt aplicado separadamente em cada rota (o mesmo da aula passada).
 * Como o 2-opt só encurta a rota e não muda seus clientes, a viabilidade é mantida.
 */
public class DoisOptIntra implements BuscaLocal {
    private static final double EPS = 1e-9;

    @Override
    public Solucao melhorar(Solucao s) {
        Solucao nova = s.copia();
        Instancia inst = nova.getInstancia();
        for (Rota rota : nova.getRotas()) {
            // sequência com o depósito na posição 0: [0, c1, c2, ..., ck]
            List<Integer> seq = new ArrayList<>();
            seq.add(0);
            seq.addAll(rota.getClientes());
            int n = seq.size();

            boolean melhorou = true;
            while (melhorou) {
                melhorou = false;
                for (int i = 0; i < n - 2; i++) {
                    for (int j = i + 2; j < n; j++) {
                        int u = seq.get(i), v = seq.get(i + 1);
                        int w = seq.get(j), z = seq.get((j + 1) % n);
                        double delta = inst.d(u, w) + inst.d(v, z) - inst.d(u, v) - inst.d(w, z);
                        if (delta < -EPS) {
                            Collections.reverse(seq.subList(i + 1, j + 1));
                            melhorou = true;
                        }
                    }
                }
            }
            rota.getClientes().clear();
            rota.getClientes().addAll(seq.subList(1, n));
        }
        return nova;
    }
}
