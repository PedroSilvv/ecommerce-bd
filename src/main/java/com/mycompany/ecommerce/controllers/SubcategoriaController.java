package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.models.Categoria;
import com.mycompany.ecommerce.models.Subcategoria;
import com.mycompany.ecommerce.services.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class SubcategoriaController {

    @Autowired
    SubcategoriaService subcategoriaService;

    @PostMapping(value = "/auth/cadastrar-subcategoria")
    public ResponseEntity<String> cadastrarSubcategoria(@RequestBody Subcategoria subcategoria) {

        try {
            subcategoriaService.inserirSubcategoria(subcategoria);
            return ResponseEntity.ok().body("Inserido com sucesso");
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/subcategorias")
    public ResponseEntity<List<Subcategoria>> listarTodasSubcategorias() {

        try {
            List<Subcategoria> categorias = subcategoriaService.buscarTodasSubcategorias();
            return ResponseEntity.ok().body(categorias);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/subcategoria/{id}")
    public ResponseEntity<Subcategoria> subcategoriaPorId(@PathVariable(value = "id") Long id) {

        try {
            Subcategoria subcategoria = subcategoriaService.buscarSubcategoriaPorId(id);
            return ResponseEntity.ok().body(subcategoria);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping(value = "/auth/atualizar-subcategoria/{id}")
    public ResponseEntity<?> atualizaSubcategoria(@PathVariable(value = "id") Long id, @RequestBody Subcategoria subcategoria) {

        try {
            subcategoriaService.atualizarSubcategoria(subcategoria ,id);
            Subcategoria subcategoriaAtualizada = subcategoriaService.buscarSubcategoriaPorId(id);
            return ResponseEntity.ok().body(subcategoriaAtualizada);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/auth/deletar-subcategoria/{id}")
    public ResponseEntity<?> deletarSubcategoria(@PathVariable(value = "id") Long id) {

        try {
            subcategoriaService.excluirSubcategoria(id);
            return ResponseEntity.ok().body("Deletada com sucesso!");
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/subcategorias-mais-vendidas/{datai}/{dataf}")
    public List<Map<String, Object>> subcategoriasMaisVendidas(@PathVariable(value = "datai") String dataI,
                                                               @PathVariable(value = "dataf") String dataF) throws Exception {


        LocalDate localDateI = LocalDate.parse(dataI);
        Date dataConvertidaI = Date.from(localDateI.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate localDateF = LocalDate.parse(dataF);
        Date dataConvertidaF = Date.from(localDateF.atStartOfDay(ZoneId.systemDefault()).toInstant());

        java.sql.Date sqlI = new java.sql.Date(dataConvertidaI.getTime());
        java.sql.Date sqlF = new java.sql.Date(dataConvertidaF.getTime());
        return subcategoriaService.buscarSubcategoriasComMaisVendas(sqlI, sqlF);
    }

}
