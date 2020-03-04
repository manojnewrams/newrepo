package com.example.h5api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class WinnerDto {
    private int id;
    private LocalDate createAt;
    private LocalDate updateAt;
    private LocalDate deleteAt;
    private ValueDtoIdName value;
    private UserDtoIdName user;
    private CampaignDtoIdDescription campaign;
    private int count;

    public WinnerDto() {
        this.createAt = LocalDate.now();
    }

    public WinnerDto(ValueDtoIdName value, UserDtoIdName user, CampaignDtoIdDescription campaign, int count) {
        this.createAt = LocalDate.now();
        this.updateAt = null;
        this.deleteAt = null;
        this.value = value;
        this.user = user;
        this.campaign = campaign;
        this.count = count;
    }
}
