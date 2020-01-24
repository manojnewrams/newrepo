package com.example.h5api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueDtoWithoutDates {
    private int id;
    private String name;
    private String description;
}
