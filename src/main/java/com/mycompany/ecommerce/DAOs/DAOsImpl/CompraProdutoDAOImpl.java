package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.CompraProdutoDAO;
import com.mycompany.ecommerce.models.CompraProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompraProdutoDAOImpl implements CompraProdutoDAO {

    private final DataSource dataSource;

    @Autowired
    public CompraProdutoDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // crud

    @Override
    public void inserir(Object... params) throws Exception {

        String compraNotaFiscal = (String) params[0];
        Long produtoId = (Long) params[1];
        Integer quantidadeItem = (Integer) params[2];
        BigDecimal precoTotalItem = (BigDecimal) params[3];

        String sql =
                "INSERT INTO compra_item (compra_nota_fiscal, produto_id, quantidade_item, preco_total_item)" +
                " VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, compraNotaFiscal);
            stmt.setLong(2, produtoId);
            stmt.setInt(3, quantidadeItem);
            stmt.setBigDecimal(4, precoTotalItem);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao inserir CompraProduto");
            }


        } catch (SQLException e) {
            throw new Exception("Erro ao inserir CompraProduto: " + e.getMessage(), e);
        }
    }

    @Override
    public CompraProduto buscarPorId(Object id) throws Exception {
        return null;
    }

    @Override
    public List<CompraProduto> buscarTodos() throws Exception {
        return List.of();
    }

    @Override
    public void atualizar(CompraProduto compraProduto, Object id) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }


    //operacoes

    @Override
    public List<CompraProduto> buscarPorCompra(String compraNotaFiscal) throws Exception {
        String sql = "SELECT * FROM compra_item WHERE compra_nota_fiscal = ?";
        List<CompraProduto> compraProdutos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Definir o valor do parâmetro
            stmt.setString(1, compraNotaFiscal);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompraProduto compraProduto = mapRowToCompraProduto(rs);
                    compraProdutos.add(compraProduto);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar compra_itens: " + e.getMessage(), e);
        }
        return compraProdutos;
    }


    //map row

    private CompraProduto mapRowToCompraProduto(ResultSet rs) throws SQLException {
        CompraProduto compraProduto = new CompraProduto();
        compraProduto.setId(rs.getLong("id"));
        compraProduto.setCompraNotaFiscal(rs.getString("compra_nota_fiscal"));
        compraProduto.setProdutoId(rs.getLong("produto_id"));
        compraProduto.setQuantidadeItem(rs.getInt("quantidade_item"));
        compraProduto.setPrecoTotalItem(rs.getBigDecimal("preco_total_item"));

        return compraProduto;
    }
}
