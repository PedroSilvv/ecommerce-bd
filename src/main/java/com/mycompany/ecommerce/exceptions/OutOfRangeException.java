package com.mycompany.ecommerce.exceptions;

public class OutOfRangeException extends RuntimeException{
    public OutOfRangeException(String message){
        super(message);
    }
}
