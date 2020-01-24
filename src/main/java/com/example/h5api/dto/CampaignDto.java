package com.example.h5api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CampaignDto {

    private int id;
    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private Date dateFrom;
    private Date dateTo;
    private String description;
    private boolean status;

    public  CampaignDto (){
        this.createAt=new Date();
    }

}
