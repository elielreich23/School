package dados;

public class Naturais extends Gerador {

    @Override
    public void gerar(int quantidade) {
        getSequencia().clear();
        for (int i = 1; i <= quantidade; i++) {
            getSequencia().add(i);
        }
    }
}
