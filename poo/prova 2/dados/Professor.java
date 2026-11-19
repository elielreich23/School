package dados;

public class Professor extends Usuario {
    private static final int limiteLivros = 10;
    private static final int limiteMonos = 10;

    public Professor(String login, String senha) {
        super(login, senha);
    }

    @Override
    public boolean podeEmprestLivro(int qtdAtual) {
        return qtdAtual < limiteLivros;
    }

    @Override
    public boolean podeEmprestMono(int qtdAtual) {
        return qtdAtual < limiteMonos;
    }
}
