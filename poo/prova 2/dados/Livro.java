package dados;

import java.time.LocalDate;
import excecoes.LimiteDeItensExcedidoException;

public class Livro extends Item {
    private static final int tempoEmprestimo = 15;

    public Livro(int id, String titulo, String autor, int ano) {
        super(id, titulo, autor, ano);
    }

    @Override
    public void emprestar(Usuario usuario, int qtdAtual)
            throws LimiteDeItensExcedidoException {
        if (!usuario.podeEmprestLivro(qtdAtual)) {
            throw new LimiteDeItensExcedidoException(
                "Limite de livros excedido."
            );
        }

        dataEmprestimo = LocalDate.now();
        dataDevolucao = dataEmprestimo.plusDays(tempoEmprestimo);
        dataUltimaPenalidadeReset();
        emprestado = true;
    }

    private void dataUltimaPenalidadeReset() {
        setDataUltimaPenalidade(null);
    }
}
