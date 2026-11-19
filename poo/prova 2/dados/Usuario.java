package dados;

public abstract class Usuario {
    private String login;
    private String senha;
    private int tempoPenalidade;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.tempoPenalidade = 0;
    }

    public abstract boolean podeEmprestLivro(int qtdAtual);
    public abstract boolean podeEmprestMono(int qtdAtual);

    public void acrescentarPenalidade(int dias) {
        if (dias > 0) {
            tempoPenalidade += dias;
        }
    }

    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public int getTempoPenalidade() { return tempoPenalidade; }
}
