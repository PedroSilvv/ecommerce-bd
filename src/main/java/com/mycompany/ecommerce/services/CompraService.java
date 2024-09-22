package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.UsuarioDAOImpl;
import com.mycompany.ecommerce.dtos.CompraProdutoResponseDTO;
import com.mycompany.ecommerce.dtos.CompraResponseDTO;
import com.mycompany.ecommerce.dtos.EfetuarCompraRequestDTO;
import com.mycompany.ecommerce.dtos.ProdutoRequestDTO;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.exceptions.UsuarioNotFound;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CompraDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CompraProdutoDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    CompraDAOImpl compraDAO;

    @Autowired
    CompraProdutoDAOImpl compraProdutoDAO;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    UsuarioService usuarioService;

    public Compra buscarCompraPorId(String notaFical) throws Exception {
        try{
            Compra compra = compraDAO.buscarPorId(notaFical);
            if (compra == null) {
                throw new NotFoundException("Compra não encontrado com nota fiscal: "+ notaFical);
            }

            return compra;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Compra> buscarTodasAsCompras() throws Exception {
        return compraDAO.buscarTodos();
    }

    public void inserirCompra(String notaFical, String usuarioDoc, String statusCompra, BigDecimal precoTotal, Date dataCompra) throws Exception {
        System.out.println("CompraService.inserirCompra");
        compraDAO.inserir(notaFical, usuarioDoc, statusCompra, precoTotal, dataCompra);
    }

    public List<CompraResponseDTO> compraPorUsuario(String docUsuario) throws Exception {

        Usuario usuario = usuarioService.findUserByDoc(docUsuario);
        if (usuario == null) {
            throw new UsuarioNotFound("Usuario não encontrado.");
        }

        List<Compra> listaCompras = compraDAO.buscarPorUsuario(docUsuario);
        List<CompraResponseDTO> comprasDTO = new ArrayList<>();

        for (Compra compra : listaCompras) {
            CompraResponseDTO compraDTO = new CompraResponseDTO();

            List<CompraProduto> compraProdutoList = compraProdutoDAO.buscarPorCompra(compra.getNotaFiscal());

            List<CompraProdutoResponseDTO> compraProdutoDTOList = compraProdutoList.stream()
                    .map(compraProduto -> {
                        try {
                            return new CompraProdutoResponseDTO(
                                    compraProduto.getProdutoId().intValue(),
                                    produtoService.buscarProdutoPorId(compraProduto.getProdutoId()).getNome(),
                                    compraProduto.getQuantidadeItem()
                            );
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());

            compraDTO.setNotaFiscal(compra.getNotaFiscal());
            compraDTO.setUsuarioDoc(usuario.getDoc());
            compraDTO.setProdutos(compraProdutoDTOList);
            compraDTO.setDataCompra(compra.getDataCompra());
            compraDTO.setPrecoTotal(compra.getPrecoTotal());

            comprasDTO.add(compraDTO);
        }

        return comprasDTO;
    }

    public Compra efetuarCompra(EfetuarCompraRequestDTO requestDTO) throws Exception {

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

                Produto produtoComprado = produtoService.buscarProdutoPorId(produto.getProdutoId());

                if(produto.getQuantidadeComprada() > produtoComprado.getQuantidade()){
                    throw new Exception("Quantidade não disponivel em estoque. Produto: "+ produtoComprado.getNome());
                }

                CompraProduto compraProduto = new CompraProduto();
                compraProduto.setCompraNotaFiscal(novaCompra.getNotaFiscal());
                compraProduto.setProdutoId(produtoComprado.getId());
                compraProduto.setQuantidadeItem(produto.getQuantidadeComprada());
                compraProduto.setPrecoTotalItem(new BigDecimal(
                        produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada())
                        .setScale(2, RoundingMode.HALF_UP));

                this.inserirCompraProduto(
                        notaFiscalFinal, compraProduto.getProdutoId(), compraProduto.getQuantidadeItem(), compraProduto.getPrecoTotalItem());

                novaCompra.getCompraProdutos().add(compraProduto);

                totalPrecoCompra += produtoComprado.getPreco().doubleValue() * produto.getQuantidadeComprada();

                Integer novaQuantidade = produtoComprado.getQuantidade() - produto.getQuantidadeComprada();
                produtoService.atualizarQuantidadeProduto(novaQuantidade, produtoComprado.getId());

                Integer novaQuantidadeDeVendas = produtoComprado.getQuantidadeVendas() + produto.getQuantidadeComprada();
                produtoService.atualizarQuantidadVendas(novaQuantidadeDeVendas, produtoComprado.getId());

            }

            novaCompra.setPrecoTotal(new BigDecimal(totalPrecoCompra));
            this.atualizarPrecoTotalCompra(novaCompra.getPrecoTotal(), notaFiscalFinal);

            return novaCompra;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public void inserirCompraProduto(String compraNotaFiscal, Long produtoId, Integer quantidadeItem, BigDecimal precoTotalItem) throws Exception {
        compraDAO.inserir(compraNotaFiscal, produtoId, quantidadeItem, produtoId);
    }

    public void atualizarPrecoTotalCompra(BigDecimal precoTotal, String notaFiscal) throws Exception{
        compraDAO.atualizarPrecoTotal(precoTotal, notaFiscal);
    }

    public void confirmarCompra(String notaFiscal) throws Exception {

        Compra compra = this.buscarCompraPorId(notaFiscal);

        if (compra == null) {
            throw new NotFoundException("Nota Fiscal não encontrada.");
        }

        compraDAO.alterarStatusCompra(notaFiscal, Compra.Status.CONCLUIDA.toString());

    }

}
