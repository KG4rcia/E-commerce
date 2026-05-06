package model;

import util.Gerenciador;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Administrador extends Usuario {

    public Administrador(String nome, int idade, String cpf, Endereco endereco) {
        super(nome, idade, cpf, endereco);
    }

    @Override
    public void info() {
        System.out.println("- ADMINISTRADOR: ");
        System.out.println("NOME: " + super.getNome());
        System.out.println("IDADE: " + super.getIdade());
        System.out.println("CPF: " + super.getCpf());
        System.out.println("ENDEREÇO: " + super.getEndereco());
        System.out.println("UF: " + super.getEndereco().getUf());
        System.out.println("CIDADE: " + super.getEndereco().getCidade());
    }

    public void editarUsuario(Scanner scanner, Gerenciador gerenciador) {
        Usuario usuario;
        int escolhaEditar;

        while (true) {
            try {
                System.out.println("-".repeat(20));
                gerenciador.listarUsuarios();
                System.out.print("INFORME O CPF: ");
                String cpf = scanner.nextLine();
                usuario = gerenciador.procurarUsuario(cpf);

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.println("INFORME O QUE SERÁ EDITADO: ");
                System.out.println("1. NOME");
                System.out.println("2. IDADE");
                System.out.println("3. CPF");
                System.out.println("4. ENDEREÇO");
                System.out.print("SUA RESPOSTA: ");
                escolhaEditar = scanner.nextInt();
                scanner.nextLine();

                if (escolhaEditar > 4 || escolhaEditar < 1) {
                    throw new IllegalArgumentException("Preencha o campo corretamente. A sua escolha deve ser coerente com as opções (1-4).");
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números.");
                scanner.nextLine();
            }
        }

        switch (escolhaEditar) {
            case 1:
                // Novo Nome
                System.out.print("INFORME O NOVO NOME: ");
                String novoNome = scanner.nextLine();
                usuario.setNome(novoNome);

                break;
            case 2:
                // Nova Idade
                while (true) {
                    System.out.print("INFORME A NOVA IDADE: ");
                    int novaIdade = scanner.nextInt();

                    if (novaIdade < 15) {
                        System.out.println("- ERRO: A idade deve ser de no mínimo 15.");
                    } else {
                        usuario.setIdade(novaIdade);
                        break;
                    }
                }

                break;
            case 3:
                // Novo CPF
                while (true) {
                    try {
                        System.out.print("INFORME O NOVO CPF: ");
                        String novoCPF = scanner.nextLine();

                        if (novoCPF.matches("\\d+")) {
                            System.out.println("- ERRO: O CPF não pode conter letras ou símbolos. Digite apenas os números.");
                            continue;
                        }

                        usuario.setCpf(novoCPF);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("- ERRO: Somente números.");
                    }
                }
                break;
            case 4:
                // Novo Endereço
                do {
                    try {
                        System.out.print("INFORME O ESTADO: ");
                        String novoEstado = scanner.nextLine();

                        System.out.println("INFORME A CIDADE: ");
                        String novaCidade = scanner.nextLine();

                        System.out.println("INFORME O BAIRRO: ");
                        String novoBairro = scanner.nextLine();

                        System.out.println("INFORME O CEP: ");
                        String novoCEP = scanner.nextLine();

                        System.out.println("INFORME O NÚMERO DA CASA: ");
                        int novoNumero = scanner.nextInt();
                        scanner.nextLine();


                        Endereco endereco = new Endereco(novoEstado, novaCidade, novoBairro, novoCEP, novoNumero);
                        usuario.setEndereco(endereco);
                        System.out.println(" - ENDEREÇO ATUALIZADO - ");
                    } catch (InputMismatchException e) {
                        System.out.println("- ERRO: Preencha o campo corretamente.");
                        scanner.nextLine();
                    }
                } while (true);
        }

    }

    public void editarProduto(Scanner scanner, Gerenciador gerenciador) {

    }

    public void removerUsuario(Scanner scanner, Gerenciador gerenciador) {
        System.out.println("-".repeat(20));
        if (gerenciador.getUsuarios().isEmpty()) {
            System.out.println("- ERRO: A lista está vazia.");
            return;
        }

        gerenciador.listarUsuarios();
        while (true) {
            try {
                System.out.print("INFORME O CPF (\"000\" para sair): ");
                String cpf = scanner.nextLine();

                if (cpf.equals("000")) {
                    System.out.println(" - RETORNANDO - ");
                    break;
                }

                if (cpf.isEmpty()) {
                    throw new IllegalArgumentException("Preecha corretamente. O campo não pode estar vazio.");
                }

                gerenciador.getUsuarios().remove(gerenciador.procurarUsuario(cpf));
                System.out.println(" - USUÁRIO REMOVIDO - ");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }
    }

    public void removerProduto(Scanner scanner, Gerenciador gerenciador) {
        System.out.println("-".repeat(20));
        if (gerenciador.getProdutos().isEmpty()) {
            System.out.println("- ERRO: A lista está vazia.");
            return;
        }

        gerenciador.listarProdutos();
        while (true) {
            try {
                System.out.print("INFORME O ID (\"000\" para sair): ");
                int idProduto = scanner.nextInt();

                if (idProduto == 0) {
                    System.out.println(" - RETORNANDO - ");
                    break;
                }

                gerenciador.getProdutos().remove(gerenciador.getProdutos().get(idProduto-1));
                System.out.println(" - PRODUTO REMOVIDO - ");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. Somente números.");
            }
        }
    }

}