package dados;

public class Abundantes extends Gerador {
    private int somaDivisoresProprios(int n) {
        if (n <= 1) return 0;
        int soma = 1;
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) {
                soma += i;
                if (i != n / i) soma += n / i;
            }
        }
        return soma;
    }

    @Override public void gerar(int quantidade) {
        getSequencia().clear();
        for (int n = 1; getSequencia().size() < quantidade; n++) {
            if (somaDivisoresProprios(n) > n) getSequencia().add(n);
        }
    }
}
