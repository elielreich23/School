package dados;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends Pessoa {
    private List<Animal> animais;

    public Tutor() {
        super();
        this.animais = new ArrayList<>();
    }

    public Tutor(String nome, long cpf, String telefone) {
        super(nome, cpf, telefone);
        this.animais = new ArrayList<>();
    }

    public void adicionarAnimal(Animal a) {
        if (this.animais == null) {
            this.animais = new ArrayList<>();
        }
        if (a != null && !this.animais.contains(a)) {
            this.animais.add(a);
            a.setTutor(this);
        }
    }

    public void removerAnimal(Animal a) {
        if (this.animais != null && a != null) {
            this.animais.remove(a);
            if (a.getTutor() == this) {
                a.setTutor(null);
            }
        }
    }

    public float calculaGastoAnimais() {
        float total = 0.0f;
        if (animais != null) {
            for (Animal a : animais) {
                if (a != null) {
                    total += a.calculaAtendimentos();
                }
            }
        }
        return total;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tutor [").append(super.toString())
          .append(String.format(" | Total Gasto com Animais: R$ %.2f", calculaGastoAnimais()))
          .append("]");

        if (animais != null && !animais.isEmpty()) {
            sb.append("\n  Animais do Tutor (").append(animais.size()).append("):");
            for (Animal a : animais) {
                sb.append("\n    * ").append(a.getNome())
                  .append(" (").append(a.getEspecie()).append(", ").append(a.getRaca()).append(")");
            }
        } else {
            sb.append("\n  Animais do Tutor: Nenhum animal vinculado.");
        }

        return sb.toString();
    }
}
