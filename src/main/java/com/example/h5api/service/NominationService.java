package com.example.h5api.service;

import com.example.h5api.builders.Transformer;
import com.example.h5api.dao.ICampaignDao;
import com.example.h5api.dao.INominationDao;
import com.example.h5api.dao.IUserAppDao;
import com.example.h5api.dto.*;
import com.example.h5api.entity.Campaign;
import com.example.h5api.entity.Nomination;
import com.example.h5api.entity.UserApp;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.element.Element;
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
        List<NominationDto> nominationListAsDto = nominationList.stream()
                .map(this::transformFromNominationToNominationDto).collect(Collectors.toList());
        return nominationListAsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public NominationDto findById(Integer id) {
        Nomination nomination = nominationDao.findById(id).orElse(null);
        return transformFromNominationToNominationDto(nomination);
    }

    @Override
    @Transactional
    public NominationDto save(NominationDto nominationDto) {
        Nomination n = transformFromNominationDtoToNomination(nominationDto);
        nominationDao.save(n);
        return nominationDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Nomination nomination = nominationDao.findById(id).orElse(null);
        if (nomination != null) {
            nomination.setDeleteAt(new Date());
            nominationDao.save(nomination);
        }
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
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto)
                .collect(Collectors.toList());
        return getValueDtoCountIds(campaignListAsDTO);
    }

    @Transactional(readOnly = true)
    public List<NominationDtoCounterRepeat> counterRepeats() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getCampaignByDateNow(new Date()).forEach(campaignList::add);
        return getCounterRepeats(campaignList);
    }

    @Transactional(readOnly = true)
    public List<NominationDtoCounterRepeat> counterRepeats(Date date) {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getCampaignByDateNow(date).forEach(campaignList::add);
        return getCounterRepeats(campaignList);
    }

    public List<NominationDtoCounterValueIdUserId> drawWinnersOfQuarter() {
        List<Campaign> campaignList = new ArrayList<>();
        List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds = new ArrayList<>();
        campaignDao.getCampaignByDateNow(new Date()).forEach(campaignList::add);
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
        Set<UserDtoIdName> userDtoList = userList.stream()
                .map(this::transformFromUserAppToUserDtoIdName).collect(Collectors.toSet());
        return userDtoList;
    }


    public List<UserDtoIdName> findAllUserNominatorList(Integer valueid,Integer userid) {
        Set<UserApp> userList = new HashSet<>();
        userDao.findUserNameAndIdforNominator(valueid,userid).forEach(userList::add);
        List<UserDtoIdName> usernominationList = userList.stream()
                .map(this::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
        return usernominationList;
    }



    public List<NominationDtoAdmin> showAdminNominations(){
        List<NominationDtoAdmin>adminList = new ArrayList<>();
        List<NominationDtoDisplayData> dataList1 = new ArrayList<>();
        List<NominationDtoDisplayData> dataList2 = new ArrayList<>();
        List<NominationDtoDisplayData> dataList3 = new ArrayList<>();
        List<NominationDtoDisplayData> dataList4 = new ArrayList<>();
        List<NominationDtoDisplayData> dataList5 = new ArrayList<>();

        List<UserDtoIdName> nominations1 = new ArrayList<>();
        nominations1.addAll(findAllUserList(1));
        List<UserDtoIdName> nominations2 = new ArrayList<>();
        nominations2.addAll(findAllUserList(2));
        List<UserDtoIdName> nominations3 = new ArrayList<>();
        nominations3.addAll(findAllUserList(3));
        List<UserDtoIdName> nominations4 = new ArrayList<>();
        nominations4.addAll(findAllUserList(4));
        List<UserDtoIdName> nominations5 = new ArrayList<>();
        nominations5.addAll(findAllUserList(5));

        NominationDtoDisplayData data = null;

       for(int i=0;i<nominations1.size();i++){
           List<UserDtoIdName> nominators = new ArrayList<>();
           nominators.addAll(findAllUserNominatorList(1,nominations1.get(i).getId()));
           data= new NominationDtoDisplayData(nominations1.get(i).getName(),nominations1.get(i).getId(),nominators.size(),nominators);
           dataList1.add(data);
       }


        for(int i=0;i<nominations2.size();i++){
            List<UserDtoIdName> nominators = new ArrayList<>();
            nominators.addAll(findAllUserNominatorList(2,nominations2.get(i).getId()));
            data= new NominationDtoDisplayData(nominations2.get(i).getName(),nominations2.get(i).getId(),nominators.size(),nominators);
            dataList2.add(data);
        }


        for(int i=0;i<nominations3.size();i++){
            List<UserDtoIdName> nominators = new ArrayList<>();
            nominators.addAll(findAllUserNominatorList(3,nominations3.get(i).getId()));
            data= new NominationDtoDisplayData(nominations3.get(i).getName(),nominations3.get(i).getId(),nominators.size(),nominators);
            dataList3.add(data);
        }


        for(int i=0;i<nominations4.size();i++){
            List<UserDtoIdName> nominators = new ArrayList<>();
            nominators.addAll(findAllUserNominatorList(4,nominations4.get(i).getId()));
            data= new NominationDtoDisplayData(nominations4.get(i).getName(),nominations4.get(i).getId(),nominators.size(),nominators);
            dataList4.add(data);
        }


        for(int i=0;i<nominations5.size();i++){
            List<UserDtoIdName> nominators = new ArrayList<>();
            nominators.addAll(findAllUserNominatorList(5,nominations5.get(i).getId()));
            data= new NominationDtoDisplayData(nominations5.get(i).getName(),nominations5.get(i).getId(),nominators.size(),nominators);
            dataList5.add(data);
        }

        List<UserDtoIdName> nominators2 = new ArrayList<>();
        NominationDtoAdmin obj = new NominationDtoAdmin(1,"Play",dataList1);
        NominationDtoAdmin obj2 = new NominationDtoAdmin(2,"Respect",dataList2);
        NominationDtoAdmin obj3 = new NominationDtoAdmin(3,"Openness",dataList3);
        NominationDtoAdmin obj4 = new NominationDtoAdmin(3,"Customer success",dataList4);
        NominationDtoAdmin obj5 = new NominationDtoAdmin(3,"Excellence",dataList5);

        adminList.add(obj);
        adminList.add(obj2);
        adminList.add(obj3);
        adminList.add(obj4);
        adminList.add(obj5);
        return adminList;

    }



    private List<NominationDtoCounterValueIdUserId> getNominationDtoCounterValueIdUserIds(List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIds, List<CampaignDto> campaignListAsDTO) {
        if (campaignListAsDTO.size() == 1) {
            Date dateTo = campaignListAsDTO.get(0).getDateTo();
            Date dateFrom = campaignListAsDTO.get(0).getDateFrom();
            List<Map<String, Number>> list = nominationDao.selectWinners(dateFrom, dateTo);
            list.forEach(item -> {
                nominationDtoCounterValueIdUserIds.add(new NominationDtoCounterValueIdUserId(item.get("counter").intValue(), item.get("value_id").intValue(), item.get("user_id").intValue()));
            });

            if (checkListContainAllValues(nominationDtoCounterValueIdUserIds)) {
                for(int i=0;i<nominationDtoCounterValueIdUserIds.size();i++){
                    UserDtoIdName user = transformFromUserDtotoUserDtoIdName(userAppService.findById(nominationDtoCounterValueIdUserIds.get(i).getUserId()));
                    ValueDtoIdName value = transformFromValueToValueDtoIdName(transformFromValueDtoToValue(valueService.findById(nominationDtoCounterValueIdUserIds.get(i).getValueId())));
                    CampaignDtoIdDescription campaign = transformFromCampaignToCampaignDtoIdDescription(transformFromCampaignDtoToCampaign(campaignService.findById(campaignListAsDTO.get(0).getId())));
                    int count = nominationDtoCounterValueIdUserIds.get(i).getCounter();
                    winnerService.save(new WinnerDto(value, user, campaign, count));
                }

                campaignService.disableCampaign(campaignListAsDTO.get(0).getId());
                campaignService.enableCampaign(campaignListAsDTO.get(0).getId()+1);
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
        List<NominationDtoWithoutDates> nominationListAsDto = nominationList.stream()
                .map(this::transformFromNominationToNominationDtoWithoutDates).collect(Collectors.toList());
        return nominationListAsDto;
    }
}
