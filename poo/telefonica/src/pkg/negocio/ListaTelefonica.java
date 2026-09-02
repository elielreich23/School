package pkg.negocio;

import pkg.dados.Contato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ListaTelefonica {
    
    // TreeMap keeps keys A-Z ordered automatically
    private final Map<Character, List<Contato>> contatos = new TreeMap<>();

    public ListaTelefonica() {
        for (char c = 'A'; c <= 'Z'; c++) {
            contatos.put(c, new ArrayList<>());
        }
    }

    public void adicionarContato(Contato contato) {
        obterInicial(contato)
            .ifPresent(inicial -> contatos.computeIfAbsent(inicial, k -> new ArrayList<>()).add(contato));
    }

    public void removerContato(Contato contato) {
        obterInicial(contato)
            .ifPresent(inicial -> Optional.ofNullable(contatos.get(inicial)).ifPresent(list -> list.remove(contato)));
    }

    public List<Contato> buscarContatos(char letra) {
        return contatos.getOrDefault(Character.toUpperCase(letra), Collections.emptyList());
    }

    public Map<Character, List<Contato>> buscarContatos() {
        return Collections.unmodifiableMap(contatos);
    }

    private Optional<Character> obterInicial(Contato contato) {
        return Optional.ofNullable(contato)
            .map(Contato::nome)
            .filter(nome -> !nome.isBlank())
            .map(nome -> Character.toUpperCase(nome.charAt(0)));
    }
}