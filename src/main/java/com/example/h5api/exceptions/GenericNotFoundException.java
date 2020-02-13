package com.example.h5api.exceptions;

public class GenericNotFoundException extends RuntimeException {
    public GenericNotFoundException(int id){
        super("Not found id: "+id);
    }
}
