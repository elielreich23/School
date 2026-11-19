package dados;

import java.time.LocalDate;
import excecoes.LimiteDeItensExcedidoException;

public class Monografia extends Item {
    private static final int tempoEmprestimo = 30;

    public Monografia(int id, String titulo, String autor, int ano) {
        super(id, titulo, autor, ano);
    }

    @Override
    public void emprestar(Usuario usuario, int qtdAtual)
            throws LimiteDeItensExcedidoException {
        if (!usuario.podeEmprestMono(qtdAtual)) {
            throw new LimiteDeItensExcedidoException(
                "Limite de monografias excedido."
            );
        }

        dataEmprestimo = LocalDate.now();
        dataDevolucao = dataEmprestimo.plusDays(tempoEmprestimo);
        setDataUltimaPenalidade(null);
        emprestado = true;
    }
}
