/**
 * Relocate inter-rotas com estratégia de primeira melhora.
 *
 * Para cada rota A, cada posição i de A (cliente k = A[i]):
 *   p = cliente anterior a k (ou 0 se i é o primeiro)
 *   s = cliente seguinte a k (ou 0 se i é o último)
 *   deltaA = d(p,s) - d(p,k) - d(k,s)
 *   Para cada rota B != A:
 *     se carga(B) + q(k) > Q: pule
 *     Para cada posição j de 0 até B.tamanho() (inserir k ANTES de B[j]; j == tamanho = no fim):
 *       u = cliente anterior à posição j (ou 0), v = B[j] (ou 0 se j == tamanho)
 *       deltaB = d(u,k) + d(k,v) - d(u,v)
 *       se distancia(B) + deltaB > L: pule
 *       se deltaA + deltaB < -EPS: remova k de A, insira em B na posição j,
 *                                  e recomece a busca do início.
 *   Pare quando nenhum movimento melhorar. No fim, chame removerRotasVazias().
 */
public class RelocateInter implements BuscaLocal {
    private static final double EPS = 1e-9;

    @Override
    public Solucao melhorar(Solucao s) {
        Solucao nova = s.copia();
        Instancia inst = nova.getInstancia();

        while (true) {
            boolean melhorou = false;

            for (int a = 0; a < nova.getRotas().size(); a++) {
                Rota A = nova.getRotas().get(a);

                for (int i = 0; i < A.tamanho(); i++) {
                    int k = A.get(i);
                    int p = (i == 0) ? 0 : A.get(i - 1);
                    int s = (i == A.tamanho() - 1) ? 0 : A.get(i + 1);
                    double deltaA = inst.d(p, s) - inst.d(p, k) - inst.d(k, s);

                    for (int b = 0; b < nova.getRotas().size(); b++) {
                        if (a == b) {
                            continue;
                        }

                        Rota B = nova.getRotas().get(b);
                        if (B.carga() + inst.q(k) > inst.Q()) {
                            continue;
                        }

                        for (int j = 0; j <= B.tamanho(); j++) {
                            int u = (j == 0) ? 0 : B.get(j - 1);
                            int v = (j == B.tamanho()) ? 0 : B.get(j);
                            double deltaB = inst.d(u, k) + inst.d(k, v) - inst.d(u, v);

                            if (B.distancia() + deltaB > inst.L() + 1e-9) {
                                continue;
                            }

                            if (deltaA + deltaB < -EPS) {
                                A.getClientes().remove(i);
                                B.getClientes().add(j, k);
                                nova.removerRotasVazias();
                                melhorou = true;
                                break;
                            }
                        }
                        if (melhorou) {
                            break;
                        }
                    }
                    if (melhorou) {
                        break;
                    }
                }
                if (melhorou) {
                    break;
                }
            }

            if (!melhorou) {
                nova.removerRotasVazias();
                return nova;
            }
        }
    }
}
