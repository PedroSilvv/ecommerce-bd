package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.jdbcModels.UsuarioJdbc;


import java.util.List;

public interface UsuarioDAO {

    List<UsuarioJdbc> buscarTodosUsuarios() throws Exception;

    UsuarioJdbc buscarPorId(String doc);

    void inserirUsuario(
            String doc,
            String nome,
            String role,
            String password
    );
}
