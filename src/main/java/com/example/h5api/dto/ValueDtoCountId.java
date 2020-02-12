package com.example.h5api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValueDtoCountId {

    private int counter;
    private int valueId;

    public ValueDtoCountId(int counter, int valueId) {
        this.counter = counter;
        this.valueId = valueId;
    }

    public ValueDtoCountId() {

    }
}
