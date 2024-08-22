package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import com.mycompany.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping("/cadastrar-produto")
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto){

        try{
            produtoService.createProduto(
                    produto.getCategoria(), produto.getNome(),
                    produto.getDescricao(), produto.getQuantidade(),
                    produto.getPreco()
            );

            return ResponseEntity.ok("Criado com sucesso");

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


}
