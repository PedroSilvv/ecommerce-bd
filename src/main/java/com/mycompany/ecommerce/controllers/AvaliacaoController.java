package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.AvaliarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.AvaliarProdutoResponseDTO;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.exceptions.OutOfRangeException;
import com.mycompany.ecommerce.services.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/avaliacao")
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @PostMapping(value = "/produto/{id}")
    public ResponseEntity<?> avaliarProduto(@PathVariable(value = "id") Long id,
                                                                    @RequestBody AvaliarProdutoRequestDTO dto) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String usuarioDoc = authentication.getName();
            AvaliarProdutoResponseDTO avaliacao = avaliacaoService.criarAvaliacao(id, usuarioDoc, dto.getNota());
            return ResponseEntity.ok(avaliacao);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (OutOfRangeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
