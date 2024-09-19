package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.SubcategoriaProduto;

import java.util.List;

public interface SubcategoriaProdutoDAO extends GenericDAO<SubcategoriaProduto> {

//    void inserirSubcategoriaProduto(SubcategoriaProdutoJdbc subcategoriaProdutoJdbc) throws Exception;
    List<SubcategoriaProduto> buscarPorProduto(Long produtoId) throws Exception;
}
