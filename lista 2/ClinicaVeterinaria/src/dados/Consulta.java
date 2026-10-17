package dados;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int codigo;
    private LocalDate data;
    private LocalTime horario;
    private Veterinario veterinario;
    private Animal paciente;

    public Consulta(int codigo, LocalDate data, LocalTime horario) {
        this.codigo = codigo; this.data = data; this.horario = horario;
    }
    public int getCodigo() { return codigo; }
    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public Veterinario getVeterinario() { return veterinario; }
    public Animal getPaciente() { return paciente; }
    public void alocarAnimal(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("Animal inválido.");
        paciente = animal;
    }
    public void alocarVeterinario(Veterinario veterinario) {
        if (veterinario == null) throw new IllegalArgumentException("Veterinário inválido.");
        this.veterinario = veterinario;
    }
    @Override public String toString() {
        return String.format("Consulta %d | %s às %s | Paciente: %s | Veterinário: %s", codigo, data, horario,
                paciente == null ? "não definido" : paciente.getNome(),
                veterinario == null ? "não definido" : veterinario.getNome());
    }
}
