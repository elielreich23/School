package dados;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Gerador implements ISequencia {
    private final List<Integer> sequencia = new ArrayList<>();

    public abstract void gerar(int quantidade);

    public List<Integer> getSequencia() {
        return sequencia;
    }

    @Override
    public int sortear() {
        if (sequencia.isEmpty()) {
            throw new IllegalStateException("A sequência está vazia.");
        }
        return sequencia.get(ThreadLocalRandom.current().nextInt(sequencia.size()));
    }

    @Override
    public long somatorio() {
        long soma = 0;
        for (int termo : sequencia) soma += termo;
        return soma;
    }

    @Override
    public double mediaAritmetica() {
        return sequencia.isEmpty() ? 0 : (double) somatorio() / sequencia.size();
    }

    @Override
    public double mediaGeometrica() {
        if (sequencia.isEmpty()) return 0;
        if (sequencia.stream().anyMatch(termo -> termo == 0)) return 0;
        if (sequencia.stream().anyMatch(termo -> termo < 0)) {
            throw new IllegalStateException("Média geométrica real não definida para termos negativos.");
        }
        double somaLogaritmos = 0;
        for (int termo : sequencia) somaLogaritmos += Math.log(termo);
        return Math.exp(somaLogaritmos / sequencia.size());
    }

    @Override
    public double variancia() {
        if (sequencia.size() < 2) return 0;
        double media = mediaAritmetica();
        double somaQuadrados = 0;
        for (int termo : sequencia) {
            double diferenca = termo - media;
            somaQuadrados += diferenca * diferenca;
        }
        return somaQuadrados / (sequencia.size() - 1);
    }

    @Override
    public double desvioPadrao() {
        return Math.sqrt(variancia());
    }

    @Override
    public long amplitude() {
        if (sequencia.isEmpty()) return 0;
        int menor = sequencia.get(0), maior = sequencia.get(0);
        for (int termo : sequencia) {
            if (termo < menor) menor = termo;
            if (termo > maior) maior = termo;
        }
        return (long) maior - menor;
    }
}
