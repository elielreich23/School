import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/** Salva uma solução como imagem SVG (abra o arquivo no navegador). Cada rota tem uma cor. */
public class Svg {
    private static final String[] CORES = {
        "#1F4E79", "#D9622B", "#2E7D32", "#6A1B9A", "#C62828", "#00838F",
        "#8D6E63", "#AD1457", "#558B2F", "#283593", "#EF6C00", "#4E342E"
    };

    public static void salvar(Solucao s, String arquivo) {
        Instancia inst = s.getInstancia();
        int tam = 600;
        double esc = tam / 1000.0;
        double raio = inst.nClientes() > 200 ? 2 : 4;
        try (PrintWriter out = new PrintWriter(arquivo)) {
            out.printf("<svg xmlns='http://www.w3.org/2000/svg' width='%d' height='%d' viewBox='-10 -30 %d %d'>%n",
                    tam + 20, tam + 40, tam + 20, tam + 40);
            out.println("<rect x='-10' y='-30' width='100%' height='100%' fill='white'/>");
            out.printf("<text x='0' y='-10' font-family='sans-serif' font-size='14'>%d clientes   %d rotas   distância = %.1f   (Q = %d, L = %.0f)</text>%n",
                    inst.nClientes(), s.numeroDeRotas(), s.custo(), inst.Q(), inst.L());
            for (int k = 0; k < s.getRotas().size(); k++) {
                Rota r = s.getRotas().get(k);
                if (r.vazia()) {
                    continue;
                }
                String cor = CORES[k % CORES.length];
                StringBuilder pts = new StringBuilder();
                pts.append(String.format(Locale.US, "%.1f,%.1f ", inst.x(0) * esc, inst.y(0) * esc));
                for (int c : r.getClientes()) {
                    pts.append(String.format(Locale.US, "%.1f,%.1f ", inst.x(c) * esc, inst.y(c) * esc));
                }
                out.printf("<polygon points='%s' fill='none' stroke='%s' stroke-width='1.8'/>%n", pts, cor);
                for (int c : r.getClientes()) {
                    out.printf(Locale.US, "<circle cx='%.1f' cy='%.1f' r='%.1f' fill='%s'/>%n",
                            inst.x(c) * esc, inst.y(c) * esc, raio, cor);
                }
            }
            out.printf(Locale.US, "<rect x='%.1f' y='%.1f' width='12' height='12' fill='black'/>%n",
                    inst.x(0) * esc - 6, inst.y(0) * esc - 6);
            out.println("</svg>");
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + arquivo + ": " + e.getMessage());
        }
    }
}
