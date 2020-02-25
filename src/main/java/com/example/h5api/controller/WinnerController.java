package com.example.h5api.controller;

import com.example.h5api.dto.NominationDtoCounterRepeat;
import com.example.h5api.dto.WinnerDto;
import com.example.h5api.dto.WinnerDtoWithoutDates;
import com.example.h5api.service.NominationService;
import com.example.h5api.service.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("winner")
public class WinnerController implements GenericController<WinnerDto> {

    final
    WinnerService winnerService;

    final
    NominationService nominationService;

    @Autowired
    public WinnerController(WinnerService winnerService, NominationService nominationService) {
        this.winnerService = winnerService;
        this.nominationService = nominationService;
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
        return nominationService.counterRepeats();
    }

    @GetMapping("hasRepeat/{date}")
    public  List<NominationDtoCounterRepeat> hasRepeat(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return nominationService.counterRepeats(date);
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
