package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.CadastrarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.dtos.ProdutoRequestDTO;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Subcategoria;
import com.mycompany.ecommerce.models.SubcategoriaProduto;
import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import com.mycompany.ecommerce.repositories.SubcategoriaProdutoRepository;
import com.mycompany.ecommerce.repositories.SubcategoriaRepository;
import com.mycompany.ecommerce.repositories.custom.CustomProdutoRepository;
import com.mycompany.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Filter;

@RestController
@RequestMapping(value = "/api")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    SubcategoriaRepository subcategoriaRepository;

    @Autowired
    SubcategoriaProdutoRepository subcategoriaProdutoRepository;


    @PostMapping("/cadastrar-produto")
    public ResponseEntity<?> cadastrarProduto(@RequestBody CadastrarProdutoRequestDTO produto){

        try{

            Produto novoProduto = produtoService.cadastrarNovoProduto(produto);

            return ResponseEntity.ok(produto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/produtos/categoria/{id}")
    public ResponseEntity<?> buscarProdutoPorCategoria(@PathVariable(value = "id") Long id){
        try{
            List<Produto> produtos = produtoService.findProdutoByCategoria(id);

            return ResponseEntity.ok(produtos);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/filtrar-produtos")
    public ResponseEntity<List<FiltrarProdutoResponseDTO>> filtrarProdutos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precoMinimo,
            @RequestParam(required = false) BigDecimal precoMaximo,
            @RequestParam(required = false) String descricao) throws Exception {

        List<FiltrarProdutoResponseDTO> produtos = produtoService.filtrarProdutos(nome, categoria, precoMinimo, precoMaximo, descricao);
        return ResponseEntity.ok(produtos);
    }




}
