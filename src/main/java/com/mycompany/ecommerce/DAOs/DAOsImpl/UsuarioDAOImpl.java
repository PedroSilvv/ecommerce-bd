package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.UsuarioDAO;
import com.mycompany.ecommerce.jdbcModels.ProdutoJdbc;
import com.mycompany.ecommerce.jdbcModels.UsuarioJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    private final DataSource dataSource;

    @Autowired
    public UsuarioDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<UsuarioJdbc> buscarTodosUsuarios() throws Exception {
        String sql = "SELECT * FROM usuario";
        List<UsuarioJdbc> usuarios = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UsuarioJdbc usuario = mapRowToUsuario(rs);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }

        return usuarios;
    }

    @Override
    public UsuarioJdbc buscarPorId(String doc) {
        String sql = "SELECT * FROM usuario WHERE doc = ?";
        UsuarioJdbc usuario = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doc);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = mapRowToUsuario(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o usuário: " + e.getMessage());
        }
        return usuario;
    }

    @Override
    public void inserirUsuario(String doc, String nome, String role, String password) {
        String sql = "INSERT INTO usuario (doc, nome, user_role, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doc);
            stmt.setString(2, nome);
            stmt.setString(3, role);
            stmt.setString(4, password);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao inserir usuario.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o usuário: " + e.getMessage());
        }
    }

    private UsuarioJdbc mapRowToUsuario(ResultSet rs) throws SQLException {
        UsuarioJdbc usuario = new UsuarioJdbc();

        usuario.setDoc(rs.getString("doc"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSingleRole(rs.getString("user_role"));
        usuario.setPassword(rs.getString("password"));
        usuario.setDataNasc(rs.getDate("data_nasc"));

        return usuario;
    }
}
