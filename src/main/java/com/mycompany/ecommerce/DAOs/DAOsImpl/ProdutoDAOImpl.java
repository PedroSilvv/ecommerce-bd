package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.ProdutoDAO;
import com.mycompany.ecommerce.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class ProdutoDAOImpl implements ProdutoDAO {

    private final DataSource dataSource;

    @Autowired
    public ProdutoDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // crud

    @Override
    public List<Produto> buscarTodos() throws Exception {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = mapRowToProduto(rs);
                produtos.add(produto);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }

        return produtos;
    }

    @Override
    public Produto buscarPorId(Object id) throws Exception {

        String sql = "SELECT * FROM produto WHERE id = ?";
        Produto produto = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, (Long) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = mapRowToProduto(rs);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produto por ID: " + e.getMessage(), e);
        }

        return produto;
    }

    @Override
    public void inserir(Object... params) throws Exception {

        Produto produto = (Produto) params[0];

        String sql =
                "INSERT INTO produto (categoria_id, nome, descricao, quantidade, preco) " +
                 "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, produto.getCategoriaId());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setBigDecimal(5, produto.getPreco());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserção de produto falhou, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Inserção de produto falhou, ID não obtido.");
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Produto produto, Object id) throws Exception {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, quantidade = ?, preco = ?  WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setBigDecimal(4, produto.getPreco());
            stmt.setLong(5, (Long) id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Atualização de produto falhou");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleção de produto falhou, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }


    // operacoes

    @Override
    public void atualizarQuantidade(Integer novaQuantidade, Long id) throws Exception {
        System.out.println("ProdutoDAOImpl.atualizarQuantidade");
        String sql = "UPDATE produto SET quantidade = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setLong(2, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao atualizar quantidade de produtos");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar quantidade de produtos: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizarQuantidadeDeVendas(Integer novaQuantidade, Long id) throws Exception {
        System.out.println("ProdutoDAOImpl.atualizarQuantidadeDeVendas");
        String sql = "UPDATE produto SET quantidade_vendas = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setLong(2, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Atualização da quantidade de vendas falhou, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar quantidade de vendas: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> buscarPorCategoria(Long id) throws Exception {
        String sql = "SELECT * FROM produto WHERE categoria_id = ?";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = mapRowToProduto(rs);
                    produtos.add(produto);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produto por categoria: " + e.getMessage(), e);
        }
        return produtos;
    }


    // Relatorios

    @Override
    public List<Map<String, Object>> findMostSelled() throws Exception {
        String sql = "SELECT id, nome, quantidade_vendas FROM produto WHERE quantidade_vendas > 0 ORDER BY quantidade_vendas DESC";
        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getLong("id"));
                row.put("nome", rs.getString("nome"));
                row.put("quantidade_vendas", rs.getInt("quantidade_vendas"));
                result.add(row);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produtos mais vendidos: " + e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findMostSelledByDate(Date dataI, Date dataF) throws Exception {
        String sql = "SELECT p.id, p.nome, SUM(ci.quantidade_item) AS total_vendido, " +
                "(SUM(ci.quantidade_item) * p.preco) AS total_receita " +
                "FROM produto p " +
                "JOIN compra_item ci ON p.id = ci.produto_id " +
                "JOIN compra c ON ci.compra_nota_fiscal = c.nota_fiscal " +
                "WHERE c.data_compra BETWEEN ? AND ? " +
                "GROUP BY p.id, p.nome";
        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(dataI.getTime()));
            stmt.setDate(2, new java.sql.Date(dataF.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getLong("id"));
                row.put("nome", rs.getString("nome"));
                row.put("total_vendido", rs.getInt("total_vendido"));
                row.put("total_receita", rs.getBigDecimal("total_receita"));
                result.add(row);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produtos mais vendidos por data: " + e.getMessage(), e);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> findMostPopulars() throws Exception {
        String sql = "SELECT p.nome AS produto, \n" +
                "ROUND(SUM(a.nota) * 1.0 / COUNT(*), 2) AS media\n" +
                "FROM produto p\n" +
                "JOIN avaliacao a ON a.produto_id = p.id\n" +
                "GROUP BY p.id\n" +
                "HAVING COUNT(*) > 0\n" +
                "ORDER BY media DESC;";

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("produto", rs.getString("produto"));
                row.put("media", rs.getDouble("media"));
                result.add(row);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produtos mais populares: " + e.getMessage(), e);
        }

        return result;
    }


    //Map row

    private Produto mapRowToProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getLong("id"));
        produto.setCategoriaId(rs.getLong("categoria_id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setQuantidade(rs.getInt("quantidade"));
        produto.setPreco(rs.getBigDecimal("preco"));
        produto.setQuantidadeVendas(rs.getInt("quantidade_vendas"));
        return produto;
    }
}
