package dados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListaTelefonica {

    private final List<Contato> contatos;
    private final ContatoDAO contatoDAO;

    public ListaTelefonica() {
        contatoDAO = new ContatoDAO();

        // Carrega os contatos existentes ao iniciar.
        contatos = contatoDAO.getAll();
    }

    public void adicionarContato(Contato contato) {
        contatos.add(contato);
        contatoDAO.insert(contato);
    }

    public void removerContato(Contato contato) {
        boolean removido = contatos.removeIf(c ->
                c.getNome().equals(contato.getNome())
                && c.getTelefone().equals(contato.getTelefone())
        );

        if (removido) {
            contatoDAO.delete(contato);
        }
    }

    public List<Contato> getContatos() {
        return new ArrayList<>(contatos);
    }

    public void exibirContatos() {
        List<Contato> ordenados = new ArrayList<>(contatos);

        ordenados.sort(
                Comparator.comparing(
                        Contato::getNome,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        if (ordenados.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        for (Contato contato : ordenados) {
            System.out.println(contato);
        }
    }
}