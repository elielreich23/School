/**
 * Vizinho mais próximo para o VRP.
 *
 * Enquanto houver clientes não atendidos:
 *   - crie uma rota vazia; atual = 0 (depósito)
 *   - repita: escolha o cliente c NÃO ATENDIDO mais próximo de 'atual' que CABE na rota:
 *       carga + q(c) <= Q
 *       distância até agora + d(atual, c) + d(c, 0) <= L    (precisa conseguir voltar ao depósito!)
 *   - se não existir, feche a rota; senão, adicione c e faça atual = c
 *   - adicione a rota à solução
 */
public class VizinhoMaisProximo implements Construtor {

    @Override
    public Solucao construir(Instancia inst) {
        Solucao sol = new Solucao(inst);
        boolean[] atendido = new boolean[inst.nClientes() + 1];
        int restantes = inst.nClientes();

        while (restantes > 0) {
            Rota rota = new Rota(inst);
            int atual = 0;
            int carga = 0;
            double distParcial = 0.0;

            while (true) {
                int melhorCliente = -1;
                double melhorDist = Double.POSITIVE_INFINITY;

                for (int c = 1; c <= inst.nClientes(); c++) {
                    if (atendido[c]) {
                        continue;
                    }
                    if (carga + inst.q(c) > inst.Q()) {
                        continue;
                    }

                    double d = inst.d(atual, c);
                    double distanciaTotal = distParcial + d + inst.d(c, 0);
                    if (distanciaTotal <= inst.L() + 1e-9) {
                        if (melhorCliente == -1 || d < melhorDist - 1e-9
                                || (Math.abs(d - melhorDist) <= 1e-9 && c < melhorCliente)) {
                            melhorCliente = c;
                            melhorDist = d;
                        }
                    }
                }

                if (melhorCliente == -1) {
                    break;
                }

                atendido[melhorCliente] = true;
                rota.adicionar(melhorCliente);
                carga += inst.q(melhorCliente);
                distParcial += inst.d(atual, melhorCliente);
                atual = melhorCliente;
                restantes--;
            }

            if (!rota.vazia()) {
                sol.adicionar(rota);
            }
        }

        sol.removerRotasVazias();
        return sol;
    }
}
