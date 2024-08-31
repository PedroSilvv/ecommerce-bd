package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "SELECT * FROM produto WHERE usuario_doc = (:usuario_doc)", nativeQuery = true)
    Produto findByUsuario(@Param("usuario_doc") String usuarioDoc);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO compra (nota_fiscal, usuario_doc, status_compra, preco_total, data_compra)" +
            " VALUES (:nota_fiscal, :usuario_doc, :status_compra, :preco_total, :data_compra)", nativeQuery = true)
    void createCompra(
            @Param("nota_fiscal") String nota_fiscal,
            @Param("usuario_doc") String usuarioDoc,
            @Param("status_compra") String statusCompra,
            @Param("preco_total") BigDecimal precoTotal,
            @Param("data_compra") Date dataCompra
    );

    @Query(value = "SELECT currval(pg_get_serial_sequence('compra', 'nota_fiscal'))", nativeQuery = true)
    String getLastInsertedId();

    @Modifying
    @Transactional
    @Query(value = "UPDATE compra SET preco_total = (:preco_total) WHERE nota_fiscal = (:nota_fiscal)", nativeQuery = true)
    void updatePrecoTotal(
            @Param("preco_total") BigDecimal precoTotal,
            @Param("nota_fiscal") String id
    );

}
