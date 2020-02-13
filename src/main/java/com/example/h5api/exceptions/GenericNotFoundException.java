package com.example.h5api.exceptions;

public class GenericNotFoundException extends RuntimeException {
    public GenericNotFoundException(){
        super("Not found");
    }
}
