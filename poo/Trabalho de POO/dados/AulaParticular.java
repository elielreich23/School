package dados;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AulaParticular {
    private String identificador;
    private Aluno aluno;
    private Professor professor;
    private LocalDate data;
    private LocalTime horarioInicio;
    private LocalTime horarioTermino;
    private String conteudoMinistrado;
    private String observacoes;

    public AulaParticular() {
    }

    public AulaParticular(String identificador, Aluno aluno, Professor professor, LocalDate data, LocalTime horarioInicio, LocalTime horarioTermino, String conteudoMinistrado, String observacoes) {
        this.identificador = identificador;
        this.aluno = aluno;
        this.professor = professor;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioTermino = horarioTermino;
        this.conteudoMinistrado = conteudoMinistrado;
        this.observacoes = observacoes;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
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

    public String getConteudoMinistrado() {
        return conteudoMinistrado;
    }

    public void setConteudoMinistrado(String conteudoMinistrado) {
        this.conteudoMinistrado = conteudoMinistrado;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AulaParticular that)) return false;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "Aula Particular [" + identificador + "] Aluno: " + (aluno != null ? aluno.getNome() : "N/A") +
                " - Prof: " + (professor != null ? professor.getNome() : "N/A") +
                " em " + data + " das " + horarioInicio + " às " + horarioTermino + " (Conteúdo: " + conteudoMinistrado + ")";
    }
}
