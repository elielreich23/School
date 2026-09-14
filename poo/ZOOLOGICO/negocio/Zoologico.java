package negocio;

import dados.Animal;
import dados.Aquario;
import dados.Viveiro;

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

    public void cadastrarAnimal(Animal animal) {

        animais.add(animal);
    }

    public boolean alocarAnimal(Animal animal, Viveiro viveiro) {

        if (!animais.contains(animal)) {
            return false;
        }

        return viveiro.adicionarAnimal(animal);
    }

    public Aquario[] getAquarios() {

        List<Aquario> aquarios = new ArrayList<>();

        for (Viveiro viveiro : viveiros) {

            if (viveiro instanceof Aquario) {
                aquarios.add((Aquario) viveiro);
            }
        }

        return aquarios.toArray(
                new Aquario[aquarios.size()]
        );
    }

    public Viveiro[] getViveiros() {

        return viveiros.toArray(
                new Viveiro[viveiros.size()]
        );
    }

    public Animal[] getAnimais() {

        return animais.toArray(
                new Animal[animais.size()]
        );
    }
}