package model;

import dao.AdministradorDAO;
import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendedorDAO;
import util.Gerenciador;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Administrador extends Usuario {

    public Administrador(String nome, String cpf, int idade, String email, String telefone, Endereco endereco) {
        super(nome, cpf, idade, email, telefone, endereco);
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

                System.out.println(" - NOME ATUALIZADO - ");
                break;
            case 2:
                // Nova Idade
                while (true) {
                    System.out.print("INFORME A NOVA IDADE: ");
                    int novaIdade = scanner.nextInt();

                    if (novaIdade < 15) {
                        System.out.println("- ERRO: A idade deve ser de no mínimo 15.");
                    } else {
                        System.out.println(" - IDADE ATUALIZADA - ");
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

                        if (!novoCPF.matches("\\d+")) {
                            System.out.println("- ERRO: O CPF não pode conter letras ou símbolos. Digite apenas os números.");
                            continue;
                        }

                        if (!gerenciador.cpfJaExiste(novoCPF)) {
                            usuario.setCpf(novoCPF);
                            System.out.println(" - CPF ATUALIZADO - ");
                            break;
                        } else {
                            throw new IllegalArgumentException("Esse CPF já está cadastrado. Coloque um CPF válido.");
                        }

                    } catch (InputMismatchException e) {
                        System.out.println("- ERRO: Somente números.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("- ERRO: " + e.getMessage());
                    }
                }
                break;
            case 4:
                // Novo Endereço
                do {
                    try {
                        System.out.print("INFORME O ESTADO: ");
                        String novoEstado = scanner.nextLine();

                        System.out.print("INFORME A CIDADE: ");
                        String novaCidade = scanner.nextLine();

                        System.out.print("INFORME O BAIRRO: ");
                        String novoBairro = scanner.nextLine();

                        System.out.print("INFORME O CEP: ");
                        String novoCEP = scanner.nextLine();

                        System.out.print("INFORME O NÚMERO DA CASA: ");
                        int novoNumero = scanner.nextInt();
                        scanner.nextLine();

                        Endereco end = usuario.getEndereco();
                        end.setUf(novoEstado);
                        end.setCidade(novaCidade);
                        end.setBairro(novoBairro);
                        end.setCep(novoCEP);
                        end.setNum(novoNumero);


                        System.out.println(" - ENDEREÇO ATUALIZADO - ");
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("- ERRO: Preencha o campo corretamente.");
                        scanner.nextLine();
                    }
                } while (true);
        }

        // Salvar alteração no banco de dados
        if (usuario instanceof Administrador) {
            new AdministradorDAO().atualizar((Administrador) usuario);
        } else if (usuario instanceof Vendedor) {
            new VendedorDAO().atualizar((Vendedor) usuario);
        } else if (usuario instanceof Cliente) {
            new ClienteDAO().atualizar((Cliente) usuario);
        }

    }

    public void editarProduto(Scanner scanner, Gerenciador gerenciador) {
        System.out.println("=".repeat(20));
        if (gerenciador.getProdutos().isEmpty()) {
            System.out.println("- ERRO: A lista de produtos está vazia.\n");
            return;
        }

        gerenciador.listarProdutos();
        Produto produtoSelecionado = null;

        // 1. Selecionar o Produto
        while (true) {
            try {
                System.out.print("INFORME O ID DO PRODUTO PARA EDITAR (\"0\" para sair): ");
                int idLista = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer

                if (idLista == 0) {
                    System.out.println(" - RETORNANDO - ");
                    return;
                }

                if (idLista < 1 || idLista > gerenciador.getProdutos().size()) {
                    throw new IndexOutOfBoundsException();
                }

                produtoSelecionado = gerenciador.getProdutos().get(idLista - 1);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("- ERRO: Produto não encontrado. Informe um ID válido.");
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. Somente números.");
                scanner.nextLine(); // Limpar o buffer
            }
        }

        System.out.println("\n--- NOVOS DADOS ---");

        // 2. Editar Nome
        while (true) {
            try {
                System.out.print("NOME ATUAL (" + produtoSelecionado.getNome() + ") -> NOVO NOME: ");
                produtoSelecionado.setNome(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 3. Editar Descrição
        while (true) {
            try {
                System.out.print("DESCRIÇÃO ATUAL (" + produtoSelecionado.getDescricao() + ") -> NOVA DESCRIÇÃO: ");
                produtoSelecionado.setDescricao(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 4. Editar Quantidade
        while (true) {
            try {
                System.out.print("QUANTIDADE ATUAL (" + produtoSelecionado.getQuantidade() + ") -> NOVA QUANTIDADE: ");
                produtoSelecionado.setQuantidade(scanner.nextInt());
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números inteiros.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 5. Editar Preço
        while (true) {
            try {
                System.out.print("PREÇO ATUAL (R$ " + produtoSelecionado.getPrecoUnitario() + ") -> NOVO PREÇO: ");
                produtoSelecionado.setPrecoUnitario(scanner.nextDouble());
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Somente números (use vírgula para decimais se o sistema pedir).");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 6. Editar Porte
        while (true) {
            System.out.print("GRANDE PORTE ATUAL (" + (produtoSelecionado.isGrandePorte() ? "SIM" : "NÃO") + ") -> GRANDE PORTE? (S/N): ");
            String porte = scanner.nextLine().toUpperCase().trim();
            if (porte.equals("S")) {
                produtoSelecionado.setGrandePorte(true);
                break;
            } else if (porte.equals("N")) {
                produtoSelecionado.setGrandePorte(false);
                break;
            } else {
                System.out.println("- ERRO: Responda apenas com 'S' ou 'N'.");
            }
        }

        // 7. Salvar no Banco
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.atualizar(produtoSelecionado);
            System.out.println("\n - PRODUTO ATUALIZADO COM SUCESSO - ");
        } catch (RuntimeException e) {
            System.out.println("- ERRO AO ATUALIZAR NO BANCO DE DADOS: " + e.getMessage());
        }
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
                    throw new IllegalArgumentException("Preencha corretamente. O campo não pode estar vazio.");
                }

                if (cpf.equals(super.cpf)) {
                    throw new IllegalArgumentException("Você não pode remover a si mesmo.");
                }

                // 1. Encontra o usuário na memória
                Usuario usuarioParaRemover = gerenciador.procurarUsuario(cpf);

                try {
                    if (usuarioParaRemover instanceof Administrador) {
                        new AdministradorDAO().excluir(usuarioParaRemover.getId());
                    } else if (usuarioParaRemover instanceof Vendedor) {
                        new VendedorDAO().excluir(usuarioParaRemover.getId());
                    } else if (usuarioParaRemover instanceof Cliente) {
                        new ClienteDAO().excluir(usuarioParaRemover.getId());
                    }

                    gerenciador.getUsuarios().remove(usuarioParaRemover);
                    System.out.println(" - USUÁRIO REMOVIDO - ");
                    break;

                } catch (RuntimeException ex) {
                    System.out.println("- ERRO NO BANCO: Não é possível excluir um usuário que possui pedidos ou produtos vinculados a ele.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            }
        }
    }

    public void removerProduto(Scanner scanner, Gerenciador gerenciador) {
        System.out.println("=".repeat(20));
        if (gerenciador.getProdutos().isEmpty()) {
            System.out.println("- ERRO: A lista está vazia.\n");
            return;
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        gerenciador.listarProdutos();

        while (true) {
            try {
                System.out.print("INFORME O ID (\"000\" para sair): ");
                int idProduto = scanner.nextInt();

                if (idProduto == 0) {
                    System.out.println(" - RETORNANDO - ");
                    break;
                }

                produtoDAO.excluir(gerenciador.getProdutos().get(idProduto-1).getId());
                gerenciador.getProdutos().remove(gerenciador.getProdutos().get(idProduto-1));
                System.out.println(" - PRODUTO REMOVIDO - ");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERRO: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("- ERRO: Preencha o campo corretamente. Somente números.");
            } catch (RuntimeException e) {
                System.out.println("- ERRO: Não é possível excluir um produto que já possui pedidos vinculados.");
                break;
            }
        }
    }

}