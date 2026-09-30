package negocio;

import dados.Animal;
import dados.Atendimento;
import dados.Medicamento;
import dados.Tutor;
import dados.Veterinario;

import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private List<Tutor> tutores;
    private List<Veterinario> veterinarios;
    private List<Medicamento> medicamentos;

    public Clinica() {
        this.tutores = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.medicamentos = new ArrayList<>();
    }

    public void adicionarTutor(Tutor t) {
        if (t != null && !tutores.contains(t)) {
            tutores.add(t);
        }
    }

    public void adicionarVeterinario(Veterinario v) {
        if (v != null && !veterinarios.contains(v)) {
            veterinarios.add(v);
        }
    }

    public void adicionarAnimal(Tutor t, Animal a) {
        if (t != null && a != null) {
            if (!tutores.contains(t)) {
                tutores.add(t);
            }
            t.adicionarAnimal(a);
        }
    }

    public void adicionarAtendimento(Animal a, Atendimento at) {
        if (a != null && at != null) {
            a.adicionaAtendimento(at);
        }
    }

    public void adicionarMedicamento(Medicamento m) {
        if (m != null && !medicamentos.contains(m)) {
            medicamentos.add(m);
        }
    }

    public List<Tutor> getTutores() {
        return tutores;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public List<Animal> getAnimais(Tutor t) {
        if (t != null) {
            return t.getAnimais();
        }
        return new ArrayList<>();
    }

    public List<Atendimento> getAtendimentos(Animal a) {
        if (a != null) {
            return a.getAtendimentos();
        }
        return new ArrayList<>();
    }

    public float calculaTotalAtendimentos() {
        float total = 0.0f;
        if (tutores != null) {
            for (Tutor t : tutores) {
                if (t != null) {
                    total += t.calculaGastoAnimais();
                }
            }
        }
        return total;
    }

    // Métodos utilitários para atender todos os requisitos de visualização na apresentação
    public List<Animal> getTodosAnimais() {
        List<Animal> todos = new ArrayList<>();
        for (Tutor t : tutores) {
            if (t.getAnimais() != null) {
                todos.addAll(t.getAnimais());
            }
        }
        return todos;
    }

    public List<Atendimento> getTodosAtendimentos() {
        List<Atendimento> todos = new ArrayList<>();
        for (Animal a : getTodosAnimais()) {
            if (a.getAtendimentos() != null) {
                todos.addAll(a.getAtendimentos());
            }
        }
        return todos;
    }

    public List<Atendimento> getAtendimentosRealizados(Animal a) {
        List<Atendimento> realizados = new ArrayList<>();
        if (a != null && a.getAtendimentos() != null) {
            for (Atendimento at : a.getAtendimentos()) {
                if (at.isFinalizado()) {
                    realizados.add(at);
                }
            }
        }
        return realizados;
    }

    public List<Atendimento> getAtendimentosRealizados(Tutor t) {
        List<Atendimento> realizados = new ArrayList<>();
        if (t != null && t.getAnimais() != null) {
            for (Animal a : t.getAnimais()) {
                realizados.addAll(getAtendimentosRealizados(a));
            }
        }
        return realizados;
    }

    public List<Atendimento> getTodosAtendimentosRealizados() {
        List<Atendimento> realizados = new ArrayList<>();
        for (Animal a : getTodosAnimais()) {
            realizados.addAll(getAtendimentosRealizados(a));
        }
        return realizados;
    }
}
