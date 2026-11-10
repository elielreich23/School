package negocio;

import dados.Animal;
import dados.Aquario;
import dados.Viveiro;
import exceptions.EspacoIndisponivelException;

import java.util.ArrayList;
import java.util.List;

public class Zoologico {

    private List<Animal> animais;
    private List<Viveiro> viveiros;

    public Zoologico() {
        animais = new ArrayList<>();
        viveiros = new ArrayList<>();
    }

    public void cadastrarViveiro(Viveiro viveiro) {
        viveiros.add(viveiro);
    }

    public void cadastrarAnimais(Animal animal) {
        animais.add(animal);
    }

    public void cadastrarAnimal(Animal animal) {
        cadastrarAnimais(animal);
    }

    public void alocarAnimal(Animal animal, Viveiro viveiro) throws EspacoIndisponivelException {
        if (animal == null || viveiro == null) {
            throw new EspacoIndisponivelException("Animal ou viveiro inválido.");
        }

        if (!animais.contains(animal)) {
            throw new EspacoIndisponivelException("O animal não está cadastrado no zoológico.");
        }

        viveiro.adicionarAnimal(animal);
    }

    public Viveiro[] mostrarViveiros() {
        return viveiros.toArray(new Viveiro[viveiros.size()]);
    }

    public Viveiro[] getViveiros() {
        return mostrarViveiros();
    }

    public Animal[] mostrarAnimais() {
        return animais.toArray(new Animal[animais.size()]);
    }

    public Animal[] getAnimais() {
        return mostrarAnimais();
    }

    public Aquario[] getAquarios() {
        List<Aquario> aquarios = new ArrayList<>();
        for (Viveiro viveiro : viveiros) {
            if (viveiro instanceof Aquario) {
                aquarios.add((Aquario) viveiro);
            }
        }
        return aquarios.toArray(new Aquario[aquarios.size()]);
    }
}
