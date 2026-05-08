package model;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected int idade;
    protected String email;
    protected String telefone;
    protected String cpf;
    protected Endereco endereco;

    public Usuario(String nome, String cpf, int idade, String email, String telefone, Endereco endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Usuario() {

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

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}