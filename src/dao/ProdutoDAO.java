package dao;

import model.Endereco;
import model.Produto;
import model.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // INSERT
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, quantidade, preco_unitario, grande_porte, vendedor_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setDouble(4, produto.getPrecoUnitario());
            stmt.setBoolean(5, produto.isGrandePorte());
            stmt.setInt(6, produto.getVendedor().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }

            System.out.println(" - PRODUTO SALVO NO BANCO DE DADOS - ");

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO NA CONEXÃO DE PRODUTO: " + e.getMessage());
        }
    }

    // SELECT
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();

        String sql = "SELECT p.*, v.nome as v_nome, v.cpf as v_cpf, v.idade as v_idade, v.email as v_email, v.telefone as v_telefone, v.vendas, " +
                "e.uf, e.cidade, e.bairro, e.cep, e.num, e.taxa_entrega, e.id as id_end " +
                "FROM produto p " +
                "JOIN vendedor v ON p.vendedor_id = v.id " +
                "JOIN endereco e ON v.endereco_id = e.id";

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
                end.setId(rs.getInt("id_end"));

                Vendedor v = new Vendedor(
                        rs.getString("v_nome"), rs.getString("v_cpf"),
                        rs.getInt("v_idade"), rs.getString("v_email"),
                        rs.getString("v_telefone"), end
                );

                v.setVendas(rs.getInt("vendas"));
                v.setId(rs.getInt("vendedor_id"));

                Produto p = new Produto(
                        rs.getString("nome"), rs.getString("descricao"),
                        rs.getDouble("preco_unitario"), rs.getInt("quantidade"),
                        rs.getBoolean("grande_porte"), v
                );
                p.setId(rs.getInt("id"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    // UPDATE
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, quantidade = ?, preco_unitario = ?, grande_porte = ? WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setDouble(4, produto.getPrecoUnitario());
            stmt.setBoolean(5, produto.isGrandePorte());
            stmt.setInt(6, produto.getId()); // Identificador de quem será alterado

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    // DELETE
    public void excluir(int idProduto) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idProduto); // Identificador de quem será apagado
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO AO EXCLUIR PRODUTO DO BANCO DE DADOS: " + e.getMessage());
        }
    }


}