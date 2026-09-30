package dados;

import java.util.ArrayList;
import java.util.List;

public class Atendimento {
    private String dataEntrada;
    private String dataSaida;
    private boolean finalizado;
    private float valor;
    private Veterinario veterinarioResponsavel;
    private List<Medicamento> medicamentos;

    public Atendimento() {
        this.medicamentos = new ArrayList<>();
        this.finalizado = false;
        this.valor = 0.0f;
        this.dataSaida = "Em andamento";
    }

    public Atendimento(String dataEntrada) {
        this();
        this.dataEntrada = dataEntrada;
    }

    public Atendimento(String dataEntrada, Veterinario veterinarioResponsavel) {
        this();
        this.dataEntrada = dataEntrada;
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public float calculaValor() {
        float total = 0.0f;
        if (medicamentos != null) {
            for (Medicamento m : medicamentos) {
                if (m != null) {
                    total += m.getPreco();
                }
            }
        }
        this.valor = total;
        return total;
    }

    public void adicionaVeterinario(Veterinario v) {
        this.veterinarioResponsavel = v;
    }

    public void adicionaMedicamento(Medicamento m) {
        if (this.medicamentos == null) {
            this.medicamentos = new ArrayList<>();
        }
        this.medicamentos.add(m);
        calculaValor();
    }

    public void finalizaAtendimento() {
        this.finalizado = true;
        calculaValor();
    }

    public void finalizaAtendimento(String dataSaida) {
        this.dataSaida = dataSaida;
        this.finalizado = true;
        calculaValor();
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
        if (finalizado) {
            calculaValor();
        }
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
        calculaValor();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Atendimento [Entrada: ").append(dataEntrada != null ? dataEntrada : "-")
          .append(" | Saída: ").append(dataSaida != null ? dataSaida : "-")
          .append(" | Status: ").append(finalizado ? "Finalizado" : "Em andamento")
          .append(String.format(" | Valor: R$ %.2f", calculaValor()))
          .append(" | Vet: ").append(veterinarioResponsavel != null ? veterinarioResponsavel.getNome() : "Não atribuído")
          .append("]");

        if (medicamentos != null && !medicamentos.isEmpty()) {
            sb.append("\n    Medicamentos (").append(medicamentos.size()).append("):");
            for (Medicamento m : medicamentos) {
                sb.append("\n      - ").append(m.toString());
            }
        } else {
            sb.append("\n    Medicamentos: Nenhum medicamento ministrado.");
        }

        return sb.toString();
    }
}
