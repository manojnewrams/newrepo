package com.example.h5api.service;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.CampaignDtoIdDescription;
import com.example.h5api.entity.Campaign;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import com.example.h5api.repository.CampaignRepository;
import com.example.h5api.utils.CampaignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService implements GenericService<CampaignDto> {
    private final CampaignRepository campaignRepository;

    private final CampaignUtil campaignUtil;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, CampaignUtil campaignUtil) {
        this.campaignRepository = campaignRepository;
        this.campaignUtil = campaignUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignDto> findAll() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignRepository.findAll().forEach(campaignList::add);
        if (campaignList.size() == 0) {
            throw new GenericEmptyListException();
        }
        return campaignList.stream()
                .map(campaignUtil::transformFromCampaignToCampaignDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignDto findById(Integer id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return campaignUtil.transformFromCampaignToCampaignDto(campaign);
    }

    @Override
    @Transactional
    public CampaignDto save(CampaignDto campaignDto) {
        campaignRepository.save(campaignUtil.transformFromCampaignDtoToCampaign(campaignDto));
        return campaignDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        campaign.setDeleteAt(new Date());
        campaignRepository.save(campaign);

    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return campaignRepository.existsById(id);
    }

    @Transactional
    public CampaignDto enableCampaign(int id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        campaign.setStatus(true);
        campaignRepository.save(campaign);
        return campaignUtil.transformFromCampaignToCampaignDto(campaign);
    }

    @Transactional
    public CampaignDto disableCampaign(int id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        campaign.setStatus(false);
        campaignRepository.save(campaign);
        return campaignUtil.transformFromCampaignToCampaignDto(campaign);
    }

    public List<CampaignDto> getCampaignByDate(Date date) {
        List<Campaign> campaignList = new ArrayList<>();
        campaignRepository.getCampaignByDate(date).forEach(campaignList::add);
        if (campaignList.size() == 0) {
            throw new GenericEmptyListException();
        }
        return campaignList.stream()
                .map(campaignUtil::transformFromCampaignToCampaignDto).collect(Collectors.toList());
    }

    public List<CampaignDto> getCampaignByDateNow() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignRepository.getCampaignByDateNow(new Date()).forEach(campaignList::add);
        if (campaignList.size() == 0) {
            throw new GenericEmptyListException();
        }
        return campaignList.stream()
                .map(campaignUtil::transformFromCampaignToCampaignDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CampaignDtoIdDescription> findAllCampaignIdName() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignRepository.getAllCampaignOrderByDateTo().forEach(campaignList::add);
        if (campaignList.size() == 0) {
            throw new GenericEmptyListException();
        }
       return campaignList.stream()
                .map(campaignUtil::transformFromCampaignToCampaignDtoIdDescription).collect(Collectors.toList());
    }

}
