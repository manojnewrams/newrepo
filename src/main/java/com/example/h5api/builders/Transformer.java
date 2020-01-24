package com.example.h5api.builders;

import com.example.h5api.dto.*;
import com.example.h5api.entity.*;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

    public UserApp transformFromUserDtoToUserApp(UserDto userDTO) {
        return UserApp.builder()
                .createAt(userDTO.getCreateAt())
                .deleteAt(userDTO.getDeleteAt())
                .updateAt(userDTO.getUpdateAt())
                .name(userDTO.getName())
                .company(userDTO.getCompany())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .status(userDTO.getStatus())
                .role(userDTO.getRole())
                .build();
    }

    public UserDtoIdName transformFromUserDtotoUserDtoIdName(UserDto userDto){
        return UserDtoIdName.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();

    }

    public UserDto transformFromUserAppToUserDto(UserApp userApp) {
        return UserDto.builder()
                .id(userApp.getId())
                .createAt(userApp.getCreateAt())
                .deleteAt(userApp.getDeleteAt())
                .updateAt(userApp.getUpdateAt())
                .name(userApp.getName())
                .company(userApp.getCompany())
                .email(userApp.getEmail())
                .password(userApp.getPassword())
                .role(userApp.getRole())
                .status(userApp.getStatus())
                .build();
    }

    public UserDtoIdName transformFromUserAppToUserDtoIdName(UserApp userApp) {
        return UserDtoIdName.builder()
                .id(userApp.getId())
                .name(userApp.getName())
                .build();
    }

    public UserApp transformFromUserDtoIdNameToUserApp(UserDtoIdName userDtoIdName) {
        return UserApp.builder()
                .id(userDtoIdName.getId())
                .name(userDtoIdName.getName())
                .build();
    }

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

    public NominationDto transformFromNominationToNominationDto(Nomination nomination) {
        return NominationDto
                .builder()
                .id(nomination.getId())
                .createAt(nomination.getCreateAt())
                .updateAt(nomination.getUpdateAt())
                .deleteAt(nomination.getDeleteAt())
                .nominatorId(nomination.getNominatorId())
                .user(transformFromUserAppToUserDtoIdName(nomination.getUser()))
                .description(nomination.getDescription())
                .value(transformFromValueToValueDtoIdName(nomination.getValue()))
                .build();
    }

    public NominationDtoWithoutDates transformFromNominationToNominationDtoWithoutDates(Nomination nomination) {
        return NominationDtoWithoutDates
                .builder()
                .id(nomination.getId())
                .nominatorId(nomination.getNominatorId())
                .user(transformFromUserAppToUserDtoIdName(nomination.getUser()))
                .description(nomination.getDescription())
                .value(transformFromValueToValueDtoIdName(nomination.getValue()))
                .build();
    }


    public Nomination transformFromNominationDtoToNomination(NominationDto nominationDto) {
        return Nomination
                .builder()
                .createAt(nominationDto.getCreateAt())
                .updateAt(nominationDto.getUpdateAt())
                .deleteAt(nominationDto.getDeleteAt())
                .nominatorId(nominationDto.getNominatorId())
                .user(transformFromUserDtoIdNameToUserApp(nominationDto.getUser()))
                .description(nominationDto.getDescription())
                .value(transformFromValueDtoIdNameToValue(nominationDto.getValue()))
                .build();
    }

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

    public Winner transformFromWinnerDtoToWinner(WinnerDto winnerDto){
        return Winner.builder()
                .id(winnerDto.getId())
                .createAt(winnerDto.getCreateAt())
                .updateAt(winnerDto.getUpdateAt())
                .deleteAt(winnerDto.getDeleteAt())
                .value(transformFromValueDtoIdNameToValue(winnerDto.getValue()))
                .user(transformFromUserDtoIdNameToUserApp(winnerDto.getUser()))
                .campaign(transformFromCampaignDtoIdDescriptionToCampaign(winnerDto.getCampaign()))
                .count(winnerDto.getCount())
                .build();
    }

    public WinnerDto transformFromWinnerToWinnerDto(Winner winner){
        return WinnerDto.builder()
                .id(winner.getId())
                .createAt(winner.getCreateAt())
                .updateAt(winner.getUpdateAt())
                .deleteAt(winner.getDeleteAt())
                .value(transformFromValueToValueDtoIdName(winner.getValue()))
                .user(transformFromUserAppToUserDtoIdName(winner.getUser()))
                .campaign(transformFromCampaignToCampaignDtoIdDescription(winner.getCampaign()))
                .count(winner.getCount())
                .build();
    }

    public WinnerDtoWithoutDates transformFromWinnerToWinnerDtoWithoutDates(Winner winner){
        return WinnerDtoWithoutDates.builder()
                .id(winner.getId())
                .value(transformFromValueToValueDtoIdName(winner.getValue()))
                .user(transformFromUserAppToUserDtoIdName(winner.getUser()))
                .campaign(transformFromCampaignToCampaignDtoIdDescription(winner.getCampaign()))
                .count(winner.getCount())
                .build();
    }


}