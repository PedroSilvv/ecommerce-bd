package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.dtos.CriarUsuarioDTO;
import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> usuariosList() throws Exception {
        return ResponseEntity.ok().body(usuarioService.findAllUsuarios());
    }

    @GetMapping("/usuario/doc/{doc}")
    public ResponseEntity<?> buscarUsuarioPorDoc(@PathVariable(value = "doc") String doc){
        try{
            Usuario usuario = usuarioService.findUserByDoc(doc);

            return ResponseEntity.ok(usuario);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<?> criarUsuario(@RequestBody CriarUsuarioDTO usuario){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

            String senhaCript = passwordEncoder.encode(usuario.getPassword());

            Usuario.Role role = isAdmin ? Usuario.Role.ADMIN : Usuario.Role.DEFAULT;
            usuarioService.inserirUsuario(
                    usuario.getDoc(),
                    usuario.getNome(),
                    role,
                    senhaCript
            );

            return ResponseEntity.ok("Criado com sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
