package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.repositories.CategoriaRepository;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    public void createProduto(Categoria categoria, String nome, String descricao, Integer quantidade, BigDecimal preco) {
        produtoRepository.createProduto(categoria.getId(), nome, descricao, quantidade, preco);
    }

    public List<Produto> findProdutoByCategoria(Long idCategoria) throws Exception {

        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);

        if(categoria.isEmpty()){
            throw new Exception("Categoria inexistente");
        }

        return produtoRepository.findByCategoria(idCategoria);
    }

}
