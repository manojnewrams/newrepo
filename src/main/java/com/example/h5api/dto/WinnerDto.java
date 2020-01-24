package com.example.h5api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class WinnerDto {
    private int id;
    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private ValueDtoIdName value;
    private UserDtoIdName user;
    private CampaignDtoIdDescription campaign;
    private int count;

    public WinnerDto (){
        this.createAt=new Date();
    }

    public WinnerDto(ValueDtoIdName value, UserDtoIdName user, CampaignDtoIdDescription campaign, int count) {
        this.createAt = new Date();
        this.updateAt = null;
        this.deleteAt = null;
        this.value = value;
        this.user = user;
        this.campaign = campaign;
        this.count = count;
    }
}
