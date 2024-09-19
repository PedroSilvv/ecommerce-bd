package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.jdbcModels.SubcategoriaJdbc;

public interface SubcategoriaDAO extends GenericDAO<SubcategoriaJdbc> {

    SubcategoriaJdbc buscarPorNome(String nome) throws Exception;

//    SubcategoriaJdbc buscarPorId(Long id) throws Exception;
}
