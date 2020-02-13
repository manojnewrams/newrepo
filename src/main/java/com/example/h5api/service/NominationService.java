package com.example.h5api.service;

import com.example.h5api.builders.Transformer;
import com.example.h5api.dao.ICampaignDao;
import com.example.h5api.dao.INominationDao;
import com.example.h5api.dao.IUserAppDao;
import com.example.h5api.dao.IValueDao;
import com.example.h5api.dto.*;
import com.example.h5api.entity.Campaign;
import com.example.h5api.entity.Nomination;
import com.example.h5api.entity.UserApp;
import com.example.h5api.exceptions.CampaignIsClosedException;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Log
@Service
public class NominationService extends Transformer implements IGenericService<NominationDto> {
    @Autowired
    private INominationDao nominationDao;

    @Autowired
    private IUserAppDao userDao;


    @Autowired
    private IValueDao valueDao;

    @Autowired
    private ICampaignDao campaignDao;

    @Autowired
    private WinnerService winnerService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private ValueService valueService;

    @Override
    @Transactional(readOnly = true)
    public List<NominationDto> findAll() {
        List<Nomination> nominationList = new ArrayList<>();
        nominationDao.findAll().forEach(nominationList::add);
        if (nominationList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<NominationDto> nominationListAsDto = nominationList.stream()
                .map(this::transformFromNominationToNominationDto).collect(Collectors.toList());
        return nominationListAsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public NominationDto findById(Integer id) {
        Nomination nomination = nominationDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return transformFromNominationToNominationDto(nomination);
    }

    @Override
    @Transactional
    public NominationDto save(NominationDto nominationDto) {
        List<Campaign> campaignList = campaignDao.getCampaignByDateNow(nominationDto.getCreateAt());
        if (campaignList.isEmpty()) {
            throw new CampaignIsClosedException();
        }
        Nomination n = transformFromNominationDtoToNomination(nominationDto);
        nominationDao.save(n);
        return nominationDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Nomination nomination = nominationDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        nomination.setDeleteAt(new Date());
        nominationDao.save(nomination);
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return nominationDao.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<ValueDtoCountId> nominationSummary(Date date) {
        List<Campaign> campaignList = new ArrayList<>();
        List<ValueDtoCountId> empty = new ArrayList<>();
        campaignDao.getCampaignByDate(date).forEach(campaignList::add);
        if (campaignList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        return getValueDtoCountIds(campaignListAsDTO);
    }

    @Transactional(readOnly = true)
    public List<ValueDtoCountId> nominationSummary() {
        List<Campaign> campaignList = new ArrayList<>();
        List<ValueDtoCountId> empty = new ArrayList<>();
        campaignDao.getCampaignByDateNow(new Date()).forEach(campaignList::add);
        if (campaignList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        return getValueDtoCountIds(campaignListAsDTO);
    }

    @Transactional(readOnly = true)
    public List<NominationDtoCounterRepeat> counterRepeats() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getCampaignByDateNow(new Date()).forEach(campaignList::add);
        if (campaignList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return getCounterRepeats(campaignList);
    }

    @Transactional(readOnly = true)
    public List<NominationDtoCounterRepeat> counterRepeats(Date date) {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getCampaignByDateNow(date).forEach(campaignList::add);
        if (campaignList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return getCounterRepeats(campaignList);
    }

    public List<NominationDtoCounterValueIdUserId> drawWinnersOfQuarter() {
        List<Campaign> campaignList = new ArrayList<>();
        List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds = new ArrayList<>();
        campaignDao.getCampaignByDateNow(new Date()).forEach(campaignList::add);
        if (campaignList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        if (counterRepeats(new Date()).size() > 0) {
            return nominationDtoCounterValueIdUserIds;
        } else {
            return getNominationDtoCounterValueIdUserIds(nominationDtoCounterValueIdUserIds, campaignListAsDTO);
        }
    }

    public List<NominationDtoCounterValueIdUserId> drawWinnersOfQuarter(Date date) {
        List<Campaign> campaignList = new ArrayList<>();
        List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds = new ArrayList<>();
        campaignDao.getCampaignByDateNow(date).forEach(campaignList::add);
        if (campaignList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        if (counterRepeats(date).size() > 0) {
            return nominationDtoCounterValueIdUserIds;
        } else {
            return getNominationDtoCounterValueIdUserIds(nominationDtoCounterValueIdUserIds, campaignListAsDTO);
        }
    }


    public Set<UserDtoIdName> findAllUserList(Integer valueid) {
        List<UserApp> userList = new ArrayList<>();
        userDao.findUserNameAndId(valueid).forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        Set<UserDtoIdName> userDtoList = userList.stream()
                .map(this::transformFromUserAppToUserDtoIdName).collect(Collectors.toSet());
        return userDtoList;
    }


    public List<UserDtoIdName> findAllUserNominatorList(Integer valueid, Integer userid) {
        Set<UserApp> userList = new HashSet<>();
        userDao.findUserNameAndIdforNominator(valueid, userid).forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<UserDtoIdName> usernominationList = userList.stream()
                .map(this::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
        return usernominationList;
    }


    public List<ValueDtoIdName> findAllValueList() {
        List<ValueDtoIdName> valueList = new ArrayList<>();
        valueDao.findIdandValue().forEach(valueList::add);
        if (valueList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return valueList;
    }

    public List<NominationDtoAdmin> showAdminNominations() {
        List<NominationDtoAdmin> adminList = new ArrayList<>();
        List<ValueDtoIdName> valueList = new ArrayList<>();
        valueList.addAll(findAllValueList());
        if (valueList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        NominationDtoDisplayData data = null;
        for (int i = 0; i < valueList.size(); i++) {
            List<UserDtoIdName> nominations = new ArrayList<>();
            nominations.addAll(findAllUserList(i + 1));
            if (nominations.isEmpty()) {
                throw new GenericEmptyListException();
            }
            List<NominationDtoDisplayData> dataList = new ArrayList<>();

            for (int j = 0; j < nominations.size(); j++) {
                List<UserDtoIdName> nominators = new ArrayList<>();
                nominators.addAll(findAllUserNominatorList(i + 1, nominations.get(j).getId()));
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

    private List<NominationDtoCounterValueIdUserId> getNominationDtoCounterValueIdUserIds(List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds, List<CampaignDto> campaignListAsDTO) {
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<Map<String, Number>> list = nominationDao.selectWinners(dateFrom, dateTo);
            if (list.isEmpty()) {
                throw new GenericEmptyListException();
            }
            list.forEach(item -> {
                nominationDtoCounterValueIdUserIds.add(new NominationDtoCounterValueIdUserId(item.get("counter").intValue(), item.get("value_id").intValue(), item.get("user_id").intValue()));
            });

            if (checkListContainAllValues(nominationDtoCounterValueIdUserIds)) {
                for (int i = 0; i < nominationDtoCounterValueIdUserIds.size(); i++) {
                    UserDtoIdName user = transformFromUserDtotoUserDtoIdName(userAppService.findById(nominationDtoCounterValueIdUserIds.get(i).getUserId()));
                    ValueDtoIdName value = transformFromValueToValueDtoIdName(transformFromValueDtoToValue(valueService.findById(nominationDtoCounterValueIdUserIds.get(i).getValueId())));
                    CampaignDtoIdDescription campaign = transformFromCampaignToCampaignDtoIdDescription(transformFromCampaignDtoToCampaign(campaignService.findById(campaignListAsDTO.get(0).getId())));
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

    private Boolean checkListContainAllValues(List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < nominationDtoCounterValueIdUserIds.size(); i++) {
            list.add(nominationDtoCounterValueIdUserIds.get(i).getValueId());
        }
        if (list.size() == 5) {
            return true;
        } else {
            return false;
        }
    }


    private List<NominationDtoCounterRepeat> getCounterRepeats(List<Campaign> campaignList) {
        List<NominationDtoCounterRepeat> counterRepeats = new ArrayList<>();
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        if (campaignListAsDTO.isEmpty()) {
            throw new GenericEmptyListException();
        }
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<BigInteger> list = nominationDao.findTie(dateFrom, dateTo);
            list.forEach(item -> {
                counterRepeats.add(new NominationDtoCounterRepeat(item.intValue()));
            });
            return counterRepeats;
        } else {
            return counterRepeats;
        }
    }


    private List<ValueDtoCountId> getValueDtoCountIds(List<CampaignDto> campaignListAsDTO) {
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<Map<String, Number>> list = nominationDao.nominationSummary(dateFrom, dateTo);
            List<ValueDtoCountId> valueDtoCountIds = new ArrayList<>();
            list.forEach(item -> {
                valueDtoCountIds.add(new ValueDtoCountId(item.get("counter").intValue(), item.get("valueid").intValue()));
            });
            return valueDtoCountIds;
        } else {
            return Collections.emptyList();
        }
    }


    public List<NominationDtoWithoutDates> findAllWithoutDates() {
        List<Nomination> nominationList = new ArrayList<>();
        nominationDao.findAll().forEach(nominationList::add);
        if (nominationList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<NominationDtoWithoutDates> nominationListAsDto = nominationList.stream()
                .map(this::transformFromNominationToNominationDtoWithoutDates).collect(Collectors.toList());
        return nominationListAsDto;
    }
}
