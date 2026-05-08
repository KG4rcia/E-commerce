package dao;

import model.Administrador;
import model.Cliente;
import model.Endereco;
import model.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {

    // INSERT
    public void salvar(Vendedor vendedor) {
        String sql = "INSERT INTO vendedor (nome, cpf, idade, email, telefone, vendas, endereco_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement entregador = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            entregador.setString(1, vendedor.getNome());
            entregador.setString(2, vendedor.getCpf());
            entregador.setInt(3, vendedor.getIdade());
            entregador.setString(4, vendedor.getEmail());
            entregador.setString(5, vendedor.getTelefone());
            entregador.setInt(6, vendedor.getVendas()); // Campo específico do vendedor
            entregador.setInt(7, vendedor.getEndereco().getId()); // FK do endereço

            entregador.executeUpdate();

            ResultSet rs = entregador.getGeneratedKeys();
            if (rs.next()) {
                vendedor.setId(rs.getInt(1));
            }

            System.out.println(" - VENDEDOR SALVO NO BANCO DE DADOS - ");

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO NA CONEXÃO DO VENDEDOR: " + e.getMessage());
        }
    }

    // SELECT
    public List<Vendedor> listarTodos() {
        List<Vendedor> vendedores = new ArrayList<>();

        String sql = "SELECT v.*, e.* FROM vendedor v JOIN endereco e ON v.endereco_id = e.id";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {

            while (rs.next()) {
                Endereco end = new Endereco(
                        rs.getString("uf"), rs.getString("cidade"),
                        rs.getString("bairro"), rs.getString("cep"),
                        rs.getInt("num")
                );

                end.setTaxaEntrega(rs.getDouble("taxa_entrega"));


                end.setId(rs.getInt("endereco_id"));

                Vendedor v = new Vendedor(
                        rs.getString("nome"), rs.getString("cpf"),
                        rs.getInt("idade"), rs.getString("email"),
                        rs.getString("telefone"), end
                );

                v.setVendas(rs.getInt("vendas"));
                v.setId(rs.getInt("id"));
                vendedores.add(v);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Vendedores: " + e.getMessage());
        }
        return vendedores;
    }

    // UPDATE
    public void atualizar(Vendedor vendedor) {
        new EnderecoDAO().atualizar(vendedor.getEndereco());

        String sql = "UPDATE vendedor SET nome = ?, idade = ?, cpf = ? WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, vendedor.getNome());
            stmt.setInt(2, vendedor.getIdade());
            stmt.setString(3, vendedor.getCpf());
            stmt.setInt(4, vendedor.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO AO ATUALIZAR VENDEDOR: " + e.getMessage());
        }
    }

    // EXCLUIR
    public void excluir(int idVendedor) {
        String sql = "DELETE FROM vendedor WHERE id = ?";
        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idVendedor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir vendedor: " + e.getMessage());
        }
    }

}