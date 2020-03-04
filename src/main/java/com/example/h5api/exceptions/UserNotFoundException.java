package com.example.h5api.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User with id: " + id + " not found.");
    }
}
