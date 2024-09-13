package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.AvaliarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.AvaliarProdutoResponseDTO;
import com.mycompany.ecommerce.models.Avaliacao;
import com.mycompany.ecommerce.services.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @PostMapping(value = "/avaliar/produto/{id}")
    public ResponseEntity<AvaliarProdutoResponseDTO> avaliarProduto(@PathVariable(value = "id") Long id,
                                                                    @RequestBody AvaliarProdutoRequestDTO dto) {

        AvaliarProdutoResponseDTO avaliacao = avaliacaoService.criarAvaliacao(id, dto.getUsuarioDoc(), dto.getNota());
        return ResponseEntity.ok(avaliacao);
    }

}
