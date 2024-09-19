package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.SubcategoriaDAO;
import com.mycompany.ecommerce.models.Subcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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


    // crud

    @Override
    public void inserir(Object... params) throws Exception {

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
            throw new Exception("Erro ao buscar produto por Id: " + e.getMessage(), e);
        }

        return subcategoria;
    }

    @Override
    public List<Subcategoria> buscarTodos() throws Exception {
        return List.of();
    }

    @Override
    public void atualizar(Subcategoria subcategoria, Object id) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

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
