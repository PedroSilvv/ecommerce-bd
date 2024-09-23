package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.CategoriaDAOImpl;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Subcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaDAOImpl categoriaDAO;


    public Categoria buscarSubcategoriaPorId(Long id) throws Exception {

        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new NotFoundException("categoria não encontrada com id: "+ id);
        }
        return categoria;
    }

    public List<Categoria> buscarTodasSubcategorias() throws Exception {
        return categoriaDAO.buscarTodos();
    }

    public void inserirSubcategoria(Categoria categoria) throws Exception {
        categoriaDAO.inserir(categoria);
    }

    public void atualizarSubcategoria(Categoria categoria ,Long id) throws Exception {

        Categoria categoriaAtualizada = categoriaDAO.buscarPorId(id);

        if (categoriaAtualizada == null) {
            throw new NotFoundException("categoria não encontrada com id: "+ id);
        }

        categoriaDAO.atualizar(categoria, id);
    }

    public void excluirSubcategoria(Long id) throws Exception {

        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new NotFoundException("categoria não encontrada com id: "+ id);
        }

        categoriaDAO.delete(categoria.getId());
    }

}
