package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.Avaliacao;


public interface AvaliacaoDAO extends GenericDAO<Avaliacao>{

//    void inserirAvaliacao(Long produtoId, String usuarioDoc, Integer nota) throws Exception;
//
//    AvaliacaoJdbc buscarPorId(Long id) throws Exception;

    boolean existsByProdutoAndUserDoc(String usuarioDoc, Long produtoId) throws Exception;

}
