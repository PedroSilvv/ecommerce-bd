package com.mycompany.ecommerce.repositories;

import com.mycompany.ecommerce.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query(value = "SELECT * FROM usuario ORDER BY nome ASC", nativeQuery = true)
    List<Usuario> findAll();

    @Query(value = "SELECT * FROM usuario WHERE doc = (:doc)", nativeQuery = true)
    Usuario findByDoc(@Param("doc") String doc);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuario VALUES (:doc, :nome, :user_role)", nativeQuery = true)
    void createUser(
            @Param("doc") String doc,
            @Param("nome") String nome,
            @Param("user_role") String role
    );

}
