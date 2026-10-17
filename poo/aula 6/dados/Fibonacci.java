package dados;

public class Fibonacci extends Gerador {

    @Override
    public void gerar(int quantidade) {
        getSequencia().clear();
        if (quantidade <= 0) {
            return;
        }

        getSequencia().add(0);
        if (quantidade == 1) {
            return;
        }

        getSequencia().add(1);
        for (int i = 2; i < quantidade; i++) {
            int proximo = getSequencia().get(i - 1) + getSequencia().get(i - 2);
            getSequencia().add(proximo);
        }
    }
}
