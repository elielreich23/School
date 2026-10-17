package dados;

import dados.enums.NivelCurso;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String codigo;
    private String nome;
    private String idioma;
    private NivelCurso nivel;
    private List<Modulo> modulos;

    public Curso(String codigo, String nome, String idioma, NivelCurso nivel) {
        this.codigo = codigo;
        this.nome = nome;
        this.idioma = idioma;
        this.nivel = nivel;
        this.modulos = new ArrayList<>();
    }

    public void adicionarModulo(Modulo modulo) {
        modulos.add(modulo);
    }

    public Modulo buscarModulo(int numero) {
        for (int i = 0; i < modulos.size(); i++) {
            Modulo modulo = modulos.get(i);
            if (modulo.getNumero() == numero) {
                return modulo;
            }
        }
        return null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public NivelCurso getNivel() {
        return nivel;
    }

    public void setNivel(NivelCurso nivel) {
        this.nivel = nivel;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    @Override
    public String toString() {
        return "Curso " + codigo + ": " + nome + " (" + idioma + " - " + nivel + ")";
    }
}
