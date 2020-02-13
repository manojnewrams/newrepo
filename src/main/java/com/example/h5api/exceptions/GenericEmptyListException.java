package com.example.h5api.exceptions;

public class GenericEmptyListException extends RuntimeException{
    public GenericEmptyListException(){
        super("List is empty");
    }
}
