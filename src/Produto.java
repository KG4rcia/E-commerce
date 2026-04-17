public class Produto {
    private String nome;
    private String descricao;
    private int quantidade;
    private double precoUnitario;
    private boolean grandePorte;

    public Produto(String nome, String descricao, double precoUnitario, int quantidade, boolean grandePorte) {
        this.nome = nome;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.grandePorte = grandePorte;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", grandePorte=" + grandePorte +
                '}';
    }

    public double calcularTotal() {
        return precoUnitario * quantidade;
    }

    public boolean verificarDisponibilidade() {
        if (this.quantidade > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void setNome(String nome) {
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: Nome não pode estar vazio");
        }

        this.nome = nome.trim().toUpperCase();
    }

    public void setDescricao(String descricao) {
        if (descricao.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: A descrição não pode estar vazio");
        }

        this.descricao = descricao.toUpperCase().trim();
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("- ERRO: A quantidade não pode ser negativa.");
        }

        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(double precoUnitario) {
        if (precoUnitario < 0) {
            throw new IllegalArgumentException("- ERRO: O valor unitário não pode ser negativa.");
        }

        this.precoUnitario = precoUnitario;
    }

    public void setGrandePorte(boolean grandePorte) {
        this.grandePorte = grandePorte;
    }

    public boolean isGrandePorte() {
        return grandePorte;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

}
