package com.mycompany.ecommerce.exceptions;

public class ProdutoNotFound extends RuntimeException{
    public ProdutoNotFound(String message){
        super(message);
    }
}
