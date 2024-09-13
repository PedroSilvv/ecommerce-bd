package com.mycompany.ecommerce.repositories.custom;

import com.mycompany.ecommerce.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class CustomAvaliacaoRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Long inserirAvaliacaoReturningId(Long produtoId, String doc, Integer nota)  {

        StringBuilder queryBuilder = new StringBuilder(
                "INSERT INTO avaliacao (produto_id, usuario_doc, nota) " +
                "VALUES (:produto_id, :usuario_doc, :nota)" +
                "RETURNING id"
        );

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("produto_id", produtoId);
        query.setParameter("usuario_doc", doc);
        query.setParameter("nota", nota);

        Object idAvaliacao = query.getSingleResult();

        if (idAvaliacao != null) {
            return ((Number) idAvaliacao).longValue();
        } else {
            throw new NotFoundException("Erro ao retornar Id");
        }

    }
}
