package model;

import util.Gerenciador;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Vendedor extends Usuario {
    private int vendas;

    public Vendedor(String nome, String cpf, int idade, String email, String telefone, Endereco endereco) {
        super(nome, cpf, idade, email, telefone, endereco);
    }


    @Override
    public void info() {
        System.out.println("- VENDEDOR: ");
        System.out.println("NOME: " + super.getNome());
        System.out.println("IDADE: " + super.getIdade());
        System.out.println("CPF: " + super.getCpf());
        System.out.println("ENDEREÇO: " + super.getEndereco());
        System.out.println("UF: " + super.getEndereco().getUf());
        System.out.println("CIDADE: " + super.getEndereco().getCidade());
        System.out.println("NÚMERO DE VENDAS: " + this.vendas);
    }

    public void vendeu() {
        vendas++;
    }

    public void cadastrarProduto(Scanner scanner, Gerenciador gerenciador) {
        String nomeProduto;
        String descricaoProduto;
        boolean verificarGrandePorte = false;
        double precoUnitario;
        int quantidadeEstoque;

        System.out.println("=".repeat(20));
        while (true) {
            System.out.print("- INFORME O NOME DO PRODUTO: ");
            nomeProduto = scanner.nextLine();

            try {

                if (nomeProduto.isEmpty()) {
                    throw new IllegalArgumentException("Preencha o campo corretamente.");
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: O nome do produto não pode estar vazio.");
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }

        }

        while (true) {
            System.out.print("- INFORME A DESCRIÇÃO DO PRODUTO: ");
            descricaoProduto = scanner.nextLine();

            try {
                if (descricaoProduto.isEmpty()) {
                    throw new IllegalArgumentException("A descrição do produto não pode estar vazio.");
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }

        }

        while (true) {
            try {
                System.out.print("- INFORME O PREÇO UNITÁRIO DO PRODUTO: ");
                precoUnitario = scanner.nextDouble();
                scanner.nextLine();

                if (precoUnitario <= 0) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O valor do produto não pode ser menor ou igual a zero.");
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números, nada de texto.");
                scanner.nextLine();
            }

        }

        while (true) {
            try {
                System.out.print("- INFORME A QUANTIDADE EM ESTOQUE: ");
                quantidadeEstoque = scanner.nextInt();
                scanner.nextLine();

                if (quantidadeEstoque <= 0) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: A quantidade do produto não pode ser menor ou igual a zero.");
                scanner.nextLine();

            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números, nada de texto.");
                scanner.nextLine();
            }

        }

        while (true) {
            try {
                System.out.print("- É UM PRODUTO DE GRANDE PORTE? [SIM/NÃO]: ");
                String grandePorte = scanner.nextLine().trim().toUpperCase();

                if (grandePorte.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (!grandePorte.equals("SIM") && !grandePorte.equals("NÃO")) {
                    throw new IllegalArgumentException();
                }

                if (grandePorte.equals("SIM")) {
                    verificarGrandePorte = true;
                    break;
                } else if (grandePorte.equals("NÃO")) {
                    verificarGrandePorte = false;
                    break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: Preencha o campo corretamente, é somente \"SIM\" ou \"NÃO\". Números e outras palavras não são válidos.");
            }

        }

        try {
            Produto produto = new Produto(nomeProduto.toUpperCase(), descricaoProduto.toUpperCase(), precoUnitario, quantidadeEstoque, verificarGrandePorte, this);
            gerenciador.adicionarProduto(produto);
            System.out.println(" - PRODUTO CRIADO - ");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    // Getters
    public int getVendas() {
        return vendas;
    }

    // Setters
    public void setVendas(int vendas) {
        this.vendas = vendas;
    }
}
