package com.example.h5api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ValueDto {
    private int id;
    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private String name;
    private String description;

    public ValueDto (){
        this.createAt = new Date();
    }
}
