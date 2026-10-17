package dados;

public class NumeroPrimos extends Gerador {

    private boolean ehPrimo(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void gerar(int quantidade) {
        getSequencia().clear();
        int numero = 2;
        while (getSequencia().size() < quantidade) {
            if (ehPrimo(numero)) {
                getSequencia().add(numero);
            }
            numero++;
        }
    }
}
