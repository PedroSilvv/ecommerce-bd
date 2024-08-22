package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "SELECT * FROM produto WHERE usuario_doc = (:usuario_doc)", nativeQuery = true)
    Produto findByUsuario(@Param("usuario_doc") String usuarioDoc);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO compra (usuario_doc, status_compra, preco_total)" +
            " VALUES (:usuario_doc, :status_compra, :preco_total)", nativeQuery = true)
    void createCompra(
            @Param("usuario_doc") String usuarioDoc,
            @Param("status_compra") String statusCompra,
            @Param("preco_total") BigDecimal precoTotal
    );

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long findLastInsertId();

    @Modifying
    @Transactional
    @Query(value = "UPDATE compra SET preco_total = (:preco_total) WHERE id = (:id)", nativeQuery = true)
    void updatePrecoTotal(
            @Param("preco_total") BigDecimal precoTotal,
            @Param("id") Long id
    );

}
