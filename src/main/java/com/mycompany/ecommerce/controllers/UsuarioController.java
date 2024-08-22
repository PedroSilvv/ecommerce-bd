package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.models.Usuario;
import com.mycompany.ecommerce.services.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> usuariosList(){
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



    @PostMapping("/criar-usuario")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario){

        try{
            usuarioService.createUser(
                    usuario.getDoc(), usuario.getNome(), usuario.getSingleRole(), usuario.getPassword()
            );

            return ResponseEntity.ok("Criado com sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
