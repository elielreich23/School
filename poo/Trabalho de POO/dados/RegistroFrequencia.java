package dados;

import java.time.LocalDate;

public class RegistroFrequencia {
    private Encontro encontro;
    private LocalDate dataAula;
    private boolean presente;

    public RegistroFrequencia() {
    }

    public RegistroFrequencia(Encontro encontro, LocalDate dataAula, boolean presente) {
        this.encontro = encontro;
        this.dataAula = dataAula;
        this.presente = presente;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    public LocalDate getDataAula() {
        return dataAula;
    }

    public void setDataAula(LocalDate dataAula) {
        this.dataAula = dataAula;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    @Override
    public String toString() {
        return "Frequência em " + dataAula + ": " + (presente ? "PRESENTE" : "FALTA");
    }
}
