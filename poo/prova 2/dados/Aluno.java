package dados;

public class Aluno extends Usuario {
    private static final int limiteLivros = 5;
    private static final int limiteMonos = 5;

    public Aluno(String login, String senha) {
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
