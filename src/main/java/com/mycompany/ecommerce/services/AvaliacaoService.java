package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.dtos.AvaliarProdutoResponseDTO;
import com.mycompany.ecommerce.exceptions.AvaliacaoExistenteException;
import com.mycompany.ecommerce.exceptions.OutOfRangeException;
import com.mycompany.ecommerce.exceptions.ProdutoNotFound;
import com.mycompany.ecommerce.exceptions.UsuarioNotFound;
import com.mycompany.ecommerce.models.Avaliacao;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.repositories.AvaliacaoRepository;
import com.mycompany.ecommerce.repositories.ProdutoRepository;
import com.mycompany.ecommerce.repositories.UsuarioRepository;
import com.mycompany.ecommerce.repositories.custom.CustomAvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CustomAvaliacaoRepository customAvaliacaoRepository;

    public Long inserirAvaliacaoReturningId(Long produtoId, String doc, Integer nota) {
        return customAvaliacaoRepository.inserirAvaliacaoReturningId(produtoId, doc, nota);
    }

    private void validarNota(Integer nota) {
        if (nota < 0 || nota > 5) {
            throw new OutOfRangeException("Nota precisa ser entre 0 e 5.");
        }
    }

    public boolean verificarExistenciaDeAvaliacao(String doc, Long produtoId){
        return avaliacaoRepository.existsByProdutoAndUserDoc(doc, produtoId);
    }

    public AvaliarProdutoResponseDTO criarAvaliacao(Long produtoId, String doc, Integer nota) {

        validarNota(nota);

        Produto produto = produtoRepository.findByIdProduto(produtoId);
        Usuario usuario = usuarioService.findUserByDoc(doc);

        if (usuario == null) {
            throw new UsuarioNotFound("Usuario não encontrado");
        }

        if (produto == null) {
            throw new ProdutoNotFound("Produto não encontrado");
        }

        boolean avaliacaoExiste = this.verificarExistenciaDeAvaliacao(usuario.getDoc(), produto.getId());

        if(avaliacaoExiste){
            throw new AvaliacaoExistenteException("Usuario com avaliação ja registrada para produto: "+ produto.getNome());
        }

        Long idAvaliacao = this.inserirAvaliacaoReturningId(
                produto.getId(), usuario.getDoc(), nota
        );

        Avaliacao avaliacao = avaliacaoRepository.findByIdAvaliacao(idAvaliacao);

        AvaliarProdutoResponseDTO response = new AvaliarProdutoResponseDTO(
                idAvaliacao, produto, usuario.getDoc(), nota, avaliacao.getDataAvaliacao()
        );

        return response;
    }

}
