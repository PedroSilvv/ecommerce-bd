package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.CompraResponseDTO;
import com.mycompany.ecommerce.dtos.EfetuarCompraRequestDTO;
import com.mycompany.ecommerce.dtos.ProdutoRequestDTO;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.repositories.CompraProdutoRepository;
import com.mycompany.ecommerce.repositories.CompraRepository;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import com.mycompany.ecommerce.services.CompraService;
import com.mycompany.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class CompraController {

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    CompraProdutoRepository compraProdutoRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    private CompraService compraService;

    @PostMapping("/compra/efetuar-compra")
    public ResponseEntity<?> efetuarComprar(@RequestBody EfetuarCompraRequestDTO requestDTO){


        try{
            LocalDateTime localTime = LocalDateTime.now();
            Date localDate = Date.from(localTime.atZone(ZoneId.systemDefault()).toInstant());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String dataFormatada = localTime.format(formatter);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String numeroAleatorio = uuid.substring(0, 6).toUpperCase();
            String notaFiscalFinal = "NF"+dataFormatada+numeroAleatorio;

            Compra novaCompra = new Compra();
            novaCompra.setUsuario(requestDTO.getUsuario());
            novaCompra.setStatus(Compra.Status.PENDENTE);
            novaCompra.setPrecoTotal(BigDecimal.ZERO);
            novaCompra.setDataCompra(localDate);
            novaCompra.setNotaFiscal(notaFiscalFinal);
            compraRepository.createCompra(notaFiscalFinal, novaCompra.getUsuario().getDoc(), novaCompra.getStatus().toString(), novaCompra.getPrecoTotal(), novaCompra.getDataCompra());

            Double totalPrecoCompra = 0.0;

            for ( ProdutoRequestDTO produto : requestDTO.getProdutos() ) {

                Produto produtoComprado = produtoRepository.findByIdProduto(produto.getProduto_id());

                if(produto.getQuantidadeComprada() > produtoComprado.getQuantidade()){
                    return ResponseEntity.badRequest().body("Quantidade não disponivel em estoque. Produto: "+ produtoComprado.getNome());
                }

                CompraProduto compraProduto = new CompraProduto();
                compraProduto.setCompra(novaCompra);
                compraProduto.setProduto(produtoComprado);
                compraProduto.setQuantidadeItem(produto.getQuantidadeComprada());
                compraProduto.setPrecoTotalItem(new BigDecimal(
                        produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada())
                        .setScale(2, RoundingMode.HALF_UP));

                compraProdutoRepository.createCompraProduto(notaFiscalFinal,
                        compraProduto.getProduto().getId(), compraProduto.getQuantidadeItem(), compraProduto.getPrecoTotalItem());

                novaCompra.getCompraProdutos().add(compraProduto);

                totalPrecoCompra += produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada();

                Integer novaQuantidade = produtoComprado.getQuantidade() - produto.getQuantidadeComprada();
                produtoService.atualizarQuantidadeProduto(novaQuantidade, produtoComprado.getId());

                Integer novaQuantidadeDeVendas = produtoComprado.getQuantidadeVendas() + produto.getQuantidadeComprada();
                produtoService.atualizarQuantidadVendas(novaQuantidadeDeVendas, produtoComprado.getId());

            }

            novaCompra.setPrecoTotal(new BigDecimal(totalPrecoCompra));
            compraRepository.updatePrecoTotal(novaCompra.getPrecoTotal(), notaFiscalFinal);

            return ResponseEntity.ok().body(novaCompra);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/compras/{doc}")
    public ResponseEntity<?> comprarPorUsuario(@PathVariable("doc") String doc){

        try{
            List<CompraResponseDTO> compraList = compraService.compraPorUsuario(doc);
            return ResponseEntity.ok().body(compraList);
        }
        catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
