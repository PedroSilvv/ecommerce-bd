package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.CategoriaDAOImpl;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaDAOImpl categoriaDAO;


    public Categoria buscarCategoriaPorId(Long id) throws Exception {

        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new NotFoundException("categoria não encontrada com id: "+ id);
        }
        return categoria;
    }

    public List<Categoria> buscarTodasCategorias() throws Exception {
        return categoriaDAO.buscarTodos();
    }

    public void inserirCategoria(Categoria categoria) throws Exception {
        categoriaDAO.inserir(categoria);
    }

    public void atualizarCategoria(Categoria categoria , Long id) throws Exception {

        Categoria categoriaAtualizada = this.buscarCategoriaPorId(id);
        categoriaDAO.atualizar(categoria, id);
    }

    public void excluirCategoria(Long id) throws Exception {

        Categoria categoria = this.buscarCategoriaPorId(id);
        categoriaDAO.delete(categoria.getId());
    }

}
