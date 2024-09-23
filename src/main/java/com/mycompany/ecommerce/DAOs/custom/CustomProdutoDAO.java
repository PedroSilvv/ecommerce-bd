package com.mycompany.ecommerce.DAOs.custom;

import com.mycompany.ecommerce.DAOs.DAOS.SubcategoriaDAO;
import com.mycompany.ecommerce.DAOs.DAOsImpl.ProdutoDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaDAOImpl;
import com.mycompany.ecommerce.DAOs.DAOsImpl.SubcategoriaProdutoDAOImpl;
import com.mycompany.ecommerce.dtos.FiltrarProdutoResponseDTO;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.SubcategoriaProduto;
import com.mycompany.ecommerce.services.SubcategoriaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomProdutoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SubcategoriaProdutoDAOImpl subcategoriaProdutoDAO;

    @Autowired
    ProdutoDAOImpl produtoDAO;

    @Autowired
    SubcategoriaDAOImpl subcategoriaDAO;


    public Long inserirProdutoReturningId(Long categoria, String nome, String descricao, Integer quantidade, BigDecimal preco) throws Exception {
        StringBuilder queryBuilder = new StringBuilder(
                "INSERT INTO produto (categoria_id, nome, descricao, quantidade, preco) " +
                "VALUES (:categoria, :nome, :descricao, :quantidade, :preco) " +
                "RETURNING id ");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("categoria", categoria);
        query.setParameter("nome", nome);
        query.setParameter("descricao", descricao);
        query.setParameter("quantidade", quantidade);
        query.setParameter("preco", preco);

        Object idProduto = query.getSingleResult();

        if (idProduto != null) {
            return ((Number) idProduto).longValue();
        } else {
            throw new Exception("Id retornado errado: "+idProduto);
        }

    }

    public List<FiltrarProdutoResponseDTO> filtrarProdutos(String nome, String categoria, BigDecimal precoMinimo, BigDecimal precoMaximo, String descricao) throws Exception {

        System.out.println("CustomProdutoDAO.filtrarProdutos");

        StringBuilder queryBuilder = new StringBuilder("SELECT p.nome, p.preco, p.quantidade, c.nome, p.descricao, p.id " +
                " FROM produto p JOIN categoria c ON p.categoria_id = c.id " +
                "WHERE 1=1 ");

        if (nome != null && !nome.isEmpty()) {
            queryBuilder.append("AND p.nome = :nome ");
        }
        if (categoria != null && !categoria.isEmpty()) {
            queryBuilder.append("AND c.nome = :categoria ");
        }
        if (precoMinimo != null) {
            queryBuilder.append("AND p.preco >= :precoMinimo ");
        }
        if (precoMaximo != null) {
            queryBuilder.append("AND p.preco <= :precoMaximo ");
        }
        if (descricao != null && !descricao.isEmpty()) {
            queryBuilder.append("AND p.descricao LIKE :descricao ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        // Define os parâmetros da query
        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", nome);
        }
        if (categoria != null && !categoria.isEmpty()) {
            query.setParameter("categoria", categoria);
        }
        if (precoMinimo != null) {
            query.setParameter("precoMinimo", precoMinimo);
        }
        if (precoMaximo != null) {
            query.setParameter("precoMaximo", precoMaximo);
        }
        if (descricao != null && !descricao.isEmpty()) {
            query.setParameter("descricao", "%" + descricao + "%");
        }

        List<Object[]> resultadoLista = query.getResultList();
        List<FiltrarProdutoResponseDTO> dtos = new ArrayList<>();

        for (Object[] o : resultadoLista) {
            String nomeProduto = (String) o[0];
            BigDecimal precoProduto = (BigDecimal) o[1];
            Integer quantidadeProduto = (Integer) o[2];
            String categoriaProduto = (String) o[3];
            String descricaoProduto = (String) o[4];
            Integer idProduto = (Integer) o[5];

            Produto produto = produtoDAO.buscarPorId(idProduto.longValue());
            List<SubcategoriaProduto> subcategorias = subcategoriaProdutoDAO.buscarPorProduto(produto.getId());

            List<String> subcategoriasNome = new ArrayList<>();
            for (SubcategoriaProduto s: subcategorias) {
                String sNome = subcategoriaDAO.buscarPorId(s.getSubcategoriaId()).getNome();
                subcategoriasNome.add(sNome);
            }

            dtos.add(new FiltrarProdutoResponseDTO(nomeProduto, precoProduto, quantidadeProduto, categoriaProduto, descricaoProduto, subcategoriasNome));
        }

        return dtos;
    }

    public List<FiltrarProdutoResponseDTO> buscarPorTermo(String termo) throws Exception {

        StringBuilder queryBuilder = new StringBuilder("SELECT p.nome, p.preco, p.quantidade, c.nome, p.descricao, p.id \n" +
                "FROM produto p\n" +
                "JOIN categoria c ON p.categoria_id = c.id \n" +
                "WHERE p.nome ILIKE :termo \n" +
                "OR p.descricao ILIKE :termo \n" +
                "OR p.marca ILIKE :termo \n" +
                "OR c.nome ILIKE :termo");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        if (termo != null && !termo.isEmpty()) {
            query.setParameter("termo", "%" + termo + "%");
        }

        System.out.println("debug1");
        List<Object[]> resultadoLista = query.getResultList();
        System.out.println("resultadoLista");
        List<FiltrarProdutoResponseDTO> dtos = new ArrayList<>();
        System.out.println("dtos");


        for (Object[] o : resultadoLista) {
            System.out.println("entrou for");
            String nomeProduto = (String) o[0];
            BigDecimal precoProduto = (BigDecimal) o[1];
            Integer quantidadeProduto = (Integer) o[2];
            String categoriaProduto = (String) o[3];
            String descricaoProduto = (String) o[4];
            Integer idProduto = (Integer) o[5];

            System.out.println("debug id produto1");

            Produto produto = produtoDAO.buscarPorId(idProduto.longValue());
            System.out.println("debug id 2");

            List<SubcategoriaProduto> subcategorias = subcategoriaProdutoDAO.buscarPorProduto(produto.getId());
            System.out.println("debug subcategrorias");

            List<String> subcategoriasNome = new ArrayList<>();
            for (SubcategoriaProduto s: subcategorias) {
                String sNome = subcategoriaDAO.buscarPorId(s.getSubcategoriaId()).getNome();
                subcategoriasNome.add(sNome);
                System.out.println("debug for subcategorias");

            }

            dtos.add(new FiltrarProdutoResponseDTO(nomeProduto, precoProduto, quantidadeProduto, categoriaProduto, descricaoProduto, subcategoriasNome));
        }
        System.out.println("debug fora for principal");

        return dtos;

    }
}
