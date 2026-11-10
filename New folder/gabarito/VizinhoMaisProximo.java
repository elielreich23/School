/** Vizinho mais próximo para o VRP: estende a rota atual enquanto algum cliente couber. */
public class VizinhoMaisProximo implements Construtor {

    @Override
    public Solucao construir(Instancia inst) {
        int n = inst.nClientes();
        boolean[] atendido = new boolean[n + 1];
        int restantes = n;
        Solucao sol = new Solucao(inst);

        while (restantes > 0) {
            Rota rota = new Rota(inst);
            int atual = 0;
            int carga = 0;
            double dist = 0; // distância percorrida até 'atual', sem a volta ao depósito

            while (true) {
                int prox = -1;
                for (int c = 1; c <= n; c++) {
                    boolean cabe = !atendido[c]
                            && carga + inst.q(c) <= inst.Q()
                            && dist + inst.d(atual, c) + inst.d(c, 0) <= inst.L();
                    if (cabe && (prox == -1 || inst.d(atual, c) < inst.d(atual, prox))) {
                        prox = c;
                    }
                }
                if (prox == -1) {
                    break;
                }
                rota.adicionar(prox);
                atendido[prox] = true;
                restantes--;
                carga += inst.q(prox);
                dist += inst.d(atual, prox);
                atual = prox;
            }

            if (rota.vazia()) {
                // algum cliente não cabe nem sozinho em um veículo
                throw new IllegalStateException("instância inviável: há cliente que não cabe em nenhuma rota");
            }
            sol.adicionar(rota);
        }
        return sol;
    }
}
