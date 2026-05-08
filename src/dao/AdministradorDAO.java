package dao;

import model.Administrador;
import model.Cliente;
import model.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    // INSERT
    public void salvar(Administrador admin) {
        String sql = "INSERT INTO administrador (nome, cpf, idade, email, telefone, endereco_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement entregador = conexao.prepareStatement(sql)) {

            entregador.setString(1, admin.getNome());
            entregador.setString(2, admin.getCpf());
            entregador.setInt(3, admin.getIdade());
            entregador.setString(4, admin.getEmail());
            entregador.setString(5, admin.getTelefone());
            entregador.setInt(6, admin.getEndereco().getId());

            entregador.executeUpdate();
            System.out.println(" - ADMINISTRADOR SALVO NO BANCO DE DADOS - ");

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO NA CONEXÃO DO ADMINISTRADOR: " + e.getMessage());
        }
    }

    // SELECT
    public List<Administrador> listarTodos() {
        List<Administrador> administradores = new ArrayList<>();

        String sql = "SELECT a.*, e.* FROM administrador a JOIN endereco e ON a.endereco_id = e.id";

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

                Administrador a = new Administrador(
                        rs.getString("nome"), rs.getString("cpf"),
                        rs.getInt("idade"), rs.getString("email"),
                        rs.getString("telefone"), end
                );

                a.setId(rs.getInt("id"));
                administradores.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage());
        }
        return administradores;
    }

    // UPDATE
    public void atualizar(Administrador admin) {
        new EnderecoDAO().atualizar(admin.getEndereco());

        String sql = "UPDATE administrador SET nome = ?, idade = ?, cpf = ? WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, admin.getNome());
            stmt.setInt(2, admin.getIdade());
            stmt.setString(3, admin.getCpf());
            stmt.setInt(4, admin.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO AO ATUALIZAR ADMINISTRADOR: " + e.getMessage());
        }
    }

    // DELETE
    public void excluir(int idAdmin) {
        String sql = "DELETE FROM administrador WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idAdmin);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir administrador: " + e.getMessage());
        }
    }

}