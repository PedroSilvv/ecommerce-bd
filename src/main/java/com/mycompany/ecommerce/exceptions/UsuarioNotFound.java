package com.mycompany.ecommerce.exceptions;



public class UsuarioNotFound extends RuntimeException{

    public UsuarioNotFound(String message){
        super(message);
    }
}

