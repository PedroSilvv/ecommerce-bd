package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.jdbcModels.SubcategoriaProdutoJdbc;

import java.util.List;

public interface SubcategoriaProdutoDAO extends GenericDAO<SubcategoriaProdutoJdbc> {

//    void inserirSubcategoriaProduto(SubcategoriaProdutoJdbc subcategoriaProdutoJdbc) throws Exception;
    List<SubcategoriaProdutoJdbc> buscarPorProduto(Long produtoId) throws Exception;
}
