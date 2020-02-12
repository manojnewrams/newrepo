package com.example.h5api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NominationDtoWithoutDates {
    private int id;
    private int nominatorId;
    private UserDtoIdName user;
    private String description;
    private ValueDtoIdName value;

    public NominationDtoWithoutDates() {

    }
}
