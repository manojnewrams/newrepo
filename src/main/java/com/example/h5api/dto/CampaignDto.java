package com.example.h5api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CampaignDto {

    private int id;
    private LocalDate createAt;
    private LocalDate updateAt;
    private LocalDate deleteAt;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String description;
    private boolean status;

    public CampaignDto() {
        this.createAt = LocalDate.now();
    }

}
