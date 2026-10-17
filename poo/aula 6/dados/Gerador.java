package dados;

import java.util.ArrayList;
import java.util.List;

public abstract class Gerador {
    private List<Integer> sequencia = new ArrayList<>();

    public abstract void gerar(int quantidade);

    public List<Integer> getSequencia() {
        return sequencia;
    }
}
