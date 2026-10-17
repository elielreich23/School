package matriz;

import java.util.ArrayList;
import java.util.List;

/** Estrutura bidimensional genérica de tamanho fixo. */
public class Matriz<T> {
    private final int linhas;
    private final int colunas;
    private final T[][] elementos;

    @SuppressWarnings("unchecked")
    public Matriz(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("A matriz deve ter ao menos uma linha e uma coluna.");
        }
        linhas = n;
        colunas = m;
        elementos = (T[][]) new Object[n][m];
    }

    /** Armazena o objeto na posição indicada; retorna false se ela for inválida. */
    public boolean set(T objeto, int i, int j) {
        if (!posicaoValida(i, j)) {
            return false;
        }
        elementos[i][j] = objeto;
        return true;
    }

    /** Retorna null quando a posição estiver fora dos limites. */
    public T get(int i, int j) {
        if (!posicaoValida(i, j)) {
            return null;
        }
        return elementos[i][j];
    }

    /** Retorna os elementos da linha; uma linha inválida produz uma lista vazia. */
    public List<T> getLinha(int linha) {
        List<T> resultado = new ArrayList<>();
        if (linha < 0 || linha >= linhas) {
            return resultado;
        }
        for (int coluna = 0; coluna < colunas; coluna++) {
            resultado.add(elementos[linha][coluna]);
        }
        return resultado;
    }

    /** Retorna os elementos da coluna; uma coluna inválida produz uma lista vazia. */
    public List<T> getColuna(int coluna) {
        List<T> resultado = new ArrayList<>();
        if (coluna < 0 || coluna >= colunas) {
            return resultado;
        }
        for (int linha = 0; linha < linhas; linha++) {
            resultado.add(elementos[linha][coluna]);
        }
        return resultado;
    }

    /** Retorna a diagonal principal, até o menor entre o número de linhas e colunas. */
    public List<T> getDiagonalPrincipal() {
        List<T> resultado = new ArrayList<>();
        for (int i = 0; i < Math.min(linhas, colunas); i++) {
            resultado.add(elementos[i][i]);
        }
        return resultado;
    }

    public int getNumeroLinhas() {
        return linhas;
    }

    public int getNumeroColunas() {
        return colunas;
    }

    private boolean posicaoValida(int i, int j) {
        return i >= 0 && i < linhas && j >= 0 && j < colunas;
    }
}
