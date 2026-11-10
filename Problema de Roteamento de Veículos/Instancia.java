import java.util.Random;

/**
 * Instância do CVRP com distância máxima por rota.
 * O vértice 0 é o depósito; os clientes são 1..nClientes().
 */
public class Instancia {
    private final double[] x;
    private final double[] y;
    private final int[] demanda;
    private final double[][] dist;
    private final int capacidade;
    private final double distanciaMaxima;

    public Instancia(double[] x, double[] y, int[] demanda, int capacidade, double distanciaMaxima) {
        this.x = x;
        this.y = y;
        this.demanda = demanda;
        this.capacidade = capacidade;
        this.distanciaMaxima = distanciaMaxima;
        int n = x.length;
        dist = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
            }
        }
    }

    /**
     * Gera uma instância aleatória: depósito no centro de [0,1000] x [0,1000],
     * clientes espalhados uniformemente e demandas entre 1 e 10.
     */
    public static Instancia aleatoria(int nClientes, int capacidade, double distanciaMaxima, long semente) {
        Random rnd = new Random(semente);
        double[] x = new double[nClientes + 1];
        double[] y = new double[nClientes + 1];
        int[] q = new int[nClientes + 1];
        x[0] = 500;
        y[0] = 500;
        for (int i = 1; i <= nClientes; i++) {
            x[i] = rnd.nextDouble() * 1000;
            y[i] = rnd.nextDouble() * 1000;
            q[i] = 1 + rnd.nextInt(10);
        }
        return new Instancia(x, y, q, capacidade, distanciaMaxima);
    }

    /** Número de clientes (sem contar o depósito). */
    public int nClientes() {
        return x.length - 1;
    }

    /** Distância entre os vértices i e j (0 = depósito). */
    public double d(int i, int j) {
        return dist[i][j];
    }

    /** Demanda do cliente i. */
    public int q(int i) {
        return demanda[i];
    }

    /** Capacidade Q de cada veículo. */
    public int Q() {
        return capacidade;
    }

    /** Distância máxima L que cada rota pode percorrer. */
    public double L() {
        return distanciaMaxima;
    }

    public double x(int i) {
        return x[i];
    }

    public double y(int i) {
        return y[i];
    }
}
