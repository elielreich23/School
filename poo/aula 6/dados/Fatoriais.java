package dados;

public class Fatoriais extends Gerador {

    @Override
    public void gerar(int quantidade) {
        getSequencia().clear();
        int fatorial = 1;
        for (int i = 1; i <= quantidade; i++) {
            fatorial *= i;
            getSequencia().add(fatorial);
        }
    }
}
