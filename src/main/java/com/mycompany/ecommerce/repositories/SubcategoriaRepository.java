package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {

    Subcategoria findByNome(String nome);
}
