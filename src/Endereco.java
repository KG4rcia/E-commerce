public class Endereco {
    private String uf;
    private String rua;
    private String bairro;
    private String cep;
    private int num;
    private String complemento;
    private double taxaEntrega;

    public Endereco(String estado, String rua, String bairro, String cep, int num) {
        this.uf = estado;
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.num = num;
    }

    public Endereco(String estado, String rua, String bairro, String cep, int num, String complemento) {
        this.uf = estado;
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.num = num;
        this.complemento = complemento;
    }

    public void calcularTaxa(Pedido pedido) {

        if (pedido.isPorteGrande()) {

        } else {

        }

    }

    public void setUf(String uf) {
        if (uf.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.uf = uf;
    }

    public void setRua(String rua) {
        if (rua.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.rua = rua;
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

    public void setComplemento(String complemento) {
        if (complemento.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.complemento = complemento;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        if (taxaEntrega < 0) {
            throw new IllegalArgumentException();
        }

        this.taxaEntrega = taxaEntrega;
    }

    public String getUf() {
        return uf;
    }

    public String getRua() {
        return rua;
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

    public String getComplemento() {
        return complemento;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }
}
