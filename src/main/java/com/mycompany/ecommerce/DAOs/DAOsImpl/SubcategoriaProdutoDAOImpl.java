package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.SubcategoriaProdutoDAO;
import com.mycompany.ecommerce.jdbcModels.SubcategoriaProdutoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubcategoriaProdutoDAOImpl implements SubcategoriaProdutoDAO {

    private final DataSource dataSource;

    @Autowired
    public SubcategoriaProdutoDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void inserirSubcategoriaProduto(SubcategoriaProdutoJdbc subcategoriaProdutoJdbc) throws Exception {
        String sql = "INSERT INTO subcategoria_produto (subcategoria_id, produto_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, subcategoriaProdutoJdbc.getSubcategoriaId());
            stmt.setLong(2, subcategoriaProdutoJdbc.getProdutoId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao inserir subcategoria produto");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subcategoriaProdutoJdbc.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Erro ao inserir subcategoria produto");
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SubcategoriaProdutoJdbc> buscarPorProduto(Long produtoId) throws Exception {
        String sql = "SELECT * FROM subcategoria_produto WHERE produto_id = ?";
        List<SubcategoriaProdutoJdbc> subcategoriaProdutosList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, produtoId);

            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set
                while (rs.next()) {
                    SubcategoriaProdutoJdbc subcategoriaProduto = mapRowToSubcategoriaProduto(rs);
                    subcategoriaProdutosList.add(subcategoriaProduto);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar tuplas: " + e.getMessage(), e);
        }

        return subcategoriaProdutosList;
    }



    private SubcategoriaProdutoJdbc mapRowToSubcategoriaProduto(ResultSet rs) throws SQLException {
        SubcategoriaProdutoJdbc subcategoriaProduto = new SubcategoriaProdutoJdbc();
        subcategoriaProduto.setId(rs.getLong("id"));
        subcategoriaProduto.setSubcategoriaId(rs.getLong("subcategoria_id"));
        subcategoriaProduto.setProdutoId(rs.getLong("produto_id"));

        return subcategoriaProduto;
    }
}
