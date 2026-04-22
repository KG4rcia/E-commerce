import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Gerenciador {
    private String nomeLoja;

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();

    public Gerenciador(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public boolean verificarCPF(String cpf) {
        for (Usuario cliente : usuarios) {
            if (cliente.getCpf().equals(cpf)) {
                return true;
            }
        }

        return false;
    }

    public void fazerPedido(Scanner scanner) {
        if (usuarios.isEmpty()) {
            System.out.println("- ERRO: Não é possível realizar uma venda se não há clientes cadastrados.");
            return;
        }

        int numProdutoVendido = 0;
        String comprador = "";
        int quantidadeProduto;
        boolean encontrado = false;

        while (true) {
            try {
                listarProdutos();

                System.out.print("\nINFORME O PRODUTO A SER VENDIDO: ");
                numProdutoVendido = scanner.nextInt();

                if ((numProdutoVendido-1) > produtos.size()) {
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

        for (Produto produto : produtos) {
            if (produto.equals(produtos.get(numProdutoVendido-1))) {
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
                            listarClientes();

                            System.out.print("INFORME O COMPRADOR (CPF): ");
                            comprador = scanner.nextLine().trim();

                            Cliente clienteComprador = procurarCliente(comprador);

                            produto.setQuantidade(produto.getQuantidade() - quantidadeProduto);
                            Pedido pedido = new Pedido(clienteComprador, produto.getNome(), produto.getDescricao(), produto.getQuantidade(), produto.getPrecoUnitario(), produto.isGrandePorte());

                            pedidos.add(pedido);
                            clienteComprador.fazerPedido(pedido);
                            System.out.println(" - VENDA REALIZADA COM SUCESSO - ");

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.printf("- ERRO: O cliente com o CPF %s não existe. Por favor, passar CPF válido.\n", comprador);
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

    public Cliente procurarCliente(String cpf) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCpf().equals(cpf) && usuarios.get(i) instanceof Cliente) {
                return ((Cliente) usuarios.get(i));
            }
        }

        throw new IllegalArgumentException();
    }

    public void listarProdutos() {
        System.out.println("-".repeat(20));
        if (produtos.isEmpty()) {
            System.out.println("- ERRO: Não há produtos disponiveis no momento.");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i+1) + " | NOME: " + produtos.get(i).getNome() +
                    " | QUANTIDADE: " + produtos.get(i).getQuantidade() +
                    " | PREÇO UNITÁRIO: R$ " + produtos.get(i).getPrecoUnitario() + "0" +
                    " | GRANDE PORTE: " + (produtos.get(i).isGrandePorte() ? "SIM" : "NÃO") +
                    " | NOME VENDEDOR: " + produtos.get(i).getVendedor().getNome().toUpperCase() +
                    " | CPF VENDEDOR: " + produtos.get(i).getVendedor().getCep());
        }

    }

    public void procurarProduto(Scanner scanner) {
        System.out.println("-".repeat(20));

        if (produtos.isEmpty()) {
            System.out.println("- ERRO: Não há produtos disponiveis no momento.");
            return;
        }

        String produtoBuscado;

        while (true) {
            try {
                System.out.print("INFORME O NOME (OU UMA PARTE DO NOME) DO PRODUTO: ");
                produtoBuscado = scanner.nextLine();
                if (produtoBuscado.isEmpty()) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente.");
                scanner.nextLine();
            }
        }

        boolean encontrado = false;
        for (Produto produto : produtos) {
            if (produto.getNome().toUpperCase().trim().contains(produtoBuscado.trim().toUpperCase())) {
                System.out.println("NOME: " + produto.getNome().toUpperCase());
                System.out.println("DESCRIÇÃO: " + produto.getDescricao().toUpperCase());
                System.out.println("PREÇO UNITÁRIO: R$ " + produto.getPrecoUnitario() + "0");
                System.out.println("QUANTIDADE: " + produto.getQuantidade());
                System.out.println("AINDA DISPONÍVEL?: " + (produto.verificarDisponibilidade() ? "SIM" : "NÃO"));
                encontrado = true;
                System.out.print("\n");
            }
        }

        if (!encontrado) {
            System.out.println(" - PRODUTO NÃO EXISTE NO ESTOQUE - ");
        }

    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void removerUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    public void listarUsuarios() {
        System.out.println("-".repeat(20));
        if (usuarios.isEmpty()) {
            throw new IllegalStateException("- ERRO: A Lista de usuários está vazia.");
        }

        System.out.println("- USUÁRIOS CADASTRADOS: ");

        for (Usuario usuario : usuarios) {
            System.out.println("NOME: " + usuario.getNome().toUpperCase());
            System.out.println("IDADE: " + usuario.getIdade());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("TIPO: " + usuario.getClass());
            System.out.println();
        }
        System.out.println("-".repeat(20));
    }

    public void listarClientes() {
        if (usuarios.isEmpty()) {
            throw new IllegalStateException();
        }

        boolean temClienteCadastrado = false;

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i) instanceof Cliente) {

                if (!temClienteCadastrado) {
                    System.out.println(" - CLIENTES: ");
                    temClienteCadastrado = true;
                }

                System.out.println("NOME: " + usuarios.get(i).getNome().toUpperCase());
                System.out.println("IDADE: " + usuarios.get(i).getIdade());
                System.out.println("CPF: " + usuarios.get(i).getCpf().toUpperCase());
            }

        }


        if (!temClienteCadastrado) {
            System.out.println(" - NÃO HÁ CLIENTES CADASTRADOS.");
        }

    }

    public Administrador procurarAdministradorPorCPF(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf) && usuario instanceof Administrador) {
                return ((Administrador) usuario);
            }
        }

        throw new IllegalArgumentException("Não existe um Administrador com esse CPF.");
    }

    public Vendedor procurarVendedorPorCPDF(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf) && usuario instanceof Vendedor) {
                return ((Vendedor) usuario);
            }
        }

        throw new IllegalArgumentException("Não existe um Vendedor com esse CPF.");
    }

    public void setNomeLoja(String nomeLoja) {
        if (nomeLoja.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.nomeLoja = nomeLoja;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}