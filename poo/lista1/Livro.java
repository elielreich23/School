/*
 * Classe Livro para a lista 1 (atividade de POO).
 * Sem pacote, fica no diretório raiz "lista 1".
 */
import java.util.Objects;

public class Livro {
    private String isbn;
    private String titulo;
    private String autor;
    private double preco;

    // Construtor completo
    public Livro(String titulo, String autor, String isbn, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.preco = preco;
    }

    // Construtor com título, isbn e preço (autor opcional)
    public Livro(String titulo, String isbn, double preco) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.preco = preco;
        this.autor = null;
    }

    // Getters / Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return "Livro{ISBN='" + isbn + "', titulo='" + titulo + "', autor='" + autor + "', preco='" + preco + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Livro other = (Livro) obj;
        return Objects.equals(this.isbn, other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
