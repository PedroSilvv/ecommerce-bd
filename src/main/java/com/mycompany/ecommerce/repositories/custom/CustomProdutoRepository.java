package com.mycompany.ecommerce.repositories.custom;

import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.models.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<FiltrarProdutoResponseDTO> filtrarProdutos(String nome, String categoria, BigDecimal precoMinimo, BigDecimal precoMaximo, String descricao) {
        StringBuilder queryBuilder = new StringBuilder("SELECT p.nome, p.preco, p.quantidade, c.nome, p.descricao " +
                " FROM produto p JOIN categoria c ON p.categoria_id = c.id " +
                "WHERE 1=1 ");

        if (nome != null && !nome.isEmpty()) {
            queryBuilder.append("AND p.nome = :nome ");
        }
        if (categoria != null && !categoria.isEmpty()) {
            queryBuilder.append("AND c.nome = :categoria ");
        }
        if (precoMinimo != null) {
            queryBuilder.append("AND p.preco >= :precoMinimo ");
        }
        if (precoMaximo != null) {
            queryBuilder.append("AND p.preco <= :precoMaximo ");
        }
        if (descricao != null && !descricao.isEmpty()) {
            queryBuilder.append("AND p.descricao LIKE :descricao ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString());



        // Define os parâmetros da query
        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", nome);
        }
        if (categoria != null && !categoria.isEmpty()) {
            query.setParameter("categoria", categoria);
        }
        if (precoMinimo != null) {
            query.setParameter("precoMinimo", precoMinimo);
        }
        if (precoMaximo != null) {
            query.setParameter("precoMaximo", precoMaximo);
        }
        if (descricao != null && !descricao.isEmpty()) {
            query.setParameter("descricao", "%" + descricao + "%");
        }

        List<Object[]> resultadoLista = query.getResultList();
        List<FiltrarProdutoResponseDTO> dtos = new ArrayList<>();

        for (Object[] o : resultadoLista) {
            String nomeProduto = (String) o[0];
            BigDecimal precoProduto = (BigDecimal) o[1];
            Integer quantidadeProduto = (Integer) o[2];
            String categoriaProduto = (String) o[3];
            String descricaoProduto = (String) o[4];

            dtos.add(new FiltrarProdutoResponseDTO(nomeProduto, precoProduto, quantidadeProduto, categoriaProduto, descricaoProduto));
        }

        return dtos;
    }
}
