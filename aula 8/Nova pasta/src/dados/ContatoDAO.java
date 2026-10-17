package dados;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private final ArquivoContatoDAO arquivoDAO;

    public ContatoDAO() {
        arquivoDAO = new ArquivoContatoDAO("contatos.csv");
    }

    // Adiciona o contato ao final do arquivo.
    public void insert(Contato contato) {
        List<Contato> contatos = getAll();

        contatos.add(contato);

        arquivoDAO.salvaContatos(contatos);
    }

    // Remove o contato correspondente.
    public void delete(Contato contato) {
        List<Contato> contatos = getAll();

        boolean removido = contatos.removeIf(c ->
                c.getNome().equals(contato.getNome())
                && c.getTelefone().equals(contato.getTelefone())
        );

        if (removido) {
            arquivoDAO.salvaContatos(contatos);
        }
    }

    // Retorna todos os contatos salvos.
    public List<Contato> getAll() {
        return new ArrayList<>(arquivoDAO.leContatos());
    }
}