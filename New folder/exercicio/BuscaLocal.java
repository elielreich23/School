/** Uma busca local: recebe uma solução e devolve uma solução igual ou melhor (e viável). */
public interface BuscaLocal {
    Solucao melhorar(Solucao s);
}
