package dao;
import model.Endereco;
import java.sql.*;

public class EnderecoDAO {

    // INSERT
    public void salvar(Endereco endereco) {
        String sql = "INSERT INTO endereco (uf, cidade, bairro, cep, num, taxa_entrega) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
            PreparedStatement entregador = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            entregador.setString(1, endereco.getUf());
            entregador.setString(2, endereco.getCidade());
            entregador.setString(3, endereco.getBairro());
            entregador.setString(4, endereco.getCep());
            entregador.setInt(5, endereco.getNum());
            entregador.setDouble(6, endereco.getTaxaEntrega());

            entregador.executeUpdate();

            try (ResultSet resultadoId = entregador.getGeneratedKeys()) {
                if (resultadoId.next()) {
                    int idGerado = resultadoId.getInt(1);
                    endereco.setId(idGerado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // UPDATE
    public void atualizar(Endereco endereco) {
        String sql = "UPDATE endereco SET uf = ?, cidade = ?, bairro = ?, cep = ?, num = ? WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getUf());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCep());
            stmt.setInt(5, endereco.getNum());
            stmt.setInt(6, endereco.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("- ERRO AO ATUALIZAR ENDEREÇO: " + e.getMessage());
        }
    }

}
