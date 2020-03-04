package com.example.h5api.service;

import com.example.h5api.repository.CampaignRepository;
import com.example.h5api.repository.NominationRepository;
import com.example.h5api.dto.*;
import com.example.h5api.entity.Campaign;
import com.example.h5api.entity.Nomination;
import com.example.h5api.exceptions.CampaignIsClosedException;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import com.example.h5api.utils.NominationUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log
@Service
public class NominationService implements GenericService<NominationDto> {

    private final NominationRepository nominationRepository;
    private final NominationUtil nominationUtil;
    private final CampaignRepository campaignRepository;
    private final ValueService valueService;
    private final  UserAppService userAppService;

    @Autowired
    public NominationService(NominationRepository nominationRepository, NominationUtil nominationUtil, CampaignRepository campaignRepository, ValueService valueService, UserAppService userAppService) {
        this.nominationRepository = nominationRepository;
        this.nominationUtil = nominationUtil;
        this.campaignRepository = campaignRepository;
        this.valueService = valueService;
        this.userAppService = userAppService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<NominationDto> findAll() {
        List<Nomination> nominationList = new ArrayList<>();
        nominationRepository.findAll().forEach(nominationList::add);
        if (nominationList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<NominationDto> nominationListAsDto = nominationList.stream()
                .map(nominationUtil::transformFromNominationToNominationDto).collect(Collectors.toList());
        return nominationListAsDto;
    }


    @Override
    @Transactional(readOnly = true)
    public NominationDto findById(Integer id) {
        Nomination nomination = nominationRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return nominationUtil.transformFromNominationToNominationDto(nomination);
    }


    @Override
    @Transactional
    public NominationDto save(NominationDto nominationDto) {
        List<Campaign> campaignList = campaignRepository.getCampaignByDateNow(nominationDto.getCreateAt());
        if (campaignList.isEmpty()) {
            throw new CampaignIsClosedException();
        }
        Nomination n = nominationUtil.transformFromNominationDtoToNomination(nominationDto);
        nominationRepository.save(n);
        return nominationDto;
    }


    @Override
    @Transactional
    public void deleteById(Integer id) {
        Nomination nomination = nominationRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        nomination.setDeleteAt(LocalDate.now());
        nominationRepository.save(nomination);
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return nominationRepository.existsById(id);
    }


    public List<NominationDtoAdmin> showAdminNominations() {
        List<NominationDtoAdmin> adminList = new ArrayList<>();
        List<ValueDtoIdName> valueList = new ArrayList<>();
        valueList.addAll(valueService.findAllValueList());
        if (valueList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        NominationDtoDisplayData data = null;
        for (int i = 0; i < valueList.size(); i++) {
            List<UserDtoIdName> nominations = new ArrayList<>();
            nominations.addAll(userAppService.findAllUserList(i + 1));
            if (nominations.isEmpty()) {
                throw new GenericEmptyListException();
            }
            List<NominationDtoDisplayData> dataList = new ArrayList<>();

            for (int j = 0; j < nominations.size(); j++) {
                List<UserDtoIdName> nominators = new ArrayList<>();
                nominators.addAll(userAppService.findAllUserNominatorList(i + 1, nominations.get(j).getId()));
                if (nominators.isEmpty()) {
                    throw new GenericEmptyListException();
                }
                data = new NominationDtoDisplayData(nominations.get(j).getName(), nominations.get(j).getId(), nominators.size(), nominators);
                dataList.add(data);
            }

            NominationDtoAdmin obj = new NominationDtoAdmin(valueList.get(i).getId(), valueList.get(i).getDescription(), dataList);
            adminList.add(obj);
        }
        return adminList;

    }


    public List<NominationDtoWithoutDates> findAllWithoutDates() {
        List<Nomination> nominationList = new ArrayList<>();
        nominationRepository.findAll().forEach(nominationList::add);
        if (nominationList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<NominationDtoWithoutDates> nominationListAsDto = nominationList.stream()
                .map(nominationUtil::transformFromNominationToNominationDtoWithoutDates).collect(Collectors.toList());
        return nominationListAsDto;
    }



}
