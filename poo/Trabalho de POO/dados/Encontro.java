package dados;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Encontro {
    private LocalDate data;
    private LocalTime horarioInicio;
    private LocalTime horarioTermino;
    private Modulo modulo;

    public Encontro() {
    }

    public Encontro(LocalDate data, LocalTime horarioInicio, LocalTime horarioTermino) {
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioTermino = horarioTermino;
    }

    public Encontro(LocalDate data, LocalTime horarioInicio, LocalTime horarioTermino, Modulo modulo) {
        this(data, horarioInicio, horarioTermino);
        this.modulo = modulo;
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

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Encontro encontro)) return false;
        return Objects.equals(data, encontro.data) &&
                Objects.equals(horarioInicio, encontro.horarioInicio) &&
                Objects.equals(horarioTermino, encontro.horarioTermino) &&
                Objects.equals(modulo, encontro.modulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, horarioInicio, horarioTermino, modulo);
    }

    @Override
    public String toString() {
        return "Encontro em " + data + " das " + horarioInicio + " às " + horarioTermino +
                (modulo != null ? " — Módulo " + modulo.getNumero() : "");
    }
}
