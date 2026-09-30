package dados;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioTurma {
    private DayOfWeek diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioTermino;
    private String sala;

    public HorarioTurma() {
    }

    public HorarioTurma(DayOfWeek diaSemana, LocalTime horarioInicio, LocalTime horarioTermino, String sala) {
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioTermino = horarioTermino;
        this.sala = sala;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
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

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return diaSemana + " das " + horarioInicio + " às " + horarioTermino + " (Sala: " + sala + ")";
    }
}
