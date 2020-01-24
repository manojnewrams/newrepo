package com.example.h5api.controller;

import com.example.h5api.dto.NominationDto;
import com.example.h5api.dto.NominationDtoCounterValueIdUserId;
import com.example.h5api.dto.NominationDtoWithoutDates;
import com.example.h5api.dto.ValueDtoCountId;
import com.example.h5api.service.NominationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("nomination")
@Log
public class NominationController implements IController<NominationDto> {
    @Autowired
    NominationService nominationService;

    @Override
    public List<NominationDto> list() {
        return nominationService.findAll();
    }

    @Override
    public NominationDto findById(Integer id) {
        return nominationService.findById(id);
    }


    @Override
    public NominationDto save(NominationDto obj) {
        return nominationService.save(obj);
    }

    @Override
    public void delete(Integer id) {
        nominationService.deleteById(id);
    }

    @GetMapping("/summary/{date}")
    public List<ValueDtoCountId> nominationSummary(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return nominationService.nominationSummary(date);
    }

    @GetMapping("/summary/")
    public List<ValueDtoCountId> nominationSummary() {
        return nominationService.nominationSummary();
    }

    @GetMapping("/drawWinners")
    public List<NominationDtoCounterValueIdUserId> winnersOfQuarter(){
        return nominationService.drawWinnersOfQuarter();
    }

    @GetMapping("/drawWinners/{date}")
    public List<NominationDtoCounterValueIdUserId> winnersOfQuarter(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return nominationService.drawWinnersOfQuarter(date);
    }

    @GetMapping("/list")
    public List<NominationDtoWithoutDates> listWithoutDates() {
        return nominationService.findAllWithoutDates();
    }

}
