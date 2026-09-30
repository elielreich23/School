package dados;

import java.util.Objects;

public class IdiomaInteresse {
    private String idioma;
    private NivelCurso nivelConhecimento;

    public IdiomaInteresse() {
    }

    public IdiomaInteresse(String idioma, NivelCurso nivelConhecimento) {
        this.idioma = idioma;
        this.nivelConhecimento = nivelConhecimento;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public NivelCurso getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(NivelCurso nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdiomaInteresse that)) return false;
        return Objects.equals(idioma != null ? idioma.toLowerCase() : null, that.idioma != null ? that.idioma.toLowerCase() : null) &&
                nivelConhecimento == that.nivelConhecimento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idioma != null ? idioma.toLowerCase() : null, nivelConhecimento);
    }

    @Override
    public String toString() {
        return idioma + " (" + nivelConhecimento + ")";
    }
}
