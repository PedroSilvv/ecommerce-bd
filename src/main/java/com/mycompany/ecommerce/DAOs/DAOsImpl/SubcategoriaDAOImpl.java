package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.SubcategoriaDAO;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Subcategoria;
import com.mycompany.ecommerce.models.SubcategoriaProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubcategoriaDAOImpl implements SubcategoriaDAO {

    private final DataSource dataSource;

    @Autowired
    public SubcategoriaDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    @Override
    public Subcategoria buscarPorNome(String nome) throws Exception {

        String sql = "SELECT * FROM subcategoria WHERE nome = ?";
        Subcategoria subcategoria = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                subcategoria = mapRowToSubcategoria(rs);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produto por Nome: " + e.getMessage(), e);
        }

        return subcategoria;
    }

    @Override
    public List<Subcategoria> buscarSubcategoriasPorCategoria(Long categoriaId) throws Exception {
        String sql = "SELECT * FROM subcategoria where categoria_principal_id = ?";
        List<Subcategoria> subcategorias = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, categoriaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Subcategoria subcategoria = mapRowToSubcategoria(rs);
                    subcategorias.add(subcategoria);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar subcategoria: " + e.getMessage(), e);
        }

        return subcategorias;
    }


    @Override
    public List<Map<String, Object>> buscarSubcategoriasComMaisVendas(Date dataI, Date dataF) throws Exception {
        String sql = "SELECT s.id AS subcategoria_id, s.nome AS subcategoria_nome, SUM(ci.quantidade_item) AS total_vendas " +
                "FROM subcategoria s " +
                "JOIN subcategoria_produto sp ON s.id = sp.subcategoria_id " +
                "JOIN produto p ON sp.produto_id = p.id " +
                "JOIN compra_item ci ON p.id = ci.produto_id " +
                "JOIN compra c ON ci.compra_nota_fiscal = c.nota_fiscal " +
                "WHERE c.data_compra BETWEEN ? AND ? " +
                "GROUP BY s.id, s.nome " +
                "ORDER BY total_vendas DESC";

        List<Map<String, Object>> subcategorias = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, dataI);
            stmt.setDate(2, dataF);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> subcategoriaMap = new HashMap<>();
                    subcategoriaMap.put("subcategoria_id", rs.getLong("subcategoria_id"));
                    subcategoriaMap.put("subcategoria_nome", rs.getString("subcategoria_nome"));
                    subcategoriaMap.put("total_vendas", rs.getLong("total_vendas"));

                    subcategorias.add(subcategoriaMap);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar subcategorias com mais vendas: " + e.getMessage(), e);
        }

        return subcategorias;
    }

    // crud

    @Override
    public void inserir(Object... params) throws Exception {
        Subcategoria subcategoria = (Subcategoria) params[0];

        String sql =
                "INSERT INTO subcategoria (nome, categoria_principal_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, subcategoria.getNome());
            stmt.setLong(2, subcategoria.getCategoriaId());


            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("erro ao inserir subcategoria");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subcategoria.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("erro ao inserir subcategoria, ID não obtido.");
                }
            }

        } catch (SQLException e) {
            throw new Exception("erro ao inserir subcategoria: " + e.getMessage(), e);
        }
    }

    @Override
    public Subcategoria buscarPorId(Object id) throws Exception {
        String sql = "SELECT * FROM subcategoria WHERE id = ?";
        Subcategoria subcategoria = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, (Long) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                subcategoria = mapRowToSubcategoria(rs);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar subcategoria por Id: " + e.getMessage(), e);
        }

        return subcategoria;
    }

    @Override
    public List<Subcategoria> buscarTodos() throws Exception {
        String sql = "SELECT * FROM subcategoria";
        List<Subcategoria> subcategorias = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Subcategoria subcategoria = mapRowToSubcategoria(rs);
                subcategorias.add(subcategoria);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todas as subcategorias: " + e.getMessage(), e);
        }

        return subcategorias;
    }

    @Override
    public void atualizar(Subcategoria subcategoria, Object id) throws Exception {
        String sql = "UPDATE subcategoria SET nome = ?  WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subcategoria.getNome());
            stmt.setLong(2, (Long) id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Atualização de subcategoria falhou");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar subcategoria: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM subcategoria WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("erro ao deletar subcategoria");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao deletar subcategoria: " + e.getMessage(), e);
        }
    }


    // map row

    private Subcategoria mapRowToSubcategoria(ResultSet rs) throws SQLException {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(rs.getLong("id"));
        subcategoria.setNome(rs.getString("nome"));
        subcategoria.setCategoriaId(rs.getLong("categoria_principal_id"));

        return subcategoria;
    }
}
