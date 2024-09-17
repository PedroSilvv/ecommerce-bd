package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Avaliacao;
import com.mycompany.ecommerce.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {


    @Query(value = "INSERT INTO avaliacao (produto_id, usuario_doc, nota) " +
                   "VALUES (:produto_id, :usuario_doc, :nota)", nativeQuery = true)
    void createAvaliacao(
            @Param("produto_id") Long produtoId,
            @Param("usuario_doc") String usuarioDoc,
            @Param("nota") Integer nota
    );

    @Query(value = "SELECT * FROM avaliacao WHERE id = (:id)", nativeQuery = true)
    Avaliacao findByIdAvaliacao(@Param("id") Long id);


    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM avaliacao " +
                   "WHERE usuario_doc = :usuario_doc and produto_id = :produto_id", nativeQuery = true)
    boolean existsByProdutoAndUserDoc(
            @Param("usuario_doc") String usuarioDoc,
            @Param("produto_id") Long produtoId
    );
}
