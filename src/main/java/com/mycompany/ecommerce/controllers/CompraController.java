package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.EfetuarCompraRequestDTO;
import com.mycompany.ecommerce.dtos.ProdutoRequestDTO;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.repositories.CompraProdutoRepository;
import com.mycompany.ecommerce.repositories.CompraRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/api")
public class CompraController {


    @Autowired
    CompraRepository compraRepository;

    @Autowired
    CompraProdutoRepository compraProdutoRepository;



    @PostMapping("/compra/efetuar-compra")
    public ResponseEntity<?> efetuarComprar(@RequestBody EfetuarCompraRequestDTO requestDTO){


        try{
            Compra novaCompra = new Compra();
            novaCompra.setUsuario(requestDTO.getUsuario());
            novaCompra.setStatus(Compra.Status.PENDENTE);
            novaCompra.setPrecoTotal(BigDecimal.ZERO);
            //compraRepository.save(novaCompra);
            compraRepository.createCompra(novaCompra.getUsuario().getDoc(), novaCompra.getStatus().toString(), novaCompra.getPrecoTotal());

            Long idCompra = compraRepository.findLastInsertId();

            Double produtoTotalPreco = 0.0;

            for ( ProdutoRequestDTO produto : requestDTO.getProdutos() ) {

                CompraProduto compraProduto = new CompraProduto();
                compraProduto.setCompra(novaCompra);
                compraProduto.setProduto(produto.getProduto());
                compraProduto.setQuantidadeItem(produto.getQuantidadeComprada());

                compraProdutoRepository.createCompraProduto(idCompra,
                        compraProduto.getProduto().getId(), compraProduto.getQuantidadeItem());

                novaCompra.getCompraProdutos().add(compraProduto);
                produtoTotalPreco += produto.getProduto().getPreco().doubleValue() * produto.getQuantidadeComprada();
            }


            novaCompra.setPrecoTotal(new BigDecimal(produtoTotalPreco));
            compraRepository.updatePrecoTotal(novaCompra.getPrecoTotal(), idCompra);

            return ResponseEntity.ok().body(novaCompra);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
