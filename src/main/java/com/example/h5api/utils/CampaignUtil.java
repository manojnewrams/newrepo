package com.example.h5api.utils;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.CampaignDtoIdDescription;
import com.example.h5api.entity.Campaign;
import org.springframework.stereotype.Component;

@Component
public class CampaignUtil {

    public Campaign transformFromCampaignDtoToCampaign(CampaignDto campaignDto) {
        return Campaign.builder()
                .id(campaignDto.getId())
                .createAt(campaignDto.getCreateAt())
                .updateAt(campaignDto.getUpdateAt())
                .deleteAt(campaignDto.getDeleteAt())
                .dateFrom(campaignDto.getDateFrom())
                .dateTo(campaignDto.getDateTo())
                .description(campaignDto.getDescription())
                .status(campaignDto.isStatus())
                .build();
    }

    public CampaignDto transformFromCampaignToCampaignDto(Campaign campaign) {
        return CampaignDto.builder()
                .id(campaign.getId())
                .createAt(campaign.getCreateAt())
                .updateAt(campaign.getUpdateAt())
                .deleteAt(campaign.getDeleteAt())
                .dateFrom(campaign.getDateFrom())
                .dateTo(campaign.getDateTo())
                .description(campaign.getDescription())
                .status(campaign.isStatus())
                .build();
    }

    public Campaign transformFromCampaignDtoIdDescriptionToCampaign(CampaignDtoIdDescription campaignDtoIdDescription) {
        return Campaign.builder()
                .id(campaignDtoIdDescription.getId())
                .description(campaignDtoIdDescription.getDescription())
                .build();

    }

    public CampaignDtoIdDescription transformFromCampaignToCampaignDtoIdDescription(Campaign campaign) {
        return CampaignDtoIdDescription.builder()
                .id(campaign.getId())
                .description(campaign.getDescription())
                .build();
    }

}
