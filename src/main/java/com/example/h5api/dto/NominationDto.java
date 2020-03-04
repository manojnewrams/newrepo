package com.example.h5api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class NominationDto {

    private int id;
    private LocalDate createAt;
    private LocalDate updateAt;
    private LocalDate deleteAt;
    private int nominatorId;
    private UserDtoIdName user;
    private String description;
    private ValueDtoIdName value;

    public NominationDto() {
        this.createAt = LocalDate.now();
    }


}
