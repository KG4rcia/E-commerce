import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Gerenciador gerenciador = new Gerenciador("PontaVenda");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Vendedor vendedor1 = new Vendedor("Rogerio", 30, "98765421", "Rua dos Vendedores", "12345");
        Vendedor vendedor2 = new Vendedor("Marcos", 46, "3336", "Rua dos Vendedores", "1214");
        Cliente cliente1 = new Cliente("Rodrigo", 19, "12345", "Rua das Flores", "0987");
        Administrador administrador1 = new Administrador("Kauan", 20, "530", "Rua das Casas", "12345");

        gerenciador.adicionarUsuario(vendedor1);
        gerenciador.adicionarUsuario(vendedor2);
        gerenciador.adicionarUsuario(cliente1);
        gerenciador.adicionarUsuario(administrador1);

        Produto produtoTeste = new Produto("CELULAR", "APARELHO DA MARCA IPHONE", 3500, 10, false, vendedor1);
        Produto produtoTeste2 = new Produto("GARRAFA", "GARRAFA TERMICA AZUL", 90, 0, false, vendedor1);
        Produto produtoTeste3 = new Produto("GELADEIRA", "GELADEIRA DUAS PORTAS", 2500, 4, true, vendedor2);

        gerenciador.adicionarProduto(produtoTeste);
        gerenciador.adicionarProduto(produtoTeste2);
        gerenciador.adicionarProduto(produtoTeste3);

        System.out.println(" - BEM VINDO(A) AO SISTEMA DA PONTAVENDA - ");
        int escolhaUsuario;

        do {
            escolhaUsuario = exibirMenu();
            processarDado(escolhaUsuario);
        } while (escolhaUsuario != 7);

    }

    public static int exibirMenu() {
        int escolha;

        System.out.println("-".repeat(20));
        System.out.println("| 1. CADASTRAR PRODUTO");
        System.out.println("| 2. LISTAR PRODUTOS");
        System.out.println("| 3. VENDER PRODUTO");
        System.out.println("| 4. PROCURAR PRODUTO");
        System.out.println("| 5. CADASTRAR USUÁRIO");
        System.out.println("| 6. GERENCIAR USUÁRIOS");
        System.out.println("| 7. ENCERRAR");
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
                cadastrarProduto();
                break;
            case 2:
                gerenciador.listarProdutos();
                break;
            case 3:
                gerenciador.fazerPedido(scanner);
                break;
            case 4:
                gerenciador.procurarProduto(scanner);
                break;
            case 5:
                cadastrarUsuario();
                break;
            case 6:
                gerenciarCliente();
                break;
            case 7:
                System.out.println(" - ENCERRANDO - ");
                System.out.println("-".repeat(20));
                return;
            default:
                System.out.println(" - ESCOLHA INVÁLIDA - ");
                break;
        }
    }

    public static String validarTexto(String texto) {
        if (texto.isEmpty()) {
            return null;
        }

        return texto.toUpperCase().trim();
    }

    public static void cadastrarProduto() {
        String usuarioAcessando;
        boolean acesso = false;
        Vendedor vendedor;
        String cpfVendedor = "";

        while (true) {
            try {
                gerenciador.listarUsuarios();
                System.out.print(" - QUEM ESTÁ ACESSANDO? (DIGITE O CPF DO USUÁRIO / DIGITE \"000\" PARA SAIR): ");
                usuarioAcessando = scanner.nextLine();

                if (usuarioAcessando.equals("000")) {
                    break;
                } else if (usuarioAcessando.isEmpty()) {
                    throw new IllegalArgumentException("Preencha o campo corretamente.");
                }

                vendedor = gerenciador.procurarVendedorPorCPDF(usuarioAcessando);
                cpfVendedor = vendedor.getCpf();
                acesso = true;

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }

        if (!acesso) {
            System.out.println(" - RETORNANDO - ");
            return;
        }

        String nomeProdutoValida;
        String descricaoProdutoValido;
        boolean verificarGrandePorte = false;
        double precoUnitario;
        int quantidadeEstoque;

        while (true) {
            System.out.print("INFORME O NOME DO PRODUTO: ");
            String nomeProduto = scanner.nextLine();

            try {
                nomeProdutoValida = validarTexto(nomeProduto);
                if (nomeProdutoValida == null) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: O nome do produto não pode estar vazio.");
            }

        }

        while (true) {
            System.out.print("INFORME A DESCRIÇÃO DO PRODUTO: ");
            String descricaoProduto = scanner.nextLine();

            try {
                descricaoProdutoValido = validarTexto(descricaoProduto);
                if (descricaoProdutoValido == null) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: A descrição do produto não pode estar vazio.");
            }

        }

        while (true) {
            try {
                System.out.print("INFORME O PREÇO UNITÁRIO DO PRODUTO: ");
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
                System.out.print("INFORME A QUANTIDADE EM ESTOQUE: ");
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
                System.out.print("É UM PRODUTO DE GRANDE PORTE? [SIM/NÃO]: ");
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

            vendedor = gerenciador.procurarVendedorPorCPDF(usuarioAcessando);

            Produto produto = new Produto(nomeProdutoValida, descricaoProdutoValido, precoUnitario, quantidadeEstoque, verificarGrandePorte, vendedor);
            gerenciador.adicionarProduto(produto);
            System.out.println(" - PRODUTO CRIADO - ");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    public static void cadastrarUsuario() {
        System.out.println("-".repeat(20));
        String clienteNome;
        int clienteIdade;
        String clienteCPF;
        String clienteCEP;
        String clienteEndereco;

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

        while (true) {
            try {
                System.out.print("INFORME O CPF: ");
                clienteCPF = scanner.nextLine();

                if (clienteCPF.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (!clienteCPF.matches("\\d+")) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O CPF do cliente não pode estar vazio e não pode conter letras ou espaços.");
            }

        }

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

        while (true) {
            try {
                System.out.print("INFORME O ENDEREÇO DO CLIENTE: ");
                clienteEndereco = scanner.nextLine();

                if (clienteEndereco.isEmpty()) {
                    throw new InputMismatchException();
                } else if (clienteEndereco.matches("\\d+")) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: O endereço do cliente não deve estar vazio.");
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O endereço do cliente não deve conter somente números.");
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
                        Cliente cliente = new Cliente(clienteNome, clienteIdade, clienteCPF, clienteEndereco, clienteCEP);
                        gerenciador.adicionarUsuario(cliente);
                        break;
                    case 2:
                        Administrador administrador = new Administrador(clienteNome, clienteIdade, clienteCPF, clienteEndereco, clienteCEP);
                        gerenciador.adicionarUsuario(administrador);
                        break;
                    case 3:
                        Vendedor vendedor = new Vendedor(clienteNome, clienteIdade, clienteCPF, clienteEndereco, clienteCEP);
                        gerenciador.adicionarUsuario(vendedor);
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

    public static void gerenciarCliente() {
        // 0. Permitir somente que ADMINISTRADORES acessem essa parte.
        String usuarioAcessando;
        gerenciador.listarUsuarios();
        Administrador administrador;
        boolean acesso = false;

        while (true) {
            try {
                System.out.print(" - QUEM ESTÁ ACESSANDO? (DIGITE O CPF DO USUÁRIO / DIGITE \"000\" PARA SAIR): ");
                usuarioAcessando = scanner.nextLine();

                if (usuarioAcessando.equals("000")) {
                    break;
                } else if (usuarioAcessando.isEmpty()) {
                    throw new IllegalArgumentException("Preencha o campo corretamente.");
                }

                administrador = gerenciador.procurarAdministradorPorCPF(usuarioAcessando);
                acesso = true;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }

        if (!acesso) {
            System.out.println(" - RETORNANDO - ");
            return;
        }

        System.out.println(" - ACESSO LIBERADO ");
        menuAdministrador();

        // 1. Listar Clientes
        // 2. Procurar Cliente
        // 3. Editar Cliente

        // 4. Encerrar Pedido
        //    1. Criar Objeto endereço que vai calcular taxa pelo estado.
        //    2. Verificar se pedido possui flag de grande porte.
        //    3. Atualizar o preço com as duas taxas.
        //    4. Mudar para finalizado.

        // 5. Apagar Cliente
    }

    public static void menuAdministrador() {
        System.out.println(" - MENU DE ADMINISTRADOR: ");

        System.out.println("1. LISTAR TODOS OS USUÁRIOS");
        System.out.println("2. LISTAR TODOS OS PEDIDOS");
        System.out.println("3. PROCURAR USUÁRIO POR CPF");
        System.out.println("4. EDITAR USUÁRIO");
        System.out.println("5. EDITAR PRODUTO");
        System.out.println("6. REMOVER USUÁRIO");
        System.out.println("7. REMOVER PRODUTO");
        System.out.println("8. MUDAR NOME DA LOJA");
        System.out.println("9. RETORNAR AO MENU PADRÃO");
    }

    public static void processarDadosAdministrador(int escolhaAdministrador) {
        switch (escolhaAdministrador) {
            case 1:
                gerenciador.listarUsuarios();
                break;
            case 2:
                gerenciador.listarProdutos();
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
        }

    }


}
