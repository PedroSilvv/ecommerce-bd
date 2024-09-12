package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface CompraProdutoRepository extends JpaRepository<CompraProduto, Long> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO compra_item (compra_nota_fiscal, produto_id, quantidade_item, preco_total_item)" +
            " VALUES (:compra_nota_fiscal, :produto_id, :quantidade_item, :preco_total_item)", nativeQuery = true)
    void createCompraProduto(
            @Param("compra_nota_fiscal") String compraId,
            @Param("produto_id") Long produtoId,
            @Param("quantidade_item") Integer quantidadeItem,
            @Param("preco_total_item") BigDecimal precoTotalItem
            );

    @Query(value = "SELECT * FROM compra_item WHERE compra_nota_fiscal = (:compra_nota_fiscal)", nativeQuery = true)
    List<CompraProduto> findByCompra(
            @Param("compra_nota_fiscal") String compraNotaFiscal
    );



}
