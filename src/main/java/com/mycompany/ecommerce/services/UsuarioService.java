package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.UsuarioDAOImpl;
import com.mycompany.ecommerce.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    UsuarioDAOImpl usuarioDAO;

    public List<Usuario> findAllUsuarios() throws Exception {
        return usuarioDAO.buscarTodos();
    }

    public void inserirUsuario(String doc, String nome, Usuario.Role role, String password, LocalDate dataNasc) throws Exception {

        try{
            usuarioDAO.inserir(
                    doc, nome, role.toString(), password, dataNasc
            );
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public Usuario findUserByDoc(String doc){
        return usuarioDAO.buscarPorId(doc);

    }

    //relatorios

    public List<Map<String, Object>> gastoMedioPorCliente() throws Exception {
        return usuarioDAO.gastoMedioPorCliente();
    }

}
