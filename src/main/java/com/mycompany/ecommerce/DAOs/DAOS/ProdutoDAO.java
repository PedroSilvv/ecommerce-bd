package com.mycompany.ecommerce.DAOs.DAOS;


import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.jdbcModels.ProdutoJdbc;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ProdutoDAO extends GenericDAO<ProdutoJdbc> {

    //CRUD
//    void inserirProduto(ProdutoJdbc produto) throws Exception;
//    void atualizarProduto(ProdutoJdbc produto, Long id) throws Exception;
//    void deletarProduto(Long id) throws Exception;
//    ProdutoJdbc buscarPorId(Long id) throws Exception;
//    List<ProdutoJdbc> buscarTodos() throws Exception;

    //Operações
    List<ProdutoJdbc> buscarPorCategoria(Long categoria) throws Exception;
    void atualizarQuantidade(Integer novaQuantidade, Long id) throws Exception;
    void atualizarQuantidadeDeVendas(Integer novaQuantidade, Long id) throws Exception;

    //Relatorios
    List<Map<String, Object>> findMostSelled() throws Exception;
    List<Map<String, Object>> findMostSelledByDate(Date dataI, Date dataF) throws Exception;
    List<Map<String, Object>> findMostPopulars() throws Exception;
}
