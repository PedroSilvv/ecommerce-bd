package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.AvaliacaoDAO;
import com.mycompany.ecommerce.models.Avaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class AvaliacaoDAOImpl implements AvaliacaoDAO {

    private final DataSource dataSource;

    @Autowired
    public AvaliacaoDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //crud

    @Override
    public Avaliacao buscarPorId(Object id) throws Exception {

        String sql = "SELECT * FROM avaliacao WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, (Long) id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAvaliacao(rs);
                } else {
                    throw new SQLException("Avaliação não encontrada.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar avaliação: " + e.getMessage(), e);
        }
    }

    @Override
    public void inserir(Object... params) throws Exception {

        Long produtoId = (Long) params[0];
        String usuarioDoc = (String) params[1];
        Integer nota = (Integer) params[2];

        String sql =
                "INSERT INTO avaliacao (produto_id, usuario_doc, nota) " +
                        "VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, produtoId);
            stmt.setString(2, usuarioDoc);
            stmt.setInt(3, nota);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao inserir avaliação.");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao inserir avaliação: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Avaliacao> buscarTodos() throws Exception {
        return List.of();
    }

    @Override
    public void atualizar(Avaliacao avaliacao, Object id) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }


    // operacoes

    @Override
    public boolean existsByProdutoAndUserDoc(String usuarioDoc, Long produtoId) throws Exception {
        String sql =
                "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
                "FROM avaliacao WHERE usuario_doc = ? AND produto_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioDoc);
            stmt.setLong(2, produtoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                } else {
                    throw new SQLException("Erro ao verificar existência de avaliação.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao verificar existência de avaliação: " + e.getMessage(), e);
        }
    }


    // map row

    private Avaliacao mapRowToAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setId(rs.getLong("id"));
        avaliacao.setProdutoId(rs.getLong("produto_id"));
        avaliacao.setUsuarioDoc(rs.getString("usuario_doc"));
        avaliacao.setNota(rs.getInt("nota"));
        avaliacao.setDataAvaliacao(rs.getDate("data_avaliacao"));

        return avaliacao;
    }
}
