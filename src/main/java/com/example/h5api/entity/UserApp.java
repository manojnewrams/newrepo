package com.example.h5api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class UserApp extends BaseEntity {

    private String name;
    private String email;
    private String company;
    private String password;
    private Boolean role; //Admin true
    private Boolean status;

}
