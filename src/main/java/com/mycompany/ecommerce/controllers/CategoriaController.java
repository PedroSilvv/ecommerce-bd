package com.mycompany.ecommerce.controllers;


import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @PostMapping(value = "/auth/cadastrar-categoria")
    public ResponseEntity<String> cadastrarCategoria(@RequestBody Categoria categoria) {

        try {
            categoriaService.inserirSubcategoria(categoria);
            return ResponseEntity.ok().body("Inserido com sucesso");
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/categorias")
    public ResponseEntity<List<Categoria>> listarTodasCategorias() {

        try {
            List<Categoria> categorias = categoriaService.buscarTodasSubcategorias();
            return ResponseEntity.ok().body(categorias);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/categoria/{id}")
    public ResponseEntity<Categoria> categoriaPorId(@PathVariable(value = "id") Long id) {

        try {
            Categoria categoria = categoriaService.buscarSubcategoriaPorId(id);
            return ResponseEntity.ok().body(categoria);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping(value = "/auth/atualizar-categoria/{id}")
    public ResponseEntity<Categoria> atualizaCategoria(@PathVariable(value = "id") Long id, @RequestBody Categoria categoria) {

        try {
            categoriaService.atualizarSubcategoria(categoria ,id);
            Categoria categoriaAtualizada = categoriaService.buscarSubcategoriaPorId(id);
            return ResponseEntity.ok().body(categoriaAtualizada);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/auth/deletar-categoria/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable(value = "id") Long id) {

        try {
            categoriaService.excluirSubcategoria(id);
            return ResponseEntity.ok().body("Deletada com sucesso!");
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
