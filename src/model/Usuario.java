package model;

public abstract class Usuario {
    protected String nome;
    protected int idade;
    protected String cpf;
    protected Endereco endereco;

    public Usuario(String nome, int idade, String cpf, Endereco endereco) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public abstract void info();


    // Setters
    public void setNome(String nome) {
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: O nome do model.Cliente não pode estar vazio.");
        }

        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCpf(String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("- ERRO: O CPF do model.Cliente não pode estar vazio.");
        }

        this.cpf = cpf;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getCpf() {
        return cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

}