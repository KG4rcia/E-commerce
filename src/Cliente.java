import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private int idade;
    private String cpf;
    private String endereco;
    private String cep;
    private List<Pedido> pedidosCliente = new ArrayList<>();

    public Cliente(String nome, int idade, String cpf, String endereco, String cep) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.endereco = endereco;
        this.cep = cep;
    }

    public void fazerPedido(Pedido pedido) {
        pedidosCliente.add(pedido);
    }


    public void setNome(String nome) {
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: O nome do Cliente não pode estar vazio.");
        }

        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCpf(String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: O CPF do Cliente não pode estar vazio.");
        }

        this.cpf = cpf;
    }

    public void setEndereco(String endereco) {
        if (endereco.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: Endereço não pode estar vazio");
        }

        this.endereco = endereco;
    }

    public void setCep(String cep) {
        if (cep.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: CEP não pode estar vazio");
        }

        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCep() {
        return cep;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getCpf() {
        return cpf;
    }
}
