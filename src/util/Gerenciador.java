package util;
import dao.*;
import model.*;
import service.PedidoService;
import service.UsuarioService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Gerenciador implements UsuarioService, PedidoService {
    private String nomeLoja;
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();

    public Gerenciador(String nomeLoja) {
        this.nomeLoja = nomeLoja.toUpperCase();
    }

    public void carregarDadosDoBanco() {
        this.usuarios.addAll(new ClienteDAO().listarTodos());
        this.usuarios.addAll(new AdministradorDAO().listarTodos());
        this.usuarios.addAll(new VendedorDAO().listarTodos());
        this.produtos.addAll(new ProdutoDAO().listarTodos());
        this.pedidos.addAll(new PedidoDAO().listarTodos());
        System.out.println(" - DADOS DO BANCO CARREGADOS - ");
    }

    public boolean cpfJaExiste(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    // Gerenciando Pedido e Produto
    public void adicionarProduto(Produto produto) {
        new ProdutoDAO().salvar(produto);
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

    }

    @Override
    public boolean listarPedidos(Usuario usuario) {
        if (pedidos.isEmpty()) {
            throw new IllegalStateException("Não há pedidos no momento.");
        }

        boolean possuiPedido = false;

        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getCpf().equals(usuario.getCpf())) {
                if (!possuiPedido) {
                    System.out.println(" === PEDIDOS === ");
                }
                possuiPedido = true;

                System.out.println("- STATUS DESSE PEDIDO: " + pedido.getStatus());
                System.out.println();
                System.out.println("- ID PEDIDO: " + pedido.getId());
                System.out.println("- NOME CLIENTE: " + pedido.getCliente().getNome());
                System.out.println("- CPF CLIENTE: " + pedido.getCliente().getCpf());
                System.out.println("- PRODUTO: " + pedido.getNomeProduto());
                System.out.println("- DESCRIÇÃO PRODUTO: " + pedido.getDescricaoProduto());
                System.out.println("- VALOR PRODUTO: " + pedido.getValorProduto());
                System.out.println("- QUANTIDADE PRODUTO: " + pedido.getQuantidadeProduto());
                System.out.println("- TAXA ENTREGA: " + pedido.getCliente().getEndereco().getTaxaEntrega());
                System.out.println("- VALOR TOTAL: " + (pedido.getValorProduto() * pedido.getQuantidadeProduto()) + pedido.getTaxaEntrega());
                System.out.println();
            }

        }
        System.out.println("=".repeat(20));

        if (!possuiPedido) {
            System.out.println("- ERRO: Esse usuário não possui pedidos.\n");
            return false;
        } else {
            return true;
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
                            " | CPF VENDEDOR: " + produtos.get(i).getVendedor().getCpf());
        }
        System.out.println("=".repeat(20));
    }

    @Override
    public void gerenciandoStatusPedido(Scanner scanner) {
        if (pedidos.isEmpty()) {
            System.out.println("- ERRO: Não há pedidos disponíveis no momento.\n");
            return;
        }

        Usuario usuario = null;
        Pedido pedidoSelecionado = null;

        while (true) {
            try {
                listarClientes();
                System.out.print("INFORME O SEU CPF (\"000\" para retornar): ");
                String cpf = scanner.nextLine();

                if (cpf.equals("000")) return;

                usuario = procurarUsuario(cpf);

                if (listarPedidos(usuario)) {
                    break;
                } else {
                    return;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("INFORME O ID DO PEDIDO QUE DESEJA ALTERAR: ");
                int idBusca = scanner.nextInt();
                scanner.nextLine();

                for (Pedido p : pedidos) {
                    if (p.getId() == idBusca) {
                        pedidoSelecionado = p;
                        break;
                    }
                }

                if (pedidoSelecionado == null) {
                    System.out.println("- ERRO: Pedido não encontrado.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Digite um número válido.");
                scanner.nextLine();
            }
        }

        String novoStatusLabel = "";
        while (true) {
            try {
                System.out.println("\n - DEFINIR NOVO STATUS - ");
                System.out.println("1. PENDENTE");
                System.out.println("2. FINALIZADO");
                System.out.println("3. CANCELADO");
                System.out.print("SUA ESCOLHA: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1: novoStatusLabel = "PENDENTE"; break;
                    case 2: novoStatusLabel = "FINALIZADO"; break;
                    case 3: novoStatusLabel = "CANCELADO"; break;
                    default: throw new IllegalArgumentException("Opção inválida (1-3).");
                }
                break;
            } catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println("- ERRO: " + e.getMessage());
                scanner.nextLine();
            }
        }

        try {
            pedidoSelecionado.setStatus(novoStatusLabel);

            new PedidoDAO().atualizarStatus(pedidoSelecionado.getId(), novoStatusLabel);

            System.out.println(" - STATUS ATUALIZADO COM SUCESSO! - ");
        } catch (RuntimeException e) {
            System.out.println("- ERRO AO SALVAR NO BANCO: " + e.getMessage());
        }
    }

    @Override
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

    @Override
    public Usuario procurarUsuario(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }

        throw new IllegalArgumentException("Não há nenhum usuário com esse CPF. Informe um CPF válido.");
    }

    @Override
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            throw new IllegalStateException("A Lista de usuários está vazia. É necessário ao menos 1 para essa operação.");
        }

        System.out.println("\n- USUÁRIOS CADASTRADOS: ");

        for (Usuario usuario : usuarios) {
            System.out.println("NOME: " + usuario.getNome().toUpperCase());
            System.out.println("IDADE: " + usuario.getIdade());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("TIPO: " + usuario.getClass());
            System.out.println();
        }
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
            System.out.println("- ERRO: Não há clientes cadastrados.");
        }

    }

    public void listarVendedores() {
        if (usuarios.isEmpty()) {
            throw new IllegalStateException("Não há vendedores no momento. Crie um vendedor para cadastrar um Produto.\n");
        }

        boolean existeVendedor = false;

        for (Usuario usuario : usuarios) {
            if (usuario instanceof Vendedor) {
                existeVendedor = true;
                break;
            }
        }

        if (existeVendedor) {
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
        } else {
            throw new IllegalStateException("Não há vendedores no momento. Crie um vendedor para cadastrar um Produto.");
        }


    }

    public void adicionarUsuario(Usuario usuario) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        enderecoDAO.salvar(usuario.getEndereco());

        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.salvar(cliente);
        } else if (usuario instanceof Vendedor) {
            new VendedorDAO().salvar((Vendedor) usuario);
        } else if (usuario instanceof Administrador) {
            new AdministradorDAO().salvar((Administrador) usuario);
        }

        this.usuarios.add(usuario);
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