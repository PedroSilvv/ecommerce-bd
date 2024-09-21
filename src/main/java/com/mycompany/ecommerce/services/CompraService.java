package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.UsuarioDAOImpl;
import com.mycompany.ecommerce.dtos.CompraProdutoResponseDTO;
import com.mycompany.ecommerce.dtos.CompraResponseDTO;
import com.mycompany.ecommerce.exceptions.NotFoundException;
import com.mycompany.ecommerce.exceptions.UsuarioNotFound;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CompraDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.CompraProdutoDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    CompraDAOImpl compraDAO;

    @Autowired
    CompraProdutoDAOImpl compraProdutoDAO;

    @Autowired
    ProdutoDAOImpl produtoDAO;


    @Autowired
    UsuarioDAOImpl usuarioDAO;

//    public List<Compra> compraPorUsuario(String docUsuario) {
//        Usuario usuario = usuarioRepository.buscarPorId(docUsuario);
//        if (usuario == null) {
//            throw new UsuarioNotFound("Usuario não encontrado.");
//        }
//        List<Compra> listaCompras = compraRepository.findByUsuario(docUsuario);
//        return listaCompras;
//    }

    public List<CompraResponseDTO> compraPorUsuario(String docUsuario) throws Exception {
        Usuario usuario = usuarioDAO.buscarPorId(docUsuario);
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
                                    produtoDAO.buscarPorId(compraProduto.getProdutoId()).getNome(),
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

    public void confirmarCompra(String notaFiscal) throws Exception {

        Compra compra = compraDAO.buscarPorId(notaFiscal);

        if (compra == null) {
            throw new NotFoundException("Nota Fiscal não encontrada.");
        }

        compraDAO.alterarStatusCompra(notaFiscal, Compra.Status.CONCLUIDA.toString());

    }



}
