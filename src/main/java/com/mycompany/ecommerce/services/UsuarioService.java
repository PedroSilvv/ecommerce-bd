package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findAllUsuarios(){
        //return usuarioRepository.findAll();
        return usuarioRepository.findAll();
    }

    public void createUser(String doc, String nome, Usuario.Role role, String password) throws Exception {

        try{
            usuarioRepository.createUser(
                    doc, nome, role.toString(), password
            );
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public Usuario findUserByDoc(String doc){

        return usuarioRepository.findByDoc(doc);

    }

}
