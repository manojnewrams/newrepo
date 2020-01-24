package com.example.h5api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class NominationDto {

    private int id;
    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private int nominatorId;
    private UserDtoIdName user;
    private String description;
    private ValueDtoIdName value;

    public NominationDto (){
        this.createAt = new Date();
    }



}
