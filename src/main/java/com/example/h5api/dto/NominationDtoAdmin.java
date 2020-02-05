package com.example.h5api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NominationDtoAdmin {
    private int valueId;
    private String valueName;
    private List<NominationDtoDisplayData> nominations;

    public NominationDtoAdmin (int valueId, String valueName, List<NominationDtoDisplayData> nominations){
        this.valueId = valueId;
        this.valueName = valueName;
        this.nominations = nominations;
    }
}
