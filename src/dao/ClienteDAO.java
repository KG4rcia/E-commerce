package dao;
import model.Administrador;
import model.Cliente;
import model.Endereco;
import model.Vendedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // INSERT
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, idade, email, telefone, endereco_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement entregador = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            entregador.setString(1, cliente.getNome());
            entregador.setString(2, cliente.getCpf());
            entregador.setInt(3, cliente.getIdade());
            entregador.setString(4, cliente.getEmail());
            entregador.setString(5, cliente.getTelefone());
            entregador.setInt(6, cliente.getEndereco().getId());

            entregador.executeUpdate();

            // Resgatando o ID gerado pelo banco
            try (ResultSet rs = entregador.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }

            System.out.println(" - CLIENTE SALVO NO BANCO DE DADOS - ");

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO NA CONEXÃO DO CLIENTE: " + e.getMessage());
        }
    }

    // SELECT
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT c.*, e.* FROM cliente c JOIN endereco e ON c.endereco_id = e.id";

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

                Cliente c = new Cliente(
                        rs.getString("nome"), rs.getString("cpf"),
                        rs.getInt("idade"), rs.getString("email"),
                        rs.getString("telefone"), end
                );

                c.setId(rs.getInt("id"));
                clientes.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // UPDATE
    public void atualizar(Cliente cliente) {
        new EnderecoDAO().atualizar(cliente.getEndereco());

        String sql = "UPDATE cliente SET nome = ?, idade = ?, cpf = ? WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setInt(2, cliente.getIdade());
            stmt.setString(3, cliente.getCpf());
            stmt.setInt(4, cliente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO AO ATUALIZAR CLIENTE: " + e.getMessage());
        }
    }

    // EXCLUIR
    public void excluir(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage());
        }
    }

}