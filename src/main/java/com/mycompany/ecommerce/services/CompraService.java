package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.dtos.CompraProdutoResponseDTO;
import com.mycompany.ecommerce.dtos.CompraResponseDTO;
import com.mycompany.ecommerce.exceptions.UsuarioNotFound;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.repositories.CompraProdutoRepository;
import com.mycompany.ecommerce.repositories.CompraRepository;
import com.mycompany.ecommerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    CompraProdutoRepository compraProdutoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


//    public List<Compra> compraPorUsuario(String docUsuario) {
//        Usuario usuario = usuarioRepository.findByDoc(docUsuario);
//        if (usuario == null) {
//            throw new UsuarioNotFound("Usuario não encontrado.");
//        }
//        List<Compra> listaCompras = compraRepository.findByUsuario(docUsuario);
//        return listaCompras;
//    }

    public List<CompraResponseDTO> compraPorUsuario(String docUsuario) {
        Usuario usuario = usuarioRepository.findByDoc(docUsuario);
        if (usuario == null) {
            throw new UsuarioNotFound("Usuario não encontrado.");
        }
        List<Compra> listaCompras = compraRepository.findByUsuario(docUsuario);
        List<CompraResponseDTO> comprasDTO = new ArrayList<>();

        for (Compra compra : listaCompras) {
            CompraResponseDTO compraDTO = new CompraResponseDTO();

            List<CompraProduto> compraProdutoList = compraProdutoRepository.findByCompra(compra.getNotaFiscal());

            List<CompraProdutoResponseDTO> compraProdutoDTOList = compraProdutoList.stream()
                    .map(compraProduto -> new CompraProdutoResponseDTO(
                            compraProduto.getProduto().getId().intValue(),
                            compraProduto.getProduto().getNome(),
                            compraProduto.getQuantidadeItem()
                    ))
                    .collect(Collectors.toList());

            compraDTO.setNotaFiscal(compra.getNotaFiscal());
            compraDTO.setUsuarioDoc(usuario.getDoc());
            compraDTO.setProdutos(compraProdutoDTOList);
            compraDTO.setDataCompra(compra.getDataCompra());
            comprasDTO.add(compraDTO);
        }

        return comprasDTO;
    }




}
