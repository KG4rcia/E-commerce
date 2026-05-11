package model;

public class Pedido {
    private int id;
    private Cliente cliente;
    private int idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private int quantidadeProduto;
    private double valorProduto;
    private boolean porteGrande;
    private String status = "PENDENTE";
    private Vendedor vendedor;
    private double taxaEntrega;

    public Pedido(Cliente cliente, int idProduto, String nomeProduto, String descricaoProduto, int quantidadeProduto, double valorProduto, boolean porteGrande, Vendedor vendedor) {
        this.cliente = cliente;
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidadeProduto = quantidadeProduto;
        this.valorProduto = valorProduto;
        this.porteGrande = porteGrande;
        this.vendedor = vendedor;
        this.taxaEntrega = calcularTaxaEntrega();
    }

    @Override
    public String toString() {
        return "model.Pedido{" +
                "nomeCliente='" + cliente.getNome() + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", descricaoProduto='" + descricaoProduto + '\'' +
                ", quantidadeProduto=" + quantidadeProduto +
                ", valorProduto=" + valorProduto +
                ", porteGrande=" + porteGrande +
                ", status='" + status + '\'' +
                '}';
    }

    public void infoPedido() {
        System.out.println("=".repeat(20));
        System.out.println("- NOME DO CLIENTE: " + this.cliente.getNome());
        System.out.println("- CPF DO CLUENTE: " + this.cliente.getCpf());
        System.out.println("- PRODUTO: " + this.nomeProduto);
        System.out.println("- STATUS DO PEDIDOD: " + this.status);
        System.out.println("- ID PRODUTO: " + this.idProduto);
        System.out.println("- GRANDE PORTE: " + (this.porteGrande ? "SIM" : "NÃO"));
        System.out.println();
        System.out.println("- QUANTIDADE | VALOR UNITÁRIO | TAXA DE ENTREGA");
        System.out.println(this.quantidadeProduto + " | " + this.valorProduto + " | " + this.taxaEntrega);
        System.out.println("- VALOR TOTAL: R$ " + ((this.quantidadeProduto*this.valorProduto) + this.taxaEntrega) + "0");
        System.out.println();
    }

    public double calcularTaxaEntrega() {
        if (this.isPorteGrande()) {
            taxaEntrega = 0.10 * this.valorProduto;
            return taxaEntrega;
        } else {
            return 0;
        }

    }

    // Getters
    public int getId() {
        return id;
    }

    public boolean isPorteGrande() {
        return porteGrande;
    }

    public Cliente getCliente() {
        return cliente;
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

    public int getIdProduto() {
        return idProduto;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setPorteGrande(boolean porteGrande) {
        this.porteGrande = porteGrande;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public void setStatus(String statusAtual) {
        if (!statusAtual.toUpperCase().equals("PENDENTE") && !statusAtual.toUpperCase().equals("FINALIZADO") && !statusAtual.toUpperCase().equals("CANCELADO")) {
            throw new IllegalArgumentException("Status inválido.");
        }

        this.status = statusAtual.toUpperCase();
    }

}