package com.example.h5api.utils;

import com.example.h5api.dto.ValueDto;
import com.example.h5api.dto.ValueDtoIdName;
import com.example.h5api.dto.ValueDtoWithoutDates;
import com.example.h5api.entity.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueUtil {

    public Value transformFromValueDtoToValue(ValueDto valueDto) {
        return Value.builder()
                .id(valueDto.getId())
                .createAt(valueDto.getCreateAt())
                .updateAt(valueDto.getUpdateAt())
                .deleteAt(valueDto.getDeleteAt())
                .name(valueDto.getName())
                .description(valueDto.getDescription())
                .build();
    }

    public ValueDto transformFromValueToValueDto(Value value) {
        return ValueDto.builder()
                .id(value.getId())
                .createAt(value.getCreateAt())
                .updateAt(value.getUpdateAt())
                .deleteAt(value.getDeleteAt())
                .name(value.getName())
                .description(value.getDescription())
                .build();
    }

    public ValueDtoWithoutDates transformFromValueToValueDtoWithoutDates(Value value) {
        return ValueDtoWithoutDates.builder()
                .id(value.getId())
                .name(value.getName())
                .description(value.getDescription())
                .build();
    }


    public ValueDtoIdName transformFromValueToValueDtoIdName(Value value) {
        return ValueDtoIdName.builder()
                .id(value.getId())
                .description(value.getDescription())
                .build();
    }

    public Value transformFromValueDtoIdNameToValue(ValueDtoIdName valueDtoIdName) {
        return Value.builder()
                .id(valueDtoIdName.getId())
                .description(valueDtoIdName.getDescription())
                .build();
    }
}
