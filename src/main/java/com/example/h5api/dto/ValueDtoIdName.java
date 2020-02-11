package com.example.h5api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValueDtoIdName {

    private int id;
    private String description;
}
