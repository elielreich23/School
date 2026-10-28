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
        throw new UnsupportedOperationException("Tarefa 3: implemente RelocateInter.melhorar()");
    }
}
