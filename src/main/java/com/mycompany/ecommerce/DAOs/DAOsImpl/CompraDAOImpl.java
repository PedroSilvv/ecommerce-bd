package com.mycompany.ecommerce.DAOs.DAOsImpl;

import com.mycompany.ecommerce.DAOs.DAOS.CompraDAO;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class CompraDAOImpl implements CompraDAO {

    private final DataSource dataSource;

    @Autowired
    public CompraDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    CompraProdutoDAOImpl compraProdutoDAO;

    //crud

    @Override
    public List<Compra> buscarTodos() throws Exception {
        String sql = "SELECT * FROM compra";
        List<Compra> compras = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Compra compra = mapRowToCompra(rs);
                    compras.add(compra);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar compras: " + e.getMessage(), e);
        }
        return compras;
    }

    @Override
    public Compra buscarPorId(Object id) throws Exception {
        String sql = "SELECT * FROM compra WHERE nota_fiscal = ?";
        Compra compra = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, (String) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                compra = mapRowToCompra(rs);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar produto por ID: " + e.getMessage(), e);
        }

        return compra;
    }

    @Override
    public void inserir(Object... params) throws Exception {
        System.out.println("CompraDAOImpl.inserir");
        String notaFiscal = (String) params[0];
        String usuarioDoc = (String) params[1];
        String statusCompra = (String) params[2];
        BigDecimal precoTotal = (BigDecimal) params[3];
        Date dataCompra = (Date) params[4];

        String sql =
                "INSERT INTO compra (nota_fiscal, usuario_doc, status_compra, preco_total, data_compra)" +
                " VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, notaFiscal);
            stmt.setString(2, usuarioDoc);
            stmt.setString(3, statusCompra);
            stmt.setBigDecimal(4, precoTotal);
            stmt.setDate(5, new java.sql.Date(dataCompra.getTime()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao inserir produto.");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Compra compra, Object id) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }


    // operacoes
    @Override
    public List<Compra> buscarPorUsuario(String usuarioDoc) throws Exception {
        String sql = "SELECT * FROM compra WHERE usuario_doc = ?";
        List<Compra> compras = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioDoc);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Compra compra = mapRowToCompra(rs);
                    compras.add(compra);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar compra por Usuario: " + e.getMessage(), e);
        }
        return compras;
    }

    @Override
    public void atualizarPrecoTotal(BigDecimal precoTotal, String notaFiscal) throws Exception {

        String sql = "UPDATE compra SET preco_total = ? WHERE nota_fiscal = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, precoTotal);
            stmt.setString(2, notaFiscal);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao atualizar preco da compra");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar preco da compra: " + e.getMessage(), e);
        }
    }

    @Override
    public void alterarStatusCompra(String notaFiscal, String status) throws Exception {
        String sql = "UPDATE compra SET status_compra = ? WHERE nota_fiscal = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, notaFiscal);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erro ao atualizar status da compra");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar status da compra: " + e.getMessage(), e);
        }
    }


    //MAP ROW

    private Compra mapRowToCompra(ResultSet rs) throws Exception {
        Compra compra = new Compra();
        compra.setNotaFiscal(rs.getString("nota_fiscal"));
        compra.setUsuarioDoc(rs.getString("usuario_doc"));
        compra.setStatusJdbc(rs.getString("status_compra"));
        compra.setPrecoTotal(rs.getBigDecimal("preco_total"));
        compra.setDataCompra(rs.getDate("data_compra"));

        List<CompraProduto> compraProdutoList = compraProdutoDAO.buscarPorCompra(rs.getString("nota_fiscal"));
        Set<CompraProduto> compraProdutoSet = new HashSet<>(compraProdutoList);
        compra.setCompraProdutos(compraProdutoSet);

        return compra;
    }
}
