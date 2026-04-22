public class Pedido {
    private Cliente cliente;
    private String nomeProduto;
    private String descricaoProduto;
    private int quantidadeProduto;
    private double valorProduto;
    private boolean porteGrande;
    private String status;

    public Pedido(Cliente cliente, String nomeProduto, String descricaoProduto, int quantidadeProduto, double valorProduto, boolean porteGrande) {
        this.cliente = cliente;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidadeProduto = quantidadeProduto;
        this.valorProduto = valorProduto;
        this.porteGrande = porteGrande;
    }

    public void setStatus(String statusAtual) {
        if (statusAtual.toUpperCase().equals("PENDENTE") || statusAtual.toUpperCase().equals("FINALIZADA") || statusAtual.toUpperCase().equals("CANCELADA")) {
            throw new IllegalArgumentException();
        }

        this.status = statusAtual;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "nomeCliente='" + cliente.getNome() + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", descricaoProduto='" + descricaoProduto + '\'' +
                ", quantidadeProduto=" + quantidadeProduto +
                ", valorProduto=" + valorProduto +
                ", porteGrande=" + porteGrande +
                ", status='" + status + '\'' +
                '}';
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isPorteGrande() {
        return porteGrande;
    }

    public String getStatus() {
        return status;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public double getValorProduto() {
        return valorProduto;
    }


    public void setNomeProduto(String nomeProduto) {
        if (nomeProduto.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.nomeProduto = nomeProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        if (descricaoProduto.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.descricaoProduto = descricaoProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        if (quantidadeProduto <= 0) {
            throw new IllegalArgumentException();
        }

        this.quantidadeProduto = quantidadeProduto;
    }

    public void setValorProduto(double valorProduto) {
        if (quantidadeProduto <= 0) {
            throw new IllegalArgumentException();
        }

        this.valorProduto = valorProduto;
    }
}