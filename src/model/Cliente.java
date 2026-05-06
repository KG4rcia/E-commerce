package model;

import util.Gerenciador;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Usuario {
    private List<Pedido> pedidosCliente = new ArrayList<>();

    public Cliente(String nome, int idade, String cpf, Endereco endereco) {
        super(nome, idade, cpf, endereco);
    }

    @Override
    public void info() {
        System.out.println("- CLIENTE: ");
        System.out.println("NOME: " + super.getNome());
        System.out.println("IDADE: " + super.getIdade());
        System.out.println("CPF: " + super.getCpf());
        System.out.println("ENDEREÇO: " + super.getEndereco());
        System.out.println("UF: " + super.getEndereco().getUf());
        System.out.println("CIDADE: " + super.getEndereco().getCidade());
    }

    public void fazerPedido(Scanner scanner, Gerenciador gerenciador) {
        if (gerenciador.getUsuarios().isEmpty()) {
            System.out.println("- ERRO: Não é possível realizar uma venda se não há clientes cadastrados.");
            return;
        }

        int numProdutoVendido = 0;
        String clienteCPF = "";
        String vendedor = "";
        int quantidadeProduto;
        boolean encontrado = false;

        while (true) {
            try {
                gerenciador.listarProdutos();

                System.out.print("\nINFORME O PRODUTO A SER VENDIDO: ");
                numProdutoVendido = scanner.nextInt();

                if ((numProdutoVendido-1) > gerenciador.getProdutos().size()) {
                    throw new IndexOutOfBoundsException();
                }

                scanner.nextLine();
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("- ERRO: Posição inválida. Por favor, passar posição válida.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: Passar apenas números inteiros, nada de letras ou números com decimais.");
                scanner.nextLine();
            }
        }

        for (Produto produto : gerenciador.getProdutos()) {
            if (produto.equals(gerenciador.getProdutos().get(numProdutoVendido-1))) {
                encontrado = true;
                if (produto.verificarDisponibilidade()) {
                    System.out.println("-".repeat(5));
                    System.out.println("INFORMAÇÕES DO PRODUTO:");
                    System.out.println("| NOME: " + produto.getNome());
                    System.out.println("| DESCRIÇÃO: " + produto.getDescricao());
                    System.out.println("| QUANTIDADE: " + produto.getQuantidade());
                    System.out.println("| VALOR UNITÁRIO: R$ " + produto.getPrecoUnitario());

                    while (true) {
                        try {
                            System.out.print("\nINFORME A QUANTIDADE: ");
                            quantidadeProduto = scanner.nextInt();

                            if (quantidadeProduto > produto.getQuantidade()) {
                                throw new IllegalArgumentException();
                            }

                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("- ERRO: Preencha o campo somente com a quantidade.");
                            scanner.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println("- ERRO: Não há estoque suficiente para realizar essa compra.");
                            scanner.nextLine();
                        }
                    }

                    while (true) {
                        try {
                            gerenciador.listarClientes();

                            Pedido pedido = new Pedido(
                                    this,
                                    numProdutoVendido,
                                    produto.getNome(),
                                    produto.getDescricao(),
                                    quantidadeProduto,
                                    produto.getPrecoUnitario(),
                                    produto.isGrandePorte(),
                                    produto.getVendedor()
                            );

                            pedido.setTaxaEntrega(super.endereco.calcularTaxa(pedido));

                            produto.getVendedor().vendeu();
                            produto.setQuantidade(produto.getQuantidade() - quantidadeProduto);
                            pedidosCliente.add(pedido);
                            gerenciador.adicionarPedido(pedido);

                            System.out.println(" - VENDA REALIZADA COM SUCESSO - ");

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.printf("- ERRO: O cliente com o CPF %s não existe. Por favor, passar CPF válido.\n", clienteCPF);
                        }
                    }
                    break;

                } else {
                    System.out.println("- ERRO: Produto está indisponivel no momento.");
                    return;
                }
            }
        }

        if (!encontrado) {
            System.out.println("- ERRO: Produto não encontrado.");
        }

    }


}
