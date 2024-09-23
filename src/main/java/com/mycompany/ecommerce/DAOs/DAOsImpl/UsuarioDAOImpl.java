package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.UsuarioDAO;
import com.mycompany.ecommerce.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    private final DataSource dataSource;

    @Autowired
    public UsuarioDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    //crud

    @Override
    public List<Usuario> buscarTodos() throws Exception {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = mapRowToUsuario(rs);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }

        return usuarios;
    }

    @Override
    public Usuario buscarPorId(Object doc) {
        String sql = "SELECT * FROM usuario WHERE doc = ?";
        Usuario usuario = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, (String) doc);

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
    public void inserir(Object...params) {

        String doc = (String) params[0];
        String nome = (String) params[1];
        String role = (String) params[2];
        String password = (String) params[3];
        LocalDate dataNasc = (LocalDate) params[4];

        java.sql.Date dataSql = java.sql.Date.valueOf(dataNasc);


        String sql = "INSERT INTO usuario (doc, nome, user_role, password, data_nasc) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doc);
            stmt.setString(2, nome);
            stmt.setString(3, role);
            stmt.setString(4, password);
            stmt.setDate(5, dataSql);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao inserir usuario.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o usuário: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Usuario usuario, Object id) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }


    //map row

    private Usuario mapRowToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setDoc(rs.getString("doc"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSingleRole(rs.getString("user_role"));
        usuario.setPassword(rs.getString("password"));
        usuario.setDataNasc(rs.getDate("data_nasc"));

        return usuario;
    }
}
