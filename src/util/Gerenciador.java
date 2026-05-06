package util;
import model.*;
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

    // Gerenciando Pedido e Produto
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            throw new IllegalStateException("Não há pedidos no momento.");
        }

        System.out.println(" === PEDIDOS ===");
        for (Pedido pedido : pedidos) {
            pedido.infoPedido();
        }
        System.out.println("=".repeat(20));

    }

    public void listarPedidos(Usuario usuario) {
        if (pedidos.isEmpty()) {
            throw new IllegalStateException("Não há pedidos no momento.");
        }

        boolean possuiPedido = false;

        System.out.println(" === PEDIDOS === ");
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getCpf().equals(usuario.getCpf())) {
                possuiPedido = true;

                System.out.println("- NOME CLIENTE: " + pedido.getCliente().getNome());
                System.out.println("- CPF CLIENTE: " + pedido.getCliente().getCpf());
                System.out.println("- PRODUTO: " + pedido.getNomeProduto());
                System.out.println("- DESCRIÇÃO PRODUTO: " + pedido.getDescricaoProduto());
                System.out.println("- VALOR PRODUTO: " + pedido.getValorProduto());
                System.out.println("- QUANTIDADE PRODUTO: " + pedido.getQuantidadeProduto());
                System.out.println("- TAXA ENTREGA: " + pedido.getCliente().getEndereco().getTaxaEntrega());
                System.out.println();
            }

        }
        System.out.println("=".repeat(20));

        if (!possuiPedido) {
            System.out.println("- ERRO: Esse usuário não possui pedidos.");
        }

    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("- ERRO: Não há produtos disponiveis no momento.\n");
            return;
        }

        System.out.println(" === PRODUTOS ===");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println(
                    "ID: " + (i+1) +
                            " | NOME: " + produtos.get(i).getNome() +
                            " | QUANTIDADE: " + produtos.get(i).getQuantidade() +
                            " | PREÇO UNITÁRIO: R$ " + produtos.get(i).getPrecoUnitario() + "0" +
                            " | GRANDE PORTE: " + (produtos.get(i).isGrandePorte() ? "SIM" : "NÃO") +
                            " | NOME VENDEDOR: " + produtos.get(i).getVendedor().getNome().toUpperCase() +
                            " | CPF VENDEDOR: " + produtos.get(i).getVendedor().getEndereco().getCep());
        }
        System.out.println("=".repeat(20));
    }

    public void gerenciandoStatusPedido(Scanner scanner) {
        if (pedidos.isEmpty()) {
            System.out.println("Não há pedidos disponiveis no momento.\n");
            return;
        }

        Usuario usuario;

        do {
            try {
                System.out.print("INFORME O SEU CPF (\"000\" para retornar): ");
                String cpf = scanner.nextLine();

                if (cpf.equals("000")) {
                    System.out.println(" - RETORNANDO -");
                    break;
                }

                usuario = procurarUsuario(cpf);
                listarPedidos(usuario);
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        } while (true);


        do {
            try {
                System.out.println(" - STATUS - ");
                System.out.println("1. PENDENTE");
                System.out.println("2. FINALIZADO");
                System.out.println("3. CANCELADO");
                System.out.print("INFORME O STATUS ATUAL: ");
                int statusAtual = scanner.nextInt();
                scanner.nextLine();

                if (statusAtual > 3 || statusAtual < 1) {
                    throw new IllegalArgumentException("Por favor, selecione uma opção válida (1-3)");
                }

                // Continuar daqui

            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números.");
                scanner.nextLine();
            }
        } while (true);

    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    // Procurando Cliente, Produto e etc.
    public Cliente procurarCliente(String cpf) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCpf().equals(cpf) && usuarios.get(i) instanceof Cliente) {
                return ((Cliente) usuarios.get(i));
            }
        }

        throw new IllegalArgumentException();
    }

    public Vendedor procurarVendedorPorCPF(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf) && usuario instanceof Vendedor) {
                return ((Vendedor) usuario);
            }
        }

        throw new IllegalArgumentException("Não existe um model.Vendedor com esse CPF.");
    }

    public void procurarProduto(Scanner scanner) {
        System.out.println("-".repeat(20));

        if (produtos.isEmpty()) {
            System.out.println("- ERRO: Não há produtos disponiveis no momento.\n");
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

    public Administrador procurarAdministradorPorCPF(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf) && usuario instanceof Administrador) {
                return ((Administrador) usuario);
            }
        }

        throw new IllegalArgumentException("Não existe um Administrador com esse CPF.");
    }

    public Usuario procurarUsuario(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }

        throw new IllegalArgumentException("Não há nenhum usuário com esse CPF. Informe um CPF válido.");
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            throw new IllegalStateException("- ERRO: A Lista de usuários está vazia. É necessário ao menos 1 para essa operação.");
        }

        System.out.println("\n- USUÁRIOS CADASTRADOS: ");

        for (Usuario usuario : usuarios) {
            System.out.println("NOME: " + usuario.getNome().toUpperCase());
            System.out.println("IDADE: " + usuario.getIdade());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("TIPO: " + usuario.getClass());
            System.out.println();
        }
        System.out.println("=".repeat(20));
    }

    public void listarClientes() {
        if (usuarios.isEmpty()) {
            throw new IllegalStateException("Nenhum cliente cadastrado. É necessário ter ao menos 1 para essa operação.\n");
        }

        boolean temClienteCadastrado = false;

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i) instanceof Cliente) {

                if (!temClienteCadastrado) {
                    System.out.println(" === CLIENTES === ");
                    temClienteCadastrado = true;
                }

                System.out.println("NOME: " + usuarios.get(i).getNome().toUpperCase());
                System.out.println("IDADE: " + usuarios.get(i).getIdade());
                System.out.println("CPF: " + usuarios.get(i).getCpf().toUpperCase());
                System.out.println();
            }

        }
        System.out.println("=".repeat(20));


        if (!temClienteCadastrado) {
            System.out.println(" - NÃO HÁ CLIENTES CADASTRADOS.");
        }

    }

    public void listarVendedores() {
        if (usuarios.isEmpty()) {
            throw new IllegalStateException();
        }

        boolean temVendedosCadastrado = false;

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i) instanceof Vendedor) {

                if (!temVendedosCadastrado) {
                    System.out.println(" === VENDEDORES === ");
                    temVendedosCadastrado = true;
                }

                System.out.println("NOME: " + usuarios.get(i).getNome().toUpperCase());
                System.out.println("IDADE: " + usuarios.get(i).getIdade());
                System.out.println("CPF: " + usuarios.get(i).getCpf().toUpperCase());
                System.out.println();
            }
        }
        System.out.println("=".repeat(20));

        if (!temVendedosCadastrado) {
            System.out.println("- ERRO: Não há vendedores no momento.");
        }

    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Getters
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


    // Setters
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setNomeLoja(String nomeLoja) {
        if (nomeLoja.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.nomeLoja = nomeLoja;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}