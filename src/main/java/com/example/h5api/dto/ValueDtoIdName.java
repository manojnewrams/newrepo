package com.example.h5api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValueDtoIdName {

    private int id;
    private String description;

}
