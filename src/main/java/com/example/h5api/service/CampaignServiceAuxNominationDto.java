package com.example.h5api.service;

import com.example.h5api.dto.*;
import com.example.h5api.entity.Campaign;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.repository.NominationRepository;
import com.example.h5api.utils.CampaignUtil;
import com.example.h5api.utils.UserUtil;
import com.example.h5api.utils.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampaignServiceAuxNominationDto {

    private final  UserUtil userUtil;
    private final UserAppService userAppService;
    private final NominationRepository nominationRepository;
    private final ValueUtil valueUtil;
    private final ValueService valueService;
    private final CampaignUtil campaignUtil;
    private final CampaignService campaignService;
    private final WinnerService winnerService;

    @Autowired
    public CampaignServiceAuxNominationDto(UserUtil userUtil, UserAppService userAppService, NominationRepository nominationRepository, ValueUtil valueUtil, ValueService valueService, CampaignUtil campaignUtil, CampaignService campaignService, WinnerService winnerService) {
        this.userUtil = userUtil;
        this.userAppService = userAppService;
        this.nominationRepository = nominationRepository;
        this.valueUtil = valueUtil;
        this.valueService = valueService;
        this.campaignUtil = campaignUtil;
        this.campaignService = campaignService;
        this.winnerService = winnerService;
    }


    public List<NominationDtoCounterValueIdUserId> getNominationDtoCounterValueIdUserIds(List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds, List<CampaignDto> campaignListAsDTO) {
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<Map<String, Number>> list = nominationRepository.selectWinners(dateFrom, dateTo);
            if (list.isEmpty()) {
                throw new GenericEmptyListException();
            }
            list.forEach(item -> {
                nominationDtoCounterValueIdUserIds.add(new NominationDtoCounterValueIdUserId(item.get("counter").intValue(), item.get("value_id").intValue(), item.get("user_id").intValue()));
            });

            if (checkListContainAllValues(nominationDtoCounterValueIdUserIds)) {
                for (int i = 0; i < nominationDtoCounterValueIdUserIds.size(); i++) {
                    UserDtoIdName user = userUtil.transformFromUserDtoToUserDtoIdName(userAppService.findById(nominationDtoCounterValueIdUserIds.get(i).getUserId()));
                    ValueDtoIdName value = valueUtil.transformFromValueToValueDtoIdName(valueUtil.transformFromValueDtoToValue(valueService.findById(nominationDtoCounterValueIdUserIds.get(i).getValueId())));
                    CampaignDtoIdDescription campaign = campaignUtil.transformFromCampaignToCampaignDtoIdDescription(campaignUtil.transformFromCampaignDtoToCampaign(campaignService.findById(campaignListAsDTO.get(0).getId())));
                    int count = nominationDtoCounterValueIdUserIds.get(i).getCounter();
                    winnerService.save(new WinnerDto(value, user, campaign, count));
                }

                campaignService.disableCampaign(campaignListAsDTO.get(0).getId());
                campaignService.enableCampaign(campaignListAsDTO.get(0).getId() + 1);
                return nominationDtoCounterValueIdUserIds;
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }


    public List<NominationDtoCounterRepeat> getCounterRepeats(List<Campaign> campaignList) {
        List<NominationDtoCounterRepeat> counterRepeats = new ArrayList<>();
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(campaignUtil::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        if (campaignListAsDTO.isEmpty()) {
            throw new GenericEmptyListException();
        }
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<BigInteger> list = nominationRepository.findTie(dateFrom, dateTo);
            list.forEach(item -> {
                counterRepeats.add(new NominationDtoCounterRepeat(item.intValue()));
            });
            return counterRepeats;
        } else {
            return counterRepeats;
        }
    }


    public List<ValueDtoCountId> getValueDtoCountIds(List<CampaignDto> campaignListAsDTO) {
        if(campaignListAsDTO.isEmpty()){
            throw new GenericEmptyListException();
        }
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<Map<String, Number>> list = nominationRepository.nominationSummary(dateFrom, dateTo);
            List<ValueDtoCountId> valueDtoCountIds = new ArrayList<>();
            list.forEach(item -> {
                valueDtoCountIds.add(new ValueDtoCountId(item.get("counter").intValue(), item.get("valueid").intValue()));
            });
            return valueDtoCountIds;
        } else {
            return Collections.emptyList();
        }
    }

    private Boolean checkListContainAllValues(List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < nominationDtoCounterValueIdUserIds.size(); i++) {
            list.add(nominationDtoCounterValueIdUserIds.get(i).getValueId());
        }
        return list.size() == 5;
    }

}
