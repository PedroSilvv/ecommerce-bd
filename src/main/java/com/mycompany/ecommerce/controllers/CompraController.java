package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.EfetuarCompraRequestDTO;
import com.mycompany.ecommerce.dtos.ProdutoRequestDTO;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.repositories.CompraProdutoRepository;
import com.mycompany.ecommerce.repositories.CompraRepository;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import com.mycompany.ecommerce.services.ProdutoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

            String idCompra = compraRepository.getLastInsertedId();
            System.out.println("Nota fiscal gerada: " + idCompra);

            Double produtoTotalPreco = 0.0;

            for ( ProdutoRequestDTO produto : requestDTO.getProdutos() ) {

                Produto produtoComprado = produtoRepository.findByIdProduto(produto.getProduto_id());

                if(produto.getQuantidadeComprada() > produtoComprado.getQuantidade()){
                    return ResponseEntity.badRequest().body("Quantidade não disponivel em estoque");
                }

                CompraProduto compraProduto = new CompraProduto();
                compraProduto.setCompra(novaCompra);
                compraProduto.setProduto(produtoComprado);
                compraProduto.setQuantidadeItem(produto.getQuantidadeComprada());

                compraProdutoRepository.createCompraProduto(notaFiscalFinal,
                        compraProduto.getProduto().getId(), compraProduto.getQuantidadeItem());

                novaCompra.getCompraProdutos().add(compraProduto);
                produtoTotalPreco += produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada();

                Integer novaQuantidade = produtoComprado.getQuantidade() - produto.getQuantidadeComprada();
                produtoService.atualizarQuantidadeProduto(novaQuantidade, produtoComprado.getId());

                Integer novaQuantidadeDeVendas = produtoComprado.getQuantidadeVendas() + produto.getQuantidadeComprada();
                produtoService.atualizarQuantidadVendas(novaQuantidadeDeVendas, produtoComprado.getId());

//                produtoComprado.setQuantidade(produtoComprado.getQuantidade() - produto.getQuantidadeComprada());
//                produtoComprado.setNovaQuantidadeDeVendas(produtoComprado.getQuantidadeVendas() + produto.getQuantidadeComprada());
//                produtoRepository.save(produtoComprado);
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
