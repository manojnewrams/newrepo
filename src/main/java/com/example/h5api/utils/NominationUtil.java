package com.example.h5api.utils;

import com.example.h5api.dto.NominationDto;
import com.example.h5api.dto.NominationDtoWithoutDates;
import com.example.h5api.entity.Nomination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NominationUtil {

    private UserUtil userUtil;
    private ValueUtil valueUtil;

    @Autowired
    public NominationUtil(UserUtil userUtil, ValueUtil valueUtil){
        this.userUtil = userUtil;
        this.valueUtil = valueUtil;
    }

    public NominationDto transformFromNominationToNominationDto(Nomination nomination) {
        return NominationDto
                .builder()
                .id(nomination.getId())
                .createAt(nomination.getCreateAt())
                .updateAt(nomination.getUpdateAt())
                .deleteAt(nomination.getDeleteAt())
                .nominatorId(nomination.getNominatorId())
                .user(userUtil.transformFromUserAppToUserDtoIdName(nomination.getUser()))
                .description(nomination.getDescription())
                .value(valueUtil.transformFromValueToValueDtoIdName(nomination.getValue()))
                .build();
    }

    public NominationDtoWithoutDates transformFromNominationToNominationDtoWithoutDates(Nomination nomination) {
        return NominationDtoWithoutDates
                .builder()
                .id(nomination.getId())
                .nominatorId(nomination.getNominatorId())
                .user(userUtil.transformFromUserAppToUserDtoIdName(nomination.getUser()))
                .description(nomination.getDescription())
                .value(valueUtil.transformFromValueToValueDtoIdName(nomination.getValue()))
                .build();
    }


    public Nomination transformFromNominationDtoToNomination(NominationDto nominationDto) {
        return Nomination
                .builder()
                .createAt(nominationDto.getCreateAt())
                .updateAt(nominationDto.getUpdateAt())
                .deleteAt(nominationDto.getDeleteAt())
                .nominatorId(nominationDto.getNominatorId())
                .user(userUtil.transformFromUserDtoIdNameToUserApp(nominationDto.getUser()))
                .description(nominationDto.getDescription())
                .value(valueUtil.transformFromValueDtoIdNameToValue(nominationDto.getValue()))
                .build();
    }
}
