package negocio;

import dados.Animal;
import dados.Consulta;
import dados.Dono;
import dados.Veterinario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Clinica {
    private ArrayList<Animal> animais = new ArrayList<>();
    private ArrayList<Veterinario> veterinarios = new ArrayList<>();
    private ArrayList<Dono> donos = new ArrayList<>();
    private ArrayList<Consulta> consultas = new ArrayList<>();

    public void cadastrarAnimal(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("Animal inválido.");
        if (!animais.contains(animal)) animais.add(animal);
        if (animal.getDono() != null) {
            cadastrarDono(animal.getDono());
            animal.getDono().cadastrarAnimal(animal);
        }
    }
    public void cadastrarVeterinario(Veterinario veterinario) {
        if (veterinario == null) throw new IllegalArgumentException("Veterinário inválido.");
        if (!veterinarios.contains(veterinario)) veterinarios.add(veterinario);
    }
    public void cadastrarDono(Dono dono) {
        if (dono == null) throw new IllegalArgumentException("Dono inválido.");
        if (!donos.contains(dono)) donos.add(dono);
    }
    public void marcarConsulta(Consulta consulta) {
        if (consulta == null) throw new IllegalArgumentException("Consulta inválida.");
        if (!consultas.contains(consulta)) consultas.add(consulta);
    }
    public void desmarcarConsulta(Consulta consulta) { consultas.remove(consulta); }
    public ArrayList<Animal> getAnimais() { return animais; }
    public ArrayList<Veterinario> getVeterinarios() { return veterinarios; }
    public ArrayList<Dono> getDonos() { return donos; }
    public ArrayList<Consulta> getConsultas() { return consultas; }

    public ArrayList<Consulta> listarConsultasPorData(LocalDate data) {
        ArrayList<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas)
            if (consulta.getData().equals(data)) resultado.add(consulta);
        resultado.sort(Comparator.comparing(Consulta::getHorario));
        return resultado;
    }
}
