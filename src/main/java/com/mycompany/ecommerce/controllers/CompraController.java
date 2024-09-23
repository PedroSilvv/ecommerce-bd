package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.DAOs.DAOsImpl.CompraDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CompraProdutoDAOImpl;
import com.mycompany.ecommerce.dtos.CompraResponseDTO;
import com.mycompany.ecommerce.dtos.EfetuarCompraRequestDTO;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.dtos.ProdutoRequestDTO;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
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
    ProdutoService produtoService;

    @Autowired
    CompraService compraService;

    @Autowired
    CompraDAOImpl compraDAO;

    @Autowired
    ProdutoDAOImpl produtoDAO;

    @Autowired
    CompraProdutoDAOImpl compraProdutoDAO;


    @PostMapping("/compra/efetuar-compra")
    public ResponseEntity<?> efetuarComprar(@RequestBody EfetuarCompraRequestDTO requestDTO){


        try{
            LocalDateTime localTime = LocalDateTime.now();
            Date localDate = Date.from(localTime.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dataCompra = java.sql.Date.valueOf(localTime.toLocalDate());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String dataFormatada = localTime.format(formatter);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String numeroAleatorio = uuid.substring(0, 6).toUpperCase();
            String notaFiscalFinal = "NF"+dataFormatada+numeroAleatorio;

            Compra novaCompra = new Compra();
            novaCompra.setUsuarioDoc(requestDTO.getUsuarioDoc());
            novaCompra.setStatus(Compra.Status.PENDENTE);
            novaCompra.setPrecoTotal(BigDecimal.ZERO);
            novaCompra.setDataCompra(localDate);
            novaCompra.setNotaFiscal(notaFiscalFinal);
            compraDAO.inserir(notaFiscalFinal, novaCompra.getUsuarioDoc(), novaCompra.getStatus().toString(), novaCompra.getPrecoTotal(), dataCompra);

            Double totalPrecoCompra = 0.0;

            for ( ProdutoRequestDTO produto : requestDTO.getProdutos() ) {

                Produto produtoComprado = produtoDAO.buscarPorId(produto.getProdutoId());

                if(produto.getQuantidadeComprada() > produtoComprado.getQuantidade()){
                    return ResponseEntity.badRequest().body("Quantidade não disponivel em estoque. Produto: "+ produtoComprado.getNome() +" - "+ produto.getProdutoId()) ;
                }

                CompraProduto compraProduto = new CompraProduto();
                compraProduto.setCompraNotaFiscal(novaCompra.getNotaFiscal());
                compraProduto.setProdutoId(produtoComprado.getId());
                compraProduto.setQuantidadeItem(produto.getQuantidadeComprada());
                compraProduto.setPrecoTotalItem(new BigDecimal(
                        produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada())
                        .setScale(2, RoundingMode.HALF_UP));

                compraProdutoDAO.inserir(
                        notaFiscalFinal, compraProduto.getProdutoId(), compraProduto.getQuantidadeItem(), compraProduto.getPrecoTotalItem());

                novaCompra.getCompraProdutos().add(compraProduto);

                totalPrecoCompra += produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada();

                Integer novaQuantidade = produtoComprado.getQuantidade() - produto.getQuantidadeComprada();
                produtoDAO.atualizarQuantidade(novaQuantidade, produtoComprado.getId());

                Integer novaQuantidadeDeVendas = produtoComprado.getQuantidadeVendas() + produto.getQuantidadeComprada();
                produtoDAO.atualizarQuantidadeDeVendas(novaQuantidadeDeVendas, produtoComprado.getId());

            }

            novaCompra.setPrecoTotal(new BigDecimal(totalPrecoCompra));
            compraDAO.atualizarPrecoTotal(novaCompra.getPrecoTotal(), notaFiscalFinal);

            return ResponseEntity.ok().body(novaCompra);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/compras/{doc}")
    public ResponseEntity<List<CompraResponseDTO>> comprarPorUsuario(@PathVariable("doc") String doc){

        try{
            List<CompraResponseDTO> compraList = compraService.compraPorUsuario(doc);
            return ResponseEntity.ok().body(compraList);
        }
        catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("auth/validar/{nota}")
    public ResponseEntity<?> validarCompra(@PathVariable("nota") String nota){
        try{

            compraService.confirmarCompra(nota);
            Compra compra = compraService.buscarCompraPorId(nota);

            return ResponseEntity.ok().body(compra);
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/compra/efetuar-compra")
//    public ResponseEntity<?> efetuarComprar(@RequestBody EfetuarCompraRequestDTO requestDTO) throws Exception {
//
//        try{
//            Compra novaCompra = compraService.efetuarCompra(requestDTO);
//            return ResponseEntity.ok().body(novaCompra);
//        }
//        catch (NotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//        catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }

}
