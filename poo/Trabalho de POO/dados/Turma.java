package dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Turma {
    private String codigo;
    private Curso curso;
    private Professor professor;
    private LocalDate inicioMatricula;
    private LocalDate fimMatricula;
    private int minimoAlunos;
    private int maximoAlunos;
    private SituacaoTurma situacao = SituacaoTurma.ABERTA;
    private List<HorarioTurma> horarios = new ArrayList<>();
    private List<Encontro> encontros = new ArrayList<>();

    public Turma() {
    }

    public Turma(String codigo, Curso curso, Professor professor, LocalDate inicioMatricula, LocalDate fimMatricula, int minimoAlunos, int maximoAlunos) {
        this.codigo = codigo;
        this.curso = curso;
        this.professor = professor;
        this.inicioMatricula = inicioMatricula;
        this.fimMatricula = fimMatricula;
        this.minimoAlunos = minimoAlunos;
        this.maximoAlunos = maximoAlunos;
        this.situacao = SituacaoTurma.ABERTA;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public LocalDate getInicioMatricula() {
        return inicioMatricula;
    }

    public void setInicioMatricula(LocalDate inicioMatricula) {
        this.inicioMatricula = inicioMatricula;
    }

    public LocalDate getFimMatricula() {
        return fimMatricula;
    }

    public void setFimMatricula(LocalDate fimMatricula) {
        this.fimMatricula = fimMatricula;
    }

    public int getMinimoAlunos() {
        return minimoAlunos;
    }

    public void setMinimoAlunos(int minimoAlunos) {
        this.minimoAlunos = minimoAlunos;
    }

    public int getMaximoAlunos() {
        return maximoAlunos;
    }

    public void setMaximoAlunos(int maximoAlunos) {
        this.maximoAlunos = maximoAlunos;
    }

    public SituacaoTurma getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoTurma situacao) {
        this.situacao = situacao;
    }

    public List<HorarioTurma> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioTurma> horarios) {
        this.horarios = horarios;
    }

    public void adicionarHorario(HorarioTurma horario) {
        if (horario != null && !horarios.contains(horario)) {
            horarios.add(horario);
        }
    }

    public List<Encontro> getEncontros() {
        return encontros;
    }

    public void setEncontros(List<Encontro> encontros) {
        this.encontros = encontros;
    }

    public void adicionarEncontro(Encontro encontro) {
        if (encontro != null && !encontros.contains(encontro)) {
            encontros.add(encontro);
        }
    }

    public boolean isPeriodoMatriculaAberto(LocalDate data) {
        if (data == null || inicioMatricula == null || fimMatricula == null) return false;
        return !data.isBefore(inicioMatricula) && !data.isAfter(fimMatricula);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turma turma)) return false;
        return Objects.equals(codigo, turma.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Turma [" + codigo + "] " + (curso != null ? curso.getIdioma() : "Curso") + " - Prof: " +
                (professor != null ? professor.getNome() : "Sem prof") + " - Situação: " + situacao +
                " (" + minimoAlunos + " a " + maximoAlunos + " alunos)";
    }
}
