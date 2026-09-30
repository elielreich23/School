package dados;

public class Veterinario extends Pessoa {
    private String especialidade;

    public Veterinario() {
        super();
    }

    public Veterinario(String nome, long cpf, String telefone, String especialidade) {
        super(nome, cpf, telefone);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return super.toString() + " | Especialidade: " + especialidade;
    }
}
