package dados;

public class Perfeitos extends Gerador {
    @Override public void gerar(int quantidade) {
        getSequencia().clear();
        // Valores perfeitos conhecidos que cabem no Integer usado pelo diagrama.
        int[] representaveis = {6, 28, 496, 8128, 33550336};
        for (int i = 0; i < quantidade && i < representaveis.length; i++)
            getSequencia().add(representaveis[i]);
    }
}
