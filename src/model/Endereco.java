package model;

public class Endereco {
    private Integer id;
    private String uf;
    private String cidade;
    private String bairro;
    private String cep;
    private int num;
    private double taxaEntrega;

    public Endereco(String estado, String cidade, String bairro, String cep, int num) {
        this.uf = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.num = num;
    }

    // Setters
    public void setUf(String uf) {
        if (uf.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.uf = uf;
    }

    public void setRua(String cidade) {
        if (cidade.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        if (bairro.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.bairro = bairro;
    }

    public void setCep(String cep) {
        if (cep.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.cep = cep;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        if (taxaEntrega < 0) {
            throw new IllegalArgumentException();
        }

        this.taxaEntrega = taxaEntrega;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    // Getters
    public String getUf() {
        return uf;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public int getNum() {
        return num;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public Integer getId() {
        return id;
    }
}
