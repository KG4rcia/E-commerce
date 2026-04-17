import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Gerenciador gerenciador = new Gerenciador("PontaVenda");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Produto produtoTeste = new Produto("CELULAR", "APARELHO DA MARCA IPHONE", 3500, 10, false);
        Produto produtoTeste2 = new Produto("GARRAFA", "GARRAFA TERMICA AZUL", 90, 0, false);
        Produto produtoTeste3 = new Produto("GELADEIRA", "GELADEIRA DUAS PORTAS", 2500, 4, true);
        gerenciador.adicionarProduto(produtoTeste);
        gerenciador.adicionarProduto(produtoTeste2);

        Cliente cliente1 = new Cliente("Rodrigo", 19, "12345", "Rua das Flores", "0987");
        gerenciador.adicionarCliente(cliente1);

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
        System.out.println("| 5. CADASTRAR CLIENTE");
        System.out.println("| 6. GERENCIAR CLIENTE");
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
                cadastrarCliente();
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
        System.out.println("-".repeat(20));
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
            System.out.print("INFORME O PREÇO UNITÁRIO DO PRODUTO: ");
            precoUnitario = scanner.nextDouble();
            scanner.nextLine();

            try {
                if (precoUnitario <= 0) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: O valor do produto não pode ser menor ou igual a zero.");
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números, nada de texto.");
            }

        }

        while (true) {
            System.out.print("INFORME A QUANTIDADE EM ESTOQUE: ");
            quantidadeEstoque = scanner.nextInt();
            scanner.nextLine();

            try {
                if (quantidadeEstoque <= 0) {
                    throw new IllegalArgumentException();
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: A quantidade do produto não pode ser menor ou igual a zero.");
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números, nada de texto.");
            }

        }

        while (true) {
            System.out.print("É UM PRODUTO DE GRANDE PORTE? [SIM/NÃO]: ");
            String grandePorte = scanner.nextLine().trim().toUpperCase();

            try {
                if (grandePorte.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (!grandePorte.equals("SIM") || !grandePorte.equals("NÃO")) {
                    throw new IllegalArgumentException();
                }

                if (grandePorte.equals("SIM")) {
                    verificarGrandePorte = true;
                } else if (grandePorte.equals("NÃO")) {
                    verificarGrandePorte = false;
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: Preencha o campo corretamente, é somente \"SIM\" ou \"NÃO\". Números e outras palavras não são válidos.");
            }

        }

        Produto produto = new Produto(nomeProdutoValida, descricaoProdutoValido, precoUnitario, quantidadeEstoque, verificarGrandePorte);
        gerenciador.adicionarProduto(produto);
        System.out.println(" - PRODUTO CRIADO - ");
    }

    public static void cadastrarCliente() {
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

        Cliente cliente = new Cliente(clienteNome, clienteIdade, clienteCPF, clienteEndereco, clienteCEP);
        System.out.println("\n - CADASTRO REALIZADO COM SUCESSO - ");
        gerenciador.adicionarCliente(cliente);
    }

    public static void gerenciarCliente() {

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


}
