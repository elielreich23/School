package dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {
    private String codigo;
    private String idioma;
    private NivelCurso nivel;
    private int cargaHorariaTotal;
    private String descricaoConteudo;
    private String materiaisNecessarios;
    private List<Modulo> modulos = new ArrayList<>();

    public Curso() {
    }

    public Curso(String codigo, String idioma, NivelCurso nivel, int cargaHorariaTotal, String descricaoConteudo, String materiaisNecessarios) {
        this.codigo = codigo;
        this.idioma = idioma;
        this.nivel = nivel;
        this.cargaHorariaTotal = cargaHorariaTotal;
        this.descricaoConteudo = descricaoConteudo;
        this.materiaisNecessarios = materiaisNecessarios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getCargaHorariaTotal() {
        return cargaHorariaTotal;
    }

    public void setCargaHorariaTotal(int cargaHorariaTotal) {
        this.cargaHorariaTotal = cargaHorariaTotal;
    }

    public String getDescricaoConteudo() {
        return descricaoConteudo;
    }

    public void setDescricaoConteudo(String descricaoConteudo) {
        this.descricaoConteudo = descricaoConteudo;
    }

    public String getMateriaisNecessarios() {
        return materiaisNecessarios;
    }

    public void setMateriaisNecessarios(String materiaisNecessarios) {
        this.materiaisNecessarios = materiaisNecessarios;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public void adicionarModulo(Modulo modulo) {
        if (modulo != null && !modulos.contains(modulo)) {
            modulos.add(modulo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso curso)) return false;
        return Objects.equals(codigo, curso.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Curso [" + codigo + "] " + idioma + " - " + nivel + " (" + cargaHorariaTotal + "h) - " + modulos.size() + " módulos";
    }
}
