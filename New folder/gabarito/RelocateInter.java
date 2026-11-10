import java.util.List;

/** Relocate inter-rotas com estratégia de primeira melhora. */
public class RelocateInter implements BuscaLocal {
    private static final double EPS = 1e-9;

    @Override
    public Solucao melhorar(Solucao s) {
        Solucao nova = s.copia();
        Instancia inst = nova.getInstancia();
        List<Rota> rotas = nova.getRotas();

        boolean melhorou = true;
        while (melhorou) {
            melhorou = false;
            busca:
            for (Rota a : rotas) {
                for (int i = 0; i < a.tamanho(); i++) {
                    int k = a.get(i);
                    int p = i == 0 ? 0 : a.get(i - 1);
                    int suc = i == a.tamanho() - 1 ? 0 : a.get(i + 1);
                    double deltaA = inst.d(p, suc) - inst.d(p, k) - inst.d(k, suc);

                    for (Rota b : rotas) {
                        if (b == a || b.carga() + inst.q(k) > inst.Q()) {
                            continue;
                        }
                        double distB = b.distancia();
                        for (int j = 0; j <= b.tamanho(); j++) {
                            int u = j == 0 ? 0 : b.get(j - 1);
                            int v = j == b.tamanho() ? 0 : b.get(j);
                            double deltaB = inst.d(u, k) + inst.d(k, v) - inst.d(u, v);
                            if (distB + deltaB > inst.L()) {
                                continue;
                            }
                            if (deltaA + deltaB < -EPS) {
                                a.getClientes().remove(i);
                                b.getClientes().add(j, k);
                                melhorou = true;
                                break busca;
                            }
                        }
                    }
                }
            }
        }
        nova.removerRotasVazias();
        return nova;
    }
}
