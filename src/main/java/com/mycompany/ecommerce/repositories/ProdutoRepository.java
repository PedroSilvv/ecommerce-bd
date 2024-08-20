package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
