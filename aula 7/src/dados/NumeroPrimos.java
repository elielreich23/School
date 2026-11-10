package dados;

public class NumeroPrimos extends Gerador {
    private boolean ehPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; (long) i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }

    @Override public void gerar(int quantidade) {
        getSequencia().clear();
        for (int n = 2; getSequencia().size() < quantidade; n++) {
            if (ehPrimo(n)) getSequencia().add(n);
        }
    }
}
