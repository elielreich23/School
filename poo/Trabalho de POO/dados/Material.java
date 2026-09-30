package dados;

import java.math.BigDecimal;
import java.util.Objects;

public class Material {
    private String codigo;
    private String titulo;
    private String editora;
    private String edicao;
    private String tipo;
    private BigDecimal preco;

    public Material() {
    }

    public Material(String codigo, String titulo, String editora, String edicao, String tipo, BigDecimal preco) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.tipo = tipo;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material material)) return false;
        return Objects.equals(codigo, material.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Material [" + codigo + "] " + titulo + " (Ed. " + edicao + ", " + editora + ") - R$ " + preco;
    }
}
