package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.Subcategoria;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface SubcategoriaDAO extends GenericDAO<Subcategoria> {

    Subcategoria buscarPorNome(String nome) throws Exception;

    List<Subcategoria> buscarSubcategoriasPorCategoria(Long categoriaId) throws Exception;

    List<Map<String, Object>> buscarSubcategoriasComMaisVendas(Date dataI, Date dataF) throws Exception;
}