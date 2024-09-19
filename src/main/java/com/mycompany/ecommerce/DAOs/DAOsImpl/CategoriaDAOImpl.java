package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.CategoriaDAO;
import com.mycompany.ecommerce.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoriaDAOImpl implements CategoriaDAO {

    private final DataSource dataSource;

    @Autowired
    public CategoriaDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //crud

    @Override
    public List<Categoria> buscarTodos() throws Exception {
        return List.of();
    }

    @Override
    public Categoria buscarPorId(Object id) throws Exception{
        String sql = "SELECT * FROM categoria WHERE id = ?";
        Categoria categoria = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, (Long) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = mapRowToCategoria(rs);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar categoria por ID: " + e.getMessage(), e);
        }

        return categoria;
    }

    @Override
    public void inserir(Object... params) throws Exception {

    }

    @Override
    public void atualizar(Categoria categoria, Object id) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }



    //map row

    private Categoria mapRowToCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();

        categoria.setId(rs.getLong("id"));
        categoria.setNome(rs.getString("nome"));
        return categoria;
    }
}
