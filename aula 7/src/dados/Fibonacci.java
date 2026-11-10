package dados;

public class Fibonacci extends Gerador {
    @Override public void gerar(int quantidade) {
        getSequencia().clear();
        int anterior = 0, atual = 1;
        for (int i = 0; i < quantidade; i++) {
            getSequencia().add(anterior);
            int proximo = anterior + atual;
            anterior = atual;
            atual = proximo;
        }
    }
}
