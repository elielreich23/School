package negocio;

import dados.Contato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaTelefonica {

    private Map<Character, List<Contato>> contatos;

    public ListaTelefonica() {
        contatos = new HashMap<>();
    }

    public void adicionarContato(Contato contato) {

        char inicial = Character.toUpperCase(
                contato.getNome().charAt(0)
        );

        if (!contatos.containsKey(inicial)) {
            contatos.put(inicial, new ArrayList<>());
        }

        contatos.get(inicial).add(contato);
    }

    public void removerContato(Contato contato) {

        char inicial = Character.toUpperCase(
                contato.getNome().charAt(0)
        );

        if (contatos.containsKey(inicial)) {

            contatos.get(inicial).remove(contato);

            if (contatos.get(inicial).isEmpty()) {
                contatos.remove(inicial);
            }
        }
    }

    public List<Contato> buscarContatos(char inicial) {

        inicial = Character.toUpperCase(inicial);

        if (contatos.containsKey(inicial)) {
            return contatos.get(inicial);
        }

        return new ArrayList<>();
    }
}