package dados;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Encontro {
    private LocalDate data;
    private LocalTime horarioInicio;
    private LocalTime horarioTermino;

    public Encontro() {
    }

    public Encontro(LocalDate data, LocalTime horarioInicio, LocalTime horarioTermino) {
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioTermino = horarioTermino;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioTermino() {
        return horarioTermino;
    }

    public void setHorarioTermino(LocalTime horarioTermino) {
        this.horarioTermino = horarioTermino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Encontro encontro)) return false;
        return Objects.equals(data, encontro.data) &&
                Objects.equals(horarioInicio, encontro.horarioInicio) &&
                Objects.equals(horarioTermino, encontro.horarioTermino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, horarioInicio, horarioTermino);
    }

    @Override
    public String toString() {
        return "Encontro em " + data + " das " + horarioInicio + " às " + horarioTermino;
    }
}
