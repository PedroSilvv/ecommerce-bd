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
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Modifying
    @Transactional
    @Query(value = "UPDATE produto SET quantidade = (:novo_valor) WHERE id = (:id)", nativeQuery = true)
    void updateQuantidade(@Param("novo_valor") Integer novoValor, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE produto SET nome = (:nome), descricao = (:descricao), quantidade = (:quantidade), preco = (:preco), categoria_id = (:categoria_id) " +
                   "WHERE id = (:id)", nativeQuery = true)
    void updateProduto(
            @Param("nome") String nome,
            @Param("descricao") String descricao,
            @Param("quantidade") Integer quantidade,
            @Param("preco") BigDecimal preco,
            @Param("categoria_id") Long categoriaId,
            @Param("id") Long id
    );


    @Modifying
    @Transactional
    @Query(value = "UPDATE produto SET quantidade_vendas = (:quantidade_vendas) " +
            "WHERE id = (:id)", nativeQuery = true)
    void updateQuantidadeDeVendas(
            @Param("quantidade_vendas") Integer novaQuantidade,
            @Param("id") Long id
    );


    @Query(value = "SELECT produto.id, produto.nome , produto.quantidade_vendas FROM produto ORDER BY quantidade_vendas DESC", nativeQuery = true)
    List<Map<String, Object>> findMostSelled();

    @Query(value =
            "SELECT p.id, p.nome, SUM(ci.quantidade_item) AS total_vendido, (SUM(ci.quantidade_item) * p.preco) as total_receita\n" +
            "FROM produto p\n" +
            "JOIN compra_item ci ON p.id = ci.produto_id\n" +
            "JOIN compra c ON ci.compra_nota_fiscal = c.nota_fiscal\n" +
            "WHERE c.data_compra BETWEEN (:data_i) AND (:data_f)\n" +
            "GROUP BY p.id, p.nome",
            nativeQuery = true)
    List<Map<String, Object>> findMostSelledByDate(
            @Param("data_i") Date dataI,
            @Param("data_f") Date dataF
    );


}
