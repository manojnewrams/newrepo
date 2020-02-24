package com.example.h5api.utils;

import com.example.h5api.dto.WinnerDto;
import com.example.h5api.dto.WinnerDtoWithoutDates;
import com.example.h5api.entity.Winner;
import org.springframework.stereotype.Component;

@Component
public class WinnerUtil {

    private ValueUtil valueUtil;
    private UserUtil userUtil;
    private CampaignUtil campaignUtil;

    public WinnerUtil(ValueUtil valueUtil, UserUtil userUtil, CampaignUtil campaignUtil) {
        this.valueUtil = valueUtil;
        this.userUtil = userUtil;
        this.campaignUtil = campaignUtil;
    }

    public Winner transformFromWinnerDtoToWinner(WinnerDto winnerDto) {
        return Winner.builder()
                .id(winnerDto.getId())
                .createAt(winnerDto.getCreateAt())
                .updateAt(winnerDto.getUpdateAt())
                .deleteAt(winnerDto.getDeleteAt())
                .value(valueUtil.transformFromValueDtoIdNameToValue(winnerDto.getValue()))
                .user(userUtil.transformFromUserDtoIdNameToUserApp(winnerDto.getUser()))
                .campaign(campaignUtil.transformFromCampaignDtoIdDescriptionToCampaign(winnerDto.getCampaign()))
                .count(winnerDto.getCount())
                .build();
    }

    public WinnerDto transformFromWinnerToWinnerDto(Winner winner) {
        return WinnerDto.builder()
                .id(winner.getId())
                .createAt(winner.getCreateAt())
                .updateAt(winner.getUpdateAt())
                .deleteAt(winner.getDeleteAt())
                .value(valueUtil.transformFromValueToValueDtoIdName(winner.getValue()))
                .user(userUtil.transformFromUserAppToUserDtoIdName(winner.getUser()))
                .campaign(campaignUtil.transformFromCampaignToCampaignDtoIdDescription(winner.getCampaign()))
                .count(winner.getCount())
                .build();
    }

    public WinnerDtoWithoutDates transformFromWinnerToWinnerDtoWithoutDates(Winner winner) {
        return WinnerDtoWithoutDates.builder()
                .id(winner.getId())
                .value(valueUtil.transformFromValueToValueDtoIdName(winner.getValue()))
                .user(userUtil.transformFromUserAppToUserDtoIdName(winner.getUser()))
                .campaign(campaignUtil.transformFromCampaignToCampaignDtoIdDescription(winner.getCampaign()))
                .count(winner.getCount())
                .build();
    }
}
