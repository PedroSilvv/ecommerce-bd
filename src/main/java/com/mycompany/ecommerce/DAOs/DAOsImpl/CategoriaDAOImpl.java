package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.CategoriaDAO;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        String sql = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria categoria = mapRowToCategoria(rs);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }

        return categorias;
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
        Categoria categoria = (Categoria) params[0];

        String sql =
                "INSERT INTO categoria (nome) VALUES (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNome());


            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("erro ao inserir categoria");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("erro ao inserir categoria, ID não obtido.");
                }
            }

        } catch (SQLException e) {
            throw new Exception("erro ao inserir categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Categoria categoria, Object id) throws Exception {
        String sql = "UPDATE categoria SET nome = ?  WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setLong(2, (Long) id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Atualização de categoria falhou");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("erro ao deletar categoria");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao deletar categoria: " + e.getMessage(), e);
        }
    }


    //map row

    private Categoria mapRowToCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();

        categoria.setId(rs.getLong("id"));
        categoria.setNome(rs.getString("nome"));
        return categoria;
    }

}
