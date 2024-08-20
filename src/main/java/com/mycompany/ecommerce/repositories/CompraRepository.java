package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
