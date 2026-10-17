package dados;

public class Quadrados extends Gerador {

    @Override
    public void gerar(int quantidade) {
        getSequencia().clear();
        for (int i = 1; i <= quantidade; i++) {
            getSequencia().add(i * i);
        }
    }
}
