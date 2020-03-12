package com.example.api.domain;

import java.util.List;

import lombok.Data;


import javax.validation.constraints.Email;

@Data
public class RegisterUser {

    private String name;
    @Email
    private String email;

    private String password;

    private List<Phone> phones;

}
