package com.example.h5api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NominationDtoCounterValueIdUserId {

    private int counter;
    private int valueId;
    private int userId;

    public NominationDtoCounterValueIdUserId() {

    }
}
