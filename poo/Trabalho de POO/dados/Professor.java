package dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Professor {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataContratacao;
    private String formacaoAcademica;
    private List<IdiomaProfissional> qualificacoes = new ArrayList<>();

    public Professor() {
    }

    public Professor(String nome, String cpf, String telefone, String email, LocalDate dataContratacao, String formacaoAcademica) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataContratacao = dataContratacao;
        this.formacaoAcademica = formacaoAcademica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getFormacaoAcademica() {
        return formacaoAcademica;
    }

    public void setFormacaoAcademica(String formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }

    public List<IdiomaProfissional> getQualificacoes() {
        return qualificacoes;
    }

    public void setQualificacoes(List<IdiomaProfissional> qualificacoes) {
        this.qualificacoes = qualificacoes;
    }

    public void adicionarQualificacao(IdiomaProfissional qualificacao) {
        if (qualificacao != null && !qualificacoes.contains(qualificacao)) {
            qualificacoes.add(qualificacao);
        }
    }

    public boolean isHabilitadoPara(String idioma, NivelCurso nivel) {
        if (idioma == null || nivel == null) return false;
        for (IdiomaProfissional q : qualificacoes) {
            if (q.getIdioma() != null && q.getIdioma().equalsIgnoreCase(idioma)) {
                if (q.getNivelProficiencia() != null && q.getNivelProficiencia().ordinal() >= nivel.ordinal()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor professor)) return false;
        return Objects.equals(cpf, professor.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Professor: " + nome + " (CPF: " + cpf + ", Formação: " + formacaoAcademica + ", Qualificações: " + qualificacoes + ")";
    }
}
