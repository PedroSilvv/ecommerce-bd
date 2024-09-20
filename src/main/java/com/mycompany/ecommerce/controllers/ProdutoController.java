package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.CadastrarProdutoRequestDTO;
import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.DAOs.custom.CustomProdutoDAO;
import com.mycompany.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CustomProdutoDAO customProdutoRepository;

    @Autowired
    ProdutoDAOImpl produtoDAO;


    @PostMapping("/auth/cadastrar-produto")
    public ResponseEntity<?> cadastrarProduto(@RequestBody CadastrarProdutoRequestDTO produto){

        try{

            Produto novoProduto = produtoService.cadastrarNovoProduto(produto);

            return ResponseEntity.ok(novoProduto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth/atualizar-produto/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable(value = "id") Long id,
                                              @RequestBody Produto requestDTO){


        try{
            Produto produto = produtoDAO.buscarPorId(id);

            produtoService.atualizarProduto(
                    requestDTO, id
            );

            return ResponseEntity.ok().body(produto);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<?> buscarProduto(@PathVariable(value = "id") Long id){
        try{
            Produto produto = produtoDAO.buscarPorId(id);
            return ResponseEntity.ok().body(produto);
        }catch (Exception e) {
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

    @GetMapping("/buscar-produto-por-termo/{termo}")
    public List<FiltrarProdutoResponseDTO> buscarProdutoPorTermo(@PathVariable(value = "termo") String termo) throws Exception {

        return customProdutoRepository.buscarPorTermo(termo);
    }

    @GetMapping("/filtrar-mais-vendidos")
    public ResponseEntity<?> filtrarMaisVendidos() throws Exception {
        List<Map<String, Object>> produtos = produtoDAO.findMostSelled();

        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("filtrar-mais-vendido-por-data/{datai}/{dataf}")
    public ResponseEntity<?> filtrarMaisVendidosPorData(@PathVariable(value = "datai") String dataI,
                                                        @PathVariable(value = "dataf") String dataF) throws Exception {

        LocalDate localDateI = LocalDate.parse(dataI);
        Date dataConvertidaI = Date.from(localDateI.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate localDateF = LocalDate.parse(dataF);
        Date dataConvertidaF = Date.from(localDateF.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Map<String, Object>> produtos = produtoDAO.findMostSelledByDate(dataConvertidaI, dataConvertidaF);
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/produtos-mais-avaliados")
    public ResponseEntity<?> produtosMaisAvaliados() throws Exception {
        List<Map<String, Object>> produtos = produtoDAO.findMostPopulars();
        return ResponseEntity.ok().body(produtos);

    }

}
