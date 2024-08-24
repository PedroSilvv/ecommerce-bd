package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {

    Subcategoria findByNome(String nome);

}
