package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.Subcategoria;

public interface SubcategoriaDAO extends GenericDAO<Subcategoria> {

    Subcategoria buscarPorNome(String nome) throws Exception;
}
