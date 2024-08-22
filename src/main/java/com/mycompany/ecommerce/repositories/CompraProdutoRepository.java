package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraProdutoRepository extends JpaRepository<CompraProduto, Long> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO compra_item (compra_id, produto_id, quantidade_item)" +
            " VALUES (:compra_id, :produto_id, :quantidade_item)", nativeQuery = true)
    void createCompraProduto(
            @Param("compra_id") Long compraId,
            @Param("produto_id") Long produtoId,
            @Param("quantidade_item") Integer quantidadeItem
    );



}
