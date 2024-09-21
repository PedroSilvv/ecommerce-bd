package com.mycompany.ecommerce.services;

import com.mycompany.ecommerce.DAOs.DAOsImpl.UsuarioDAOImpl;
import com.mycompany.ecommerce.models.CustomUserDetails;
import com.mycompany.ecommerce.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDAOImpl usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String doc) throws UsernameNotFoundException {
        Usuario user = usuarioDAO.buscarPorId(doc);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o documento : " + doc);
        }
        return new CustomUserDetails(user);
    }

}
