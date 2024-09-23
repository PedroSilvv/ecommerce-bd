package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaDAOImpl;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.models.Subcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoriaService {

    @Autowired
    SubcategoriaDAOImpl subcategoriaDAO;


    public Subcategoria buscarSubcategoriaPorNome(String nome) throws Exception {

        Subcategoria subcategoria = subcategoriaDAO.buscarPorNome(nome);

        if (subcategoria == null) {
            throw new NotFoundException("Subcategoria não encontrada com nome: "+ nome);
        }

        return subcategoria;
    }

    public Subcategoria buscarSubcategoriaPorId(Long id) throws Exception {

        Subcategoria subcategoria = subcategoriaDAO.buscarPorId(id);

        if (subcategoria == null) {
            throw new NotFoundException("Subcategoria não encontrada com id: "+ id);
        }
        return subcategoria;
    }

    public List<Subcategoria> buscarTodasSubcategorias() throws Exception {
        return subcategoriaDAO.buscarTodos();
    }

    public void inserirSubcategoria(Subcategoria subcategoria) throws Exception {
        subcategoriaDAO.inserir(subcategoria);
    }

    public void atualizarSubcategoria(Subcategoria subcategoria, Long id) throws Exception {

        Subcategoria subcategoriaAtualizada = subcategoriaDAO.buscarPorId(id);

        if (subcategoriaAtualizada == null) {
            throw new NotFoundException("Subcategoria não encontrada com id: "+ id);
        }

        subcategoriaDAO.atualizar(subcategoria, id);
    }

    public void excluirSubcategoria(Long id) throws Exception {

        Subcategoria subcategoria = subcategoriaDAO.buscarPorId(id);

        if (subcategoria == null) {
            throw new NotFoundException("Subcategoria não encontrada com id: "+ id);
        }

        subcategoriaDAO.delete(subcategoria.getId());
    }

}
