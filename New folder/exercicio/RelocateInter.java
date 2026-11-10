import java.util.List;

/**
 * Relocate inter-rotas com estratégia de primeira melhora.
 *
 * TODO (Tarefa 3):
 *   Para cada rota A, cada posição i de A (cliente k = A[i]):
 *     p = cliente anterior a k (ou 0 se i é o primeiro)
 *     s = cliente seguinte a k (ou 0 se i é o último)
 *     deltaA = d(p,s) - d(p,k) - d(k,s)
 *     Para cada rota B != A:
 *       se carga(B) + q(k) > Q: pule
 *       Para cada posição j de 0 até B.tamanho() (inserir k ANTES de B[j]; j == tamanho = no fim):
 *         u = cliente anterior à posição j (ou 0), v = B[j] (ou 0 se j == tamanho)
 *         deltaB = d(u,k) + d(k,v) - d(u,v)
 *         se distancia(B) + deltaB > L: pule
 *         se deltaA + deltaB < -EPS: remova k de A, insira em B na posição j,
 *                                    e recomece a busca do início.
 *   Pare quando nenhum movimento melhorar. No fim, chame removerRotasVazias().
 *
 * Trabalhe sobre uma cópia: Solucao nova = s.copia();
 */
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
