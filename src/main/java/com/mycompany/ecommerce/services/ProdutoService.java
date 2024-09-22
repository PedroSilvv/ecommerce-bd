package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.dtos.CadastrarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CategoriaDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaProdutoDAOImpl;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.exceptions.ProdutoNotFound;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Subcategoria;
import com.mycompany.ecommerce.models.SubcategoriaProduto;
import com.mycompany.ecommerce.DAOs.custom.CustomProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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



    public Produto buscarProdutoPorId(Long id) throws Exception {
        Produto produto = produtoDAO.buscarPorId(id);
        if(produto == null){
            throw new NotFoundException("Produto não encontrado com id: "+ id);
        }
        return produto;
    }

    public void createProduto(Produto produto) throws Exception {
        produtoDAO.inserir(produto);
    }

    public Long inserirProdutoRetornoId(Categoria categoria, String nome, String descricao, Integer quantidade, BigDecimal preco) throws Exception {
        return customProdutoRepository.inserirProdutoReturningId(categoria.getId(), nome, descricao, quantidade, preco);
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
            Produto novoProduto = this.buscarProdutoPorId(idNovoProduto);

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

    public List<Produto> buscarProdutoPorCategoria(Long idCategoria) throws Exception {

            Categoria categoria = categoriaDAO.buscarPorId(idCategoria);

            if(categoria == null){
                throw new NotFoundException("Categoria não encontrada com ID: " + idCategoria);
            }
            return produtoDAO.buscarPorCategoria(idCategoria);
    }


    // atualizacoes

    public void atualizarProduto(Produto novoProduto, Long id) throws Exception {

        Produto produtoAtualizado = this.buscarProdutoPorId(id);

        if(produtoAtualizado == null){
            throw new ProdutoNotFound("Produto não encontraod");
        }

        produtoDAO.atualizar(
                novoProduto,
                produtoAtualizado.getId()
                );
    }

    public void atualizarQuantidadeProduto(Integer novaQuantidade, Long id) throws Exception {

        Produto produto = this.buscarProdutoPorId(id);

        if(produto == null){
            throw new ProdutoNotFound("Produto não encontraod");
        }

        produtoDAO.atualizarQuantidade(novaQuantidade, id);
    }

    public void atualizarQuantidadVendas(Integer novaQuantidade, Long id) throws Exception {

        Produto produto = this.buscarProdutoPorId(id);

        if(produto == null){
            throw new ProdutoNotFound("Produto não encontraod");
        }

        produtoDAO.atualizarQuantidadeDeVendas(novaQuantidade, id);
    }

    // Buscar

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

    public List<FiltrarProdutoResponseDTO> buscarProdutoPorTermo(String termo) throws Exception {

        List<FiltrarProdutoResponseDTO> produtos = customProdutoRepository.buscarPorTermo(termo);

        return produtos;
    }


    //Relatorios

    public List<Map<String, Object>> maisVendidos() throws Exception{
        return produtoDAO.findMostSelled();
    }

    public List<Map<String, Object>> maisVendidosPorData(Date dataI, Date dataF) throws Exception{
        return produtoDAO.findMostSelledByDate(dataI, dataF);
    }

    public List<Map<String, Object>> maisPopulares() throws Exception{
        return produtoDAO.findMostPopulars();
    }


}


