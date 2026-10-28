/**
 * Vizinho mais próximo para o VRP.
 *
 * TODO (Tarefa 2):
 *   Enquanto houver clientes não atendidos:
 *     - crie uma rota vazia; atual = 0 (depósito)
 *     - repita: escolha o cliente c NÃO ATENDIDO mais próximo de 'atual' que CABE na rota:
 *         carga + q(c) <= Q
 *         distância até agora + d(atual, c) + d(c, 0) <= L    (precisa conseguir voltar ao depósito!)
 *       se não existir, feche a rota; senão, adicione c e faça atual = c
 *     - adicione a rota à solução
 *
 * Dica: mantenha 'carga' e 'distância até agora' (sem a volta) em variáveis locais,
 * em vez de chamar rota.carga() / rota.distancia() a cada teste.
 */
public class VizinhoMaisProximo implements Construtor {

    @Override
    public Solucao construir(Instancia inst) {
        throw new UnsupportedOperationException("Tarefa 2: implemente VizinhoMaisProximo.construir()");
    }
}
