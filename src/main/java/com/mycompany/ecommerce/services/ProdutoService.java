package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.dtos.CadastrarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CategoriaDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaProdutoDAOImpl;
import com.mycompany.ecommerce.exceptions.ProdutoNotFound;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Subcategoria;
import com.mycompany.ecommerce.models.SubcategoriaProduto;
import com.mycompany.ecommerce.DAOs.custom.CustomProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    CustomProdutoDAO customProdutoRepository;

    @Autowired
    ProdutoDAOImpl produtoDAO;

    @Autowired
    SubcategoriaDAOImpl subcategoriaDAO;

    @Autowired
    SubcategoriaProdutoDAOImpl subcategoriaProdutoDAO;

    @Autowired
    CategoriaDAOImpl categoriaDAO;

    public void createProduto(Produto produto) throws Exception {
        produtoDAO.inserir(produto);
    }

    public Long inserirProdutoRetornoId(Categoria categoria, String nome, String descricao, Integer quantidade, BigDecimal preco) throws Exception {
        return customProdutoRepository.inserirProdutoReturningId(categoria.getId(), nome, descricao, quantidade, preco);
    }

    public List<Produto> findProdutoByCategoria(Long idCategoria) throws Exception {

        try{
            Categoria categoria = categoriaDAO.buscarPorId(idCategoria);

            if(categoria == null){
                throw new Exception("Categoria inexistente");
            }

            return produtoDAO.buscarPorCategoria(idCategoria);

        }
        catch (Exception e){
            throw new Exception("Categoria não encontrada: " + e.getMessage());
        }
    }


    public Produto cadastrarNovoProduto(CadastrarProdutoRequestDTO produto) throws Exception {

        try{
            System.out.println("ProdutoService.cadastrarNovoProduto");

            for (String sub : produto.getSubcategorias()) {
                Subcategoria subcategoria = subcategoriaDAO.buscarPorNome(sub);

                if(!subcategoria.getCategoriaId().equals(produto.getCategoria().getId())){
                    throw new Exception("Subcategoria não permitida.");
                }
            }

            Long idNovoProduto = this.inserirProdutoRetornoId(
                    produto.getCategoria(), produto.getNome(),
                    produto.getDescricao(), produto.getQuantidade(),
                    produto.getPreco()
            );
            System.out.println(idNovoProduto);
            Produto novoProduto = produtoDAO.buscarPorId(idNovoProduto);

            for (String sub : produto.getSubcategorias()) {
                SubcategoriaProduto subcategoriaProduto = new SubcategoriaProduto();

                Subcategoria subcategoria = subcategoriaDAO.buscarPorNome(sub);


                System.out.println(novoProduto);

                subcategoriaProduto.setProdutoId(novoProduto.getId());
                subcategoriaProduto.setSubcategoriaId(subcategoria.getId());
                System.out.println(subcategoria);
                subcategoriaProdutoDAO.inserir(
                        subcategoriaProduto);
            }

            return novoProduto;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
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

    public void atualizarQuantidadeProduto(Integer novaQuantidade, Long id) throws Exception {
        try{
            produtoDAO.atualizarQuantidade(novaQuantidade, id);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void atualizarProduto(Produto novoProduto, Long id) throws Exception {

        try{
            Produto produtoAtualizado = produtoDAO.buscarPorId(id);

            if(produtoAtualizado == null){
                throw new ProdutoNotFound("Produto não encontraod");
            }

            produtoDAO.atualizar(
                    novoProduto,
                    produtoAtualizado.getId()
                    );

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void atualizarQuantidadVendas(Integer novaQuantidade, Long id) throws Exception {

        try{
            produtoDAO.atualizarQuantidadeDeVendas(novaQuantidade, id);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }



}


