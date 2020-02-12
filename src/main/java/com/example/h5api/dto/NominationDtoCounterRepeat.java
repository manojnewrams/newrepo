package com.example.h5api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NominationDtoCounterRepeat {

    private int repeat;

    public NominationDtoCounterRepeat() {

    }
}


