package com.example.h5api.exceptions;

import com.example.h5api.entity.UserApp;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id){
        super("User with id: "+id+" not found.");
    }
}
