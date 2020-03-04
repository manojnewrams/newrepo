package com.example.h5api.controller;

import com.example.h5api.dto.NominationDtoCounterRepeat;
import com.example.h5api.dto.WinnerDto;
import com.example.h5api.dto.WinnerDtoWithoutDates;
import com.example.h5api.service.CampaignService;
import com.example.h5api.service.NominationService;
import com.example.h5api.service.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("winner")
public class WinnerController implements GenericController<WinnerDto> {

    private final WinnerService winnerService;
    private final NominationService nominationService;
    private final CampaignService campaignService;

    @Autowired
    public WinnerController(WinnerService winnerService, NominationService nominationService, CampaignService campaignService) {
        this.winnerService = winnerService;
        this.nominationService = nominationService;
        this.campaignService = campaignService;
    }

    @Override
    public List<WinnerDto> list() {
        return winnerService.findAll();
    }

    @Override
    public WinnerDto findById(Integer id) {
        return winnerService.findById(id);
    }

    @Override
    public WinnerDto save(WinnerDto obj) {
        return winnerService.save(obj);
    }

    @Override
    public void delete(Integer id) {
        winnerService.deleteById(id);
    }

    @GetMapping("findByCampaignId/{id}")
    public List <WinnerDtoWithoutDates> findByCampaignId(@PathVariable Integer id){
        return winnerService.findWinnerByCampaignId(id);
    }

    @GetMapping("hasRepeat")
    public  List<NominationDtoCounterRepeat> hasRepeat(){
        return campaignService.counterRepeats();
    }

    @GetMapping("hasRepeat/{date}")
    public  List<NominationDtoCounterRepeat> hasRepeat(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return campaignService.counterRepeats(date);
    }

    @GetMapping("list/all")
    public List<WinnerDtoWithoutDates> listWinnersWithoutDates(){
        return winnerService.findAllWithoutDates();
    }

    @GetMapping("list")
    public List<WinnerDtoWithoutDates> listWinnersOfLastQuarter(){
        return winnerService.findWinnersFromLastCampaignWithoutDates();
    }
}
