package com.example.h5api.controller;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.CampaignDtoIdDescription;
import com.example.h5api.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("campaign")
public class CampaignController implements GenericController<CampaignDto> {

    @Autowired
    private CampaignService campaignService;

    @Override
    public List<CampaignDto> list() {
        return campaignService.findAll();
    }

    @Override
    public CampaignDto findById(Integer id) {
        return campaignService.findById(id);
    }

    @Override
    public CampaignDto save(CampaignDto campaignDto) {
        return campaignService.save(campaignDto);
    }

    @Override
    public void delete(Integer id) {
        campaignService.deleteById(id);
    }

    @GetMapping("/enable/{id}")
    public CampaignDto enableCampaign(@PathVariable Integer id) {
        return campaignService.enableCampaign(id);
    }

    @GetMapping("/disable/{id}")
    public CampaignDto disableCampaign(@PathVariable Integer id) {
        return campaignService.disableCampaign(id);
    }

    @GetMapping("/get/{date}")
    public List <CampaignDto> nominationSummary(@PathVariable("date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return campaignService.getCampaignByDate(date);
    }

    @GetMapping("/get/")
    public List <CampaignDto> nominationSummary(){
        return campaignService.getCampaignByDateNow();
    }

    @GetMapping("/listCampaigns")
    public List <CampaignDtoIdDescription> findAllCampaignIdName(){
        return campaignService.findAllCampaignIdName();
    }
}
