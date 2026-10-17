package dados;

import java.util.Objects;

public class IdiomaProfissional {
    private String idioma;
    private NivelCurso nivelProficiencia;

    public IdiomaProfissional() {
    }

    public IdiomaProfissional(String idioma, NivelCurso nivelProficiencia) {
        this.idioma = idioma;
        this.nivelProficiencia = nivelProficiencia;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public NivelCurso getNivelProficiencia() {
        return nivelProficiencia;
    }

    public void setNivelProficiencia(NivelCurso nivelProficiencia) {
        this.nivelProficiencia = nivelProficiencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdiomaProfissional that)) return false;
        return Objects.equals(idioma != null ? idioma.toLowerCase() : null, that.idioma != null ? that.idioma.toLowerCase() : null) &&
                nivelProficiencia == that.nivelProficiencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idioma != null ? idioma.toLowerCase() : null, nivelProficiencia);
    }

    @Override
    public String toString() {
        return idioma + " (" + nivelProficiencia + ")";
    }
}
