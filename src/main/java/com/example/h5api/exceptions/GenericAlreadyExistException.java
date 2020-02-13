package com.example.h5api.exceptions;

public class GenericAlreadyExistException extends RuntimeException {
    public GenericAlreadyExistException(int id){
        super("Already exist id:"+id);
    }
}
