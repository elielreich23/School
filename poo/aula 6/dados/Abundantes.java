package dados;

public class Abundantes extends Gerador {

    private int somaDivisoresProprios(int n) {
        if (n <= 1) {
            return 0;
        }
        int soma = 1;
        int limite = (int) Math.sqrt(n);
        for (int i = 2; i <= limite; i++) {
            if (n % i == 0) {
                soma += i;
                int complemento = n / i;
                if (complemento != i) {
                    soma += complemento;
                }
            }
        }
        return soma;
    }

    @Override
    public void gerar(int quantidade) {
        getSequencia().clear();
        int numero = 1;
        while (getSequencia().size() < quantidade) {
            if (somaDivisoresProprios(numero) > numero) {
                getSequencia().add(numero);
            }
            numero++;
        }
    }
}
