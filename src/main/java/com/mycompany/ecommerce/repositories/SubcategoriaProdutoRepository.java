package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.SubcategoriaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface SubcategoriaProdutoRepository extends JpaRepository<SubcategoriaProduto, Long> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO subcategoria_produto (subcategoria_id, produto_id)" +
            " VALUES (:subcategoria_id, :produto_id)", nativeQuery = true)
    void create(
            @Param("subcategoria_id") Long subcategoria,
            @Param("produto_id") Long produto
    );

}
