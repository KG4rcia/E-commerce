public class Pedido {
    private String nomeCliente;
    private String nomeProduto;
    private String descricaoProduto;
    private int quantidadeProduto;
    private double valorProduto;
    private String status;

    public Pedido(String nomeCliente, String nomeProduto, String descricaoProduto, int quantidadeProduto, double valorProduto) {
        this.nomeCliente = nomeCliente;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidadeProduto = quantidadeProduto;
        this.valorProduto = valorProduto;
        this.status = "PENDENTE";
    }

    public void setStatus(String statusAtual) {
        if (statusAtual.toUpperCase().equals("PENDENTE") || statusAtual.toUpperCase().equals("FINALIZADA") || statusAtual.toUpperCase().equals("CANCELADA")) {
            throw new IllegalArgumentException();
        }

        this.status = statusAtual;
    }

    public String getStatus() {
        return status;
    }

    public String getNomeCliente() {
        return nomeCliente;
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


    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.nomeCliente = nomeCliente;
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