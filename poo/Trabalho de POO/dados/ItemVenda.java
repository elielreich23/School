package dados;

import java.math.BigDecimal;

public class ItemVenda {
    private Material material;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemVenda() {
    }

    public ItemVenda(Material material, int quantidade, BigDecimal precoUnitario) {
        this.material = material;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        if (precoUnitario == null) return BigDecimal.ZERO;
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return (material != null ? material.getTitulo() : "Item") + " x " + quantidade + " @ R$ " + precoUnitario + " = R$ " + getSubtotal();
    }
}
