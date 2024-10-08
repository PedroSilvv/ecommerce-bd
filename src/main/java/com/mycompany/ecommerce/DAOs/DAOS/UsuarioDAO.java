package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UsuarioDAO extends GenericDAO<Usuario> {

    //Relatorios

    List<Map<String, Object>> gastoMedioPorCliente() throws Exception;
}
