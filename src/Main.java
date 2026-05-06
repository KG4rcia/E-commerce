import model.*;
import util.Gerenciador;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Gerenciador gerenciador = new Gerenciador("PONTAVENDA");
    static Scanner scanner = new Scanner(System.in);

    public static void teste() {

        System.out.println("\n === RODANDO COM TESTE === \n");

        Endereco e1 = new Endereco("São Paulo", "Mogi das Cruzes", "Vila Mogilar", "12345", 123);
        Endereco e2 = new Endereco("Minas Gerais", "Belo Horizonte", "", "1441", 234);
        Endereco e3 = new Endereco("Rio de Janeiro", "São Gonçalo", "", "8416", 120);
        Endereco e4 = new Endereco("Bahia", "Salvador", "", "7311", 44);

        Vendedor vendedor1 = new Vendedor("Rogerio", 30, "98765421", e1);
        Vendedor vendedor2 = new Vendedor("Marcos", 46, "3336", e2);
        Cliente cliente1 = new Cliente("Rodrigo", 19, "12345", e3);
        Cliente cliente2 = new Cliente("Murilo", 19, "2468", e1);
        Cliente cliente3 = new Cliente("Jorge", 19, "36912", e4);

        Administrador administrador1 = new Administrador("Kauan", 20, "530", e4);

        gerenciador.adicionarUsuario(vendedor1);
        gerenciador.adicionarUsuario(vendedor2);
        gerenciador.adicionarUsuario(cliente1);
        gerenciador.adicionarUsuario(cliente2);
        gerenciador.adicionarUsuario(cliente3);
        gerenciador.adicionarUsuario(administrador1);

        Produto produtoTeste = new Produto("CELULAR", "APARELHO DA MARCA IPHONE", 3500, 10, false, vendedor1);
        Produto produtoTeste2 = new Produto("GARRAFA", "GARRAFA TERMICA AZUL", 90, 0, false, vendedor1);
        Produto produtoTeste3 = new Produto("GELADEIRA", "GELADEIRA DUAS PORTAS", 2500, 4, true, vendedor2);

        gerenciador.adicionarProduto(produtoTeste);
        gerenciador.adicionarProduto(produtoTeste2);
        gerenciador.adicionarProduto(produtoTeste3);
    }

    public static void main(String[] args) {
//        teste();

        int escolhaUsuario;

        do {
            escolhaUsuario = exibirMenu();
            processarDado(escolhaUsuario);
        } while (escolhaUsuario != 7);

    }

    public static int exibirMenu() {
        int escolha;
        System.out.println("- BEM VINDO(A) AO SISTEMA DA " + gerenciador.getNomeLoja() + " - ");
        System.out.println("| 1. CADASTRAR PRODUTO");
        System.out.println("| 2. LISTAR PRODUTOS");
        System.out.println("| 3. FAZER PEDIDO");
        System.out.println("| 4. PROCURAR PRODUTO");
        System.out.println("| 5. CADASTRAR USUÁRIO");
        System.out.println("| 6. GERENCIAR USUÁRIOS");
        System.out.println("| 7. GERENCIAR SEU PEDIDO");
        System.out.println("| 8. ENCERRAR");
        System.out.println("-".repeat(20));

        while (true) {
            System.out.print("-> SUA ESCOLHA: ");

            try {
                escolha = scanner.nextInt();
                scanner.nextLine();
                return escolha;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Digite apenas números.");
                scanner.nextLine();
            }

        }

    }

    public static void processarDado(int escolhaUsuario) {
        switch (escolhaUsuario) {
            case 1:
                while (true) {
                    try {
                        gerenciador.listarVendedores();

                        System.out.print("- INFORME O CPF DO VENDEDOR: ");
                        String vendedorCPF = scanner.nextLine();

                        Vendedor vendedor = gerenciador.procurarVendedorPorCPF(vendedorCPF);

                        vendedor.cadastrarProduto(scanner, gerenciador);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("- ERRO: " + e.getMessage());
                    }
                }
                break;
            case 2:
                gerenciador.listarProdutos();
                break;
            case 3:
                while (true) {
                    try {
                        gerenciador.listarClientes();

                        System.out.print("- INFORME O CPF DO CLIENTE: ");
                        String clienteCPF = scanner.nextLine();

                        Cliente cliente = gerenciador.procurarCliente(clienteCPF);

                        cliente.fazerPedido(scanner, gerenciador);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("- ERRO: " + e.getMessage());
                    } catch (IllegalStateException e) {
                        System.out.println("- ERRO: " + e.getMessage());
                        return;
                    }
                }
                break;
            case 4:
                gerenciador.procurarProduto(scanner);
                break;
            case 5:
                cadastrarUsuario();
                break;
            case 6:
                gerenciadorAdministrador();
                break;
            case 7:
                gerenciador.gerenciandoStatusPedido(scanner);
                break;
            case 8:
                System.out.println(" - ENCERRANDO - ");
                System.out.println("-".repeat(20));
                return;
            default:
                System.out.println(" - ESCOLHA INVÁLIDA - ");
                break;
        }
    }

    public static void cadastrarUsuario() {
        System.out.println("-".repeat(20));
        String clienteNome;
        int clienteIdade;
        String clienteCPF;

        String clienteCEP =  "";
        String clienteEstado = "";
        String clienteCidade = "";
        String clienteBairro = "";
        int clienteNumCasa = 0;

        // Nome Cliente
        while (true) {
            try {
                System.out.print("INFORME O NOME DO CLIENTE: ");
                clienteNome = scanner.nextLine();

                if (clienteNome.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O nome do cliente não pode estar vazio.");
            }
        }

        // Idade do Cliente
        while (true) {
            try {
                System.out.print("INFORME A IDADE DO CLIENTE: ");
                clienteIdade = scanner.nextInt();

                if (clienteIdade < 15) {
                    throw new IllegalArgumentException();
                }

                scanner.nextLine();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: A idade do cliente deve ser no mínimo de 15.");
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo IDADE corretamente. Somente números.");
                scanner.nextLine();
            }
        }

        // CPF Cliente
        while (true) {
            try {
                System.out.print("INFORME O CPF: ");
                clienteCPF = scanner.nextLine();

                if (clienteCPF.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O CPF do cliente não pode estar vazio e não pode conter letras ou espaços.");
            }

        }

        // Cep Cliente
        while (true) {
            try {
                System.out.print("INFORME O CEP DO CLIENTE: ");
                clienteCEP = scanner.nextLine();

                if (clienteCEP.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (!clienteCEP.matches("\\d+")) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O CEP do cliente não pode estar vazio e não deve conter letras.");
            }

        }

        // Estado Cliente
        while (true) {
            try {
                System.out.print("INFORME O ESTADO DO CLIENTE: ");
                clienteEstado = scanner.nextLine();

                if (clienteEstado.isEmpty()) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. Ele não deve estar vazio.");
            }

        }

        // Cidade Cliente
        while (true) {
            try {
                System.out.print("INFORME A CIDADE DO CLIENTE: ");
                clienteEstado = scanner.nextLine();

                if (clienteEstado.isEmpty()) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. Ele não deve estar vazio.");
            }

        }

        // Bairro Cliente
        while (true) {
            try {
                System.out.print("INFORME O BAIRRO DO CLIENTE: ");
                clienteEstado = scanner.nextLine();

                if (clienteEstado.isEmpty()) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. Ele não deve estar vazio.");
            }

        }

        // Número da casa Cliente
        while (true) {
            try {
                System.out.print("INFORME O NÚMERO DA CASA DO CLIENTE: ");
                clienteNumCasa = scanner.nextInt();

                if (clienteNumCasa < 0) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. O número não deve ser menor ou igual a zero.");
            }

        }

        while (true) {
            try {
                System.out.println("\n- SELECIONE UMA DAS OPÇÕES: ");
                System.out.println("1. CLIENTE: Como \"CLIENTE\", é possivel ver a lista de produtos e realizar compras.");
                System.out.println("2. VENDEDOR: Como \"VENDEDOR\", é possivel ver a lista de produtos e cadastrar novos produtos.");
                System.out.println("3. ADMINISTRADOR: Como \"ADMINISTRADOR\", é possivel ver a lista de produtos e gerenciar os usuários.");
                System.out.print("SUA ESCOLHA: ");
                int opcaoUsuario = scanner.nextInt();

                switch (opcaoUsuario) {
                    case 1:
                        Endereco enderecoCliente = new Endereco(clienteEstado, clienteCidade, clienteBairro, clienteCEP, clienteNumCasa);
                        Cliente cliente = new Cliente(clienteNome, clienteIdade, clienteCPF, enderecoCliente);
                        gerenciador.adicionarUsuario(cliente);

                        break;
                    case 2:
                        Endereco enderecoVendedor = new Endereco(clienteEstado, clienteCidade, clienteBairro, clienteCEP, clienteNumCasa);
                        Vendedor vendedor = new Vendedor(clienteNome, clienteIdade, clienteCPF, enderecoVendedor);
                        gerenciador.adicionarUsuario(vendedor);

                        break;
                    case 3:
                        Endereco enderecoAdministrador = new Endereco(clienteEstado, clienteCidade, clienteBairro, clienteCEP, clienteNumCasa);
                        Administrador administrador = new Administrador(clienteNome, clienteIdade, clienteCPF, enderecoAdministrador);
                        gerenciador.adicionarUsuario(administrador);

                        break;
                    default:
                        throw new IllegalArgumentException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números, nada de letras.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: Escolha uma opção válida (1-3).");
            }

        }

        System.out.println("\n - CADASTRO REALIZADO COM SUCESSO - ");
    }

    // Administrador
    public static void menuAdministrador() {
        System.out.println("=".repeat(10));
        System.out.println("\n - MENU DE ADMINISTRADOR: ");

        System.out.println("1. LISTAR TODOS OS USUÁRIOS");
        System.out.println("2. LISTAR TODOS OS PEDIDOS");
        System.out.println("3. EDITAR USUÁRIO"); // Testar
        System.out.println("4. EDITAR PRODUTO"); // Começar
        System.out.println("5. REMOVER USUÁRIO");
        System.out.println("6. REMOVER PRODUTO");
        System.out.println("7. RETORNAR AO MENU PADRÃO");
    }

    public static void gerenciadorAdministrador() {
        String usuarioAcessando;
        Administrador administrador;

        while (true) {
            try {
                gerenciador.listarUsuarios();
                System.out.print(" - QUEM ESTÁ ACESSANDO? (DIGITE O CPF DO USUÁRIO OU DIGITE \"000\" PARA SAIR): ");
                usuarioAcessando = scanner.nextLine();

                if (usuarioAcessando.equals("000")) {
                    System.out.println(" - RETORNANDO AO MENU PADRÃO -");
                    break;

                } else if (usuarioAcessando.isEmpty()) {
                    throw new IllegalArgumentException("Preencha o campo corretamente.");
                }

                administrador = gerenciador.procurarAdministradorPorCPF(usuarioAcessando);
                System.out.println(" - ACESSO LIBERADO -\n");

                do {
                    menuAdministrador();
                    System.out.print("-> SUA ESCOLHA: ");
                    int opcaoAdministrador = scanner.nextInt();

                    if (opcaoAdministrador == 7) {
                        System.out.println(" - RETORNANDO AO MENU PADRÃO -");
                        break;
                    }

                    scanner.nextLine();
                    processarDadosAdministrador(administrador, opcaoAdministrador);

                } while (true);

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }

    }

    public static void processarDadosAdministrador(Administrador administrador, int escolhaAdministrador) {
        try {
            switch (escolhaAdministrador) {
                case 1:
                    gerenciador.listarUsuarios();
                    break;
                case 2:
                    gerenciador.listarPedidos();
                    break;
                case 3:
                    administrador.editarUsuario(scanner, gerenciador);
                    break;
                case 4:
                    administrador.editarProduto(scanner, gerenciador);
                    break;
                case 5:
                    administrador.removerUsuario(scanner, gerenciador);
                    break;
                case 6:
                    administrador.removerProduto(scanner, gerenciador);
                    break;
                case 7:
                    System.out.println(" - RETORNANDO AO MENU PADRÃO - ");
                    return;
                default:
                    System.out.println(" - POR FAVOR, INSERIR OPÇÃO VÁLIDA - ");
                    break;
            }

        } catch (IllegalStateException e) {
            System.out.println("\n- ERRO: " + e.getMessage());
        }

    }

}
