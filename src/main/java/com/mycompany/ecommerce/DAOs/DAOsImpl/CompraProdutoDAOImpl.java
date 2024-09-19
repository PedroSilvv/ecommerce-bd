package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.CompraProdutoDAO;
import com.mycompany.ecommerce.jdbcModels.CompraProdutoJdbc;
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


    @Override
    public void inserirCompraProduto(String compraId, Long produtoId, Integer quantidadeItem, BigDecimal precoTotalItem) throws Exception {
        System.out.println("CompraProdutoDAOImpl.inserirCompraProduto");
        String sql =
                "INSERT INTO compra_item (compra_nota_fiscal, produto_id, quantidade_item, preco_total_item)" +
                " VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, compraId);
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
    public List<CompraProdutoJdbc> buscarPorCompra(String compraNotaFiscal) throws Exception {
        String sql = "SELECT * FROM compra_item WHERE compra_nota_fiscal = ?";
        List<CompraProdutoJdbc> compraProdutoJdbcs = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Definir o valor do parâmetro
            stmt.setString(1, compraNotaFiscal);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompraProdutoJdbc compraProduto = mapRowToCompraProduto(rs);
                    compraProdutoJdbcs.add(compraProduto);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar compra_itens: " + e.getMessage(), e);
        }
        return compraProdutoJdbcs;
    }



    private CompraProdutoJdbc mapRowToCompraProduto(ResultSet rs) throws SQLException {
        CompraProdutoJdbc compraProduto = new CompraProdutoJdbc();
        compraProduto.setId(rs.getLong("id"));
        compraProduto.setCompraNotaFiscal(rs.getString("compra_nota_fiscal"));
        compraProduto.setProdutoId(rs.getLong("produto_id"));
        compraProduto.setQuantidadeItem(rs.getInt("quantidade_item"));

        return compraProduto;
    }
}
