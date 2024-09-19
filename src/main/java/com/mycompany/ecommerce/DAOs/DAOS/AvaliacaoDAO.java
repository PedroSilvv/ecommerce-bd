package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.jdbcModels.AvaliacaoJdbc;


public interface AvaliacaoDAO extends GenericDAO<AvaliacaoJdbc>{

//    void inserirAvaliacao(Long produtoId, String usuarioDoc, Integer nota) throws Exception;
//
//    AvaliacaoJdbc buscarPorId(Long id) throws Exception;

    boolean existsByProdutoAndUserDoc(String usuarioDoc, Long produtoId) throws Exception;

}
