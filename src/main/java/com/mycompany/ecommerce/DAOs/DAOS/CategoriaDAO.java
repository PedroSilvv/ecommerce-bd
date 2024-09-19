package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.jdbcModels.CategoriaJdbc;


public interface CategoriaDAO {

    CategoriaJdbc buscarPorId(Long id) throws Exception;

}
