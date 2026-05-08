package dao;

import model.Cliente;
import model.Endereco;
import model.Pedido;
import model.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // INSERT
    public void salvar(Pedido pedido) {
        String sql = "INSERT INTO pedido (cliente_id, produto_id, quantidade_produto, valor_produto, porte_grande, status, vendedor_id, taxa_entrega) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setInt(2, pedido.getIdProduto());
            stmt.setInt(3, pedido.getQuantidadeProduto());
            stmt.setDouble(4, pedido.getValorProduto());
            stmt.setBoolean(5, pedido.isPorteGrande());
            stmt.setString(6, pedido.getStatus());
            stmt.setInt(7, pedido.getVendedor().getId());
            stmt.setDouble(8, pedido.getTaxaEntrega());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }
            System.out.println("✅ Pedido salvo no banco de dados!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage());
        }
    }

    // SELECT
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT ped.*, pr.nome as pr_nome, pr.descricao as pr_desc, " +
                "c.nome as c_nome, c.cpf as c_cpf, c.idade as c_idade, c.email as c_email, c.telefone as c_telefone, " +
                "ec.uf as c_uf, ec.cidade as c_cidade, ec.bairro as c_bairro, ec.cep as c_cep, ec.num as c_num, ec.taxa_entrega as c_taxa, ec.id as c_end_id, " +
                "v.nome as v_nome, v.cpf as v_cpf, v.idade as v_idade, v.email as v_email, v.telefone as v_telefone, v.vendas, " +
                "ev.uf as v_uf, ev.cidade as v_cidade, ev.bairro as v_bairro, ev.cep as v_cep, ev.num as v_num, ev.taxa_entrega as v_taxa, ev.id as v_end_id " +
                "FROM pedido ped " +
                "JOIN produto pr ON ped.produto_id = pr.id " +
                "JOIN cliente c ON ped.cliente_id = c.id " +
                "JOIN endereco ec ON c.endereco_id = ec.id " +
                "JOIN vendedor v ON ped.vendedor_id = v.id " +
                "JOIN endereco ev ON v.endereco_id = ev.id";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {

            while (rs.next()) {
                // Recria Cliente + Endereço
                Endereco endCli = new Endereco(rs.getString("c_uf"), rs.getString("c_cidade"), rs.getString("c_bairro"), rs.getString("c_cep"), rs.getInt("c_num"));
                endCli.setTaxaEntrega(rs.getDouble("c_taxa"));
                endCli.setId(rs.getInt("c_end_id"));

                Cliente cli = new Cliente(rs.getString("c_nome"), rs.getString("c_cpf"), rs.getInt("c_idade"), rs.getString("c_email"), rs.getString("c_telefone"), endCli);
                cli.setId(rs.getInt("cliente_id"));

                // Recria Vendedor + Endereço
                Endereco endVend = new Endereco(rs.getString("v_uf"), rs.getString("v_cidade"), rs.getString("v_bairro"), rs.getString("v_cep"), rs.getInt("v_num"));
                endVend.setTaxaEntrega(rs.getDouble("v_taxa"));
                endVend.setId(rs.getInt("v_end_id"));

                Vendedor vend = new Vendedor(rs.getString("v_nome"), rs.getString("v_cpf"), rs.getInt("v_idade"), rs.getString("v_email"), rs.getString("v_telefone"), endVend);
                vend.setVendas(rs.getInt("vendas"));
                vend.setId(rs.getInt("vendedor_id"));

                // Recria Pedido
                Pedido ped = new Pedido(
                        cli,
                        rs.getInt("produto_id"),
                        rs.getString("pr_nome"),
                        rs.getString("pr_desc"),
                        rs.getInt("quantidade_produto"),
                        rs.getDouble("valor_produto"),
                        rs.getBoolean("porte_grande"),
                        vend
                );
                ped.setId(rs.getInt("id"));
                ped.setStatus(rs.getString("status"));
                ped.setTaxaEntrega(rs.getDouble("taxa_entrega"));

                pedidos.add(ped);
            }
        } catch (SQLException e) {
            throw new RuntimeException("- ERRO NA CONEXÃO DE PEDIDO" + e.getMessage());
        }
        return pedidos;
    }

    // PEDIDO
    public void atualizarStatus(int idPedido, String novoStatus) {
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idPedido);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO AO ATUALIZAR STATUS PEDIDO: " + e.getMessage());
        }
    }

    // DELETE
    public void excluir(int idPedido) {
        String sql = "DELETE FROM pedido WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pedido: " + e.getMessage());
        }
    }

}