/**
 * Compilar:  javac *.java
 * Executar:  java Main
 */
public class Main {

    public static void main(String[] args) {
        // Experimente mudar Q (capacidade) e L (distância máxima por rota)!
        int nClientes = 50;
        int Q = 50;
        double L = 2000;
        Instancia inst = Instancia.aleatoria(nClientes, Q, L, 7);

        System.out.printf("===== %d clientes, Q = %d, L = %.0f =====%n", nClientes, Q, L);
        executar("vmp", inst, new VizinhoMaisProximo());
        executar("vmp_2opt", inst, new VizinhoMaisProximo(), new DoisOptIntra());
        executar("vmp_2opt_reloc", inst, new VizinhoMaisProximo(), new DoisOptIntra(), new RelocateInter());
    }

    /** Constrói uma solução, aplica as buscas locais em sequência, valida, imprime e salva um SVG. */
    static Solucao executar(String nome, Instancia inst, Construtor construtor, BuscaLocal... buscas) {
        try {
            long inicio = System.nanoTime();
            Solucao s = construtor.construir(inst);
            for (BuscaLocal b : buscas) {
                s = b.melhorar(s);
            }
            double ms = (System.nanoTime() - inicio) / 1e6;

            String erro = s.validar();
            if (erro != null) {
                System.out.printf("%-16s SOLUÇÃO INVÁLIDA: %s%n%s", nome, erro, s);
                return null;
            }
            System.out.printf("%-16s distância = %8.1f  rotas = %2d  tempo = %7.2f ms%n",
                    nome, s.custo(), s.numeroDeRotas(), ms);
            Svg.salvar(s, nome + ".svg");
            return s;
        } catch (UnsupportedOperationException e) {
            System.out.printf("%-16s (pendente) %s%n", nome, e.getMessage());
            return null;
        }
    }
}
