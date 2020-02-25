package com.example.h5api.service;

import com.example.h5api.repository.CampaignRepository;
import com.example.h5api.repository.NominationRepository;
import com.example.h5api.repository.UserRepository;
import com.example.h5api.repository.ValueRepository;
import com.example.h5api.dto.*;
import com.example.h5api.entity.Campaign;
import com.example.h5api.entity.Nomination;
import com.example.h5api.entity.UserApp;
import com.example.h5api.exceptions.CampaignIsClosedException;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import com.example.h5api.utils.CampaignUtil;
import com.example.h5api.utils.NominationUtil;
import com.example.h5api.utils.UserUtil;
import com.example.h5api.utils.ValueUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Log
@Service
public class NominationService implements GenericService<NominationDto> {
    private final NominationRepository nominationDao;

    private final UserRepository userDao;

    private final ValueRepository valueDao;

    private final CampaignRepository campaignDao;

    private final WinnerService winnerService;

    private final CampaignService campaignService;

    private final UserAppService userAppService;

    private final ValueService valueService;

    private final NominationUtil nominationUtil;

    private final CampaignUtil campaignUtil;

    private final UserUtil userUtil;

    private final ValueUtil valueUtil;

    @Autowired
    public NominationService(NominationRepository nominationDao, UserRepository userDao, ValueRepository valueDao, CampaignRepository campaignDao, WinnerService winnerService, CampaignService campaignService, UserAppService userAppService, ValueService valueService, NominationUtil nominationUtil, CampaignUtil campaignUtil, ValueUtil valueUtil, UserUtil userUtil) {
        this.nominationDao = nominationDao;
        this.userDao = userDao;
        this.valueDao = valueDao;
        this.campaignDao = campaignDao;
        this.winnerService = winnerService;
        this.campaignService = campaignService;
        this.userAppService = userAppService;
        this.valueService = valueService;
        this.nominationUtil = nominationUtil;
        this.campaignUtil = campaignUtil;
        this.valueUtil = valueUtil;
        this.userUtil = userUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NominationDto> findAll() {
        List<Nomination> nominationList = new ArrayList<>();
        nominationDao.findAll().forEach(nominationList::add);
        if (nominationList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return nominationList.stream()
                .map(nominationUtil::transformFromNominationToNominationDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NominationDto findById(Integer id) {
        Nomination nomination = nominationDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return nominationUtil.transformFromNominationToNominationDto(nomination);
    }

    @Override
    @Transactional
    public NominationDto save(NominationDto nominationDto) {
        List<Campaign> campaignList = campaignDao.getCampaignByDateNow(nominationDto.getCreateAt());
        if (campaignList.isEmpty()) {
            throw new CampaignIsClosedException();
        }
        Nomination n = nominationUtil.transformFromNominationDtoToNomination(nominationDto);
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
                .map(campaignUtil::transformFromCampaignToCampaignDto)
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
                .map(campaignUtil::transformFromCampaignToCampaignDto)
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
                .map(campaignUtil::transformFromCampaignToCampaignDto)
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
                .map(campaignUtil::transformFromCampaignToCampaignDto)
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
        return userList.stream()
                .map(userUtil::transformFromUserAppToUserDtoIdName).collect(Collectors.toSet());
    }


    public List<UserDtoIdName> findAllUserNominatorList(Integer valueid, Integer userid) {
        Set<UserApp> userList = new HashSet<>();
        userDao.findUserNameAndIdForNominator(valueid, userid).forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return userList.stream()
                .map(userUtil::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
    }


    public List<ValueDtoIdName> findAllValueList() {
        List<ValueDtoIdName> valueList = new ArrayList<>();
        valueDao.findIdAndValue().forEach(valueList::add);
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
                .map(campaignUtil::transformFromCampaignToCampaignDto)
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
        return nominationList.stream()
                .map(nominationUtil::transformFromNominationToNominationDtoWithoutDates).collect(Collectors.toList());
    }
}
