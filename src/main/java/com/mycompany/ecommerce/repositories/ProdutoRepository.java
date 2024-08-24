package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM produto ORDER BY id DESC", nativeQuery = true)
    List<Produto> findAll();


    @Query(value = "SELECT * FROM produto WHERE id = (:id)", nativeQuery = true)
    Produto findByIdProduto(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO produto (categoria_id, nome, descricao, quantidade, preco)" +
                   " VALUES (:categoria_id, :nome, :descricao, :quantidade, :preco)", nativeQuery = true)
    void createProduto(
            @Param("categoria_id") Long categoria,
            @Param("nome") String nome,
            @Param("descricao") String descricao,
            @Param("quantidade") Integer quantidade,
            @Param("preco") BigDecimal preco
            );

    @Query(value = "SELECT * FROM produto WHERE categoria_id = (:categoria_id)", nativeQuery = true)
    List<Produto> findByCategoria(@Param("categoria_id") Long categoria);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long findLastInsertId();

    @Modifying
    @Transactional
    @Query(value = "UPDATE produto SET quantidade = (:novo_valor) WHERE id = (:id)", nativeQuery = true)
    void updateQuantidade(@Param("novo_valor") Integer novoValor, @Param("id") Long id);
}
