package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.dtos.CadastrarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Subcategoria;
import com.mycompany.ecommerce.models.SubcategoriaProduto;
import com.mycompany.ecommerce.repositories.CategoriaRepository;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import com.mycompany.ecommerce.repositories.SubcategoriaProdutoRepository;
import com.mycompany.ecommerce.repositories.SubcategoriaRepository;
import com.mycompany.ecommerce.repositories.custom.CustomProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    SubcategoriaRepository subcategoriaRepository;

    @Autowired
    SubcategoriaProdutoRepository subcategoriaProdutoRepository;

    @Autowired
    CustomProdutoRepository customProdutoRepository;

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

    public Produto cadastrarNovoProduto(CadastrarProdutoRequestDTO produto) throws Exception {

        try{
            this.createProduto(
                    produto.getCategoria(), produto.getNome(),
                    produto.getDescricao(), produto.getQuantidade(),
                    produto.getPreco()
            );

            Long novoProdutoId = produtoRepository.findLastInsertId();
            Produto novoProduto = produtoRepository.findByIdProduto(novoProdutoId);

            System.out.println(produto.getSubcategorias());

            for (String sub : produto.getSubcategorias()) {
                SubcategoriaProduto subcategoriaProduto = new SubcategoriaProduto();

                Subcategoria subcategoria = subcategoriaRepository.findByNome(sub);

                if(subcategoria == null){
                    throw new Exception("Subcategoria inexistente");
                }

                System.out.println(novoProduto);
                System.out.println(subcategoria);

                subcategoriaProduto.setProduto(novoProduto);
                subcategoriaProduto.setSubcategoria(subcategoria);
                subcategoriaProdutoRepository.create(
                        subcategoriaProduto.getSubcategoria().getId(),
                        subcategoriaProduto.getProduto().getId());
            }

            return novoProduto;

        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<FiltrarProdutoResponseDTO> filtrarProdutos(String nome, String categoria, BigDecimal precoMinimo, BigDecimal precoMaximo, String descricao) throws Exception {

        try{
            List<FiltrarProdutoResponseDTO> produtos = customProdutoRepository.filtrarProdutos(
                    nome, categoria, precoMinimo, precoMaximo, descricao);

            return produtos;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public void atualizarQuantidadeProduto(Integer novoValor, Long id){
        produtoRepository.updateQuantidade(novoValor, id);
    }

}


