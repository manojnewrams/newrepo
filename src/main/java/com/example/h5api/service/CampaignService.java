package com.example.h5api.service;

import com.example.h5api.utils.Transformer;
import com.example.h5api.repository.CampaignRepository;
import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.CampaignDtoIdDescription;
import com.example.h5api.entity.Campaign;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService extends Transformer implements GenericService<CampaignDto> {
    @Autowired
    private CampaignRepository campaignDao;

    @Override
    @Transactional(readOnly = true)
    public List<CampaignDto> findAll() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.findAll().forEach(campaignList::add);
        if (campaignList.size() == 0) {
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto).collect(Collectors.toList());
        return campaignListAsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignDto findById(Integer id) {
        Campaign campaign = campaignDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return transformFromCampaignToCampaignDto(campaign);
    }

    @Override
    @Transactional
    public CampaignDto save(CampaignDto campaignDto) {
        campaignDao.save(transformFromCampaignDtoToCampaign(campaignDto));
        return campaignDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Campaign campaign = campaignDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        campaign.setDeleteAt(new Date());
        campaignDao.save(campaign);

    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return campaignDao.existsById(id);
    }

    @Transactional
    public CampaignDto enableCampaign(int id) {
        Campaign campaign = campaignDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        campaign.setStatus(true);
        campaignDao.save(campaign);
        return transformFromCampaignToCampaignDto(campaign);
    }

    @Transactional
    public CampaignDto disableCampaign(int id) {
        Campaign campaign = campaignDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        campaign.setStatus(false);
        campaignDao.save(campaign);
        return transformFromCampaignToCampaignDto(campaign);
    }

    public List<CampaignDto> getCampaignByDate(Date date) {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getCampaignByDate(date).forEach(campaignList::add);
        if(campaignList.size()==0){
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto).collect(Collectors.toList());
        return campaignListAsDTO;
    }

    public List<CampaignDto> getCampaignByDateNow() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getCampaignByDateNow(new Date()).forEach(campaignList::add);
        if(campaignList.size()==0){
            throw new GenericEmptyListException();
        }
        List<CampaignDto> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDto).collect(Collectors.toList());
        return campaignListAsDTO;
    }

    @Transactional(readOnly = true)
    public List<CampaignDtoIdDescription> findAllCampaignIdName() {
        List<Campaign> campaignList = new ArrayList<>();
        campaignDao.getAllCampaignOrderByDateTo().forEach(campaignList::add);
        if (campaignList.size() == 0) {
            throw new GenericEmptyListException();
        }
        List<CampaignDtoIdDescription> campaignListAsDTO = campaignList.stream()
                .map(this::transformFromCampaignToCampaignDtoIdDescription).collect(Collectors.toList());
        return campaignListAsDTO;
    }

}
