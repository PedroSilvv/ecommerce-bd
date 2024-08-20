package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
