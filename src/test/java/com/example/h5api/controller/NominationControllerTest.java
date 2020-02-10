package com.example.h5api.controller;


import com.example.h5api.dto.*;
import com.example.h5api.entity.Nomination;
import com.example.h5api.service.NominationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class NominationControllerTest {

    @Mock
    NominationService nominationService;

    @InjectMocks
    private NominationController nominationController;

    private NominationDto nomination;
    private NominationDtoWithoutDates nominationDtoWithoutDates;


    @Before
    public void setup() {
        nomination = new NominationDto();
        nominationDtoWithoutDates= new NominationDtoWithoutDates(1,1, new UserDtoIdName(1,"Seba"), "play a lot", new ValueDtoIdName(1, "play"));
        nomination.setId(1);
    }

    @Test
    public void findNominationById() {
        Mockito.when(nominationService.findById(Mockito.anyInt())).thenReturn(nomination);
        NominationDto nominationEntity = nominationController.findById(nomination.getId());
        assertNotNull(nominationEntity);
        Assert.assertEquals(1, nominationEntity.getId());
        verify(nominationService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void findAllNominations() {
        List<NominationDto> nominationList = new LinkedList<>();
        nominationList.add(nomination);
        Mockito.when(nominationService.findAll()).thenReturn(nominationList);
        List<NominationDto> responseList = nominationController.nominationService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationService).findAll();
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void saveNomination() {
        Mockito.when(nominationService.save(any(NominationDto.class))).thenReturn(nomination);
        NominationDto response = nominationController.nominationService.save(nomination);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(nominationService).save(Mockito.any(NominationDto.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void deleteWinner() {
        nominationController.nominationService.deleteById(nomination.getId());
        verify(nominationService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void checkNominationSummaryWithoutDateAsParameter() {
        List<NominationDtoWithoutDates> list = new ArrayList<>();
        list.add(nominationDtoWithoutDates);
        Mockito.when(nominationService.findAllWithoutDates()).thenReturn(list);
        List<NominationDtoWithoutDates> responseList = nominationController.nominationService.findAllWithoutDates();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationService).findAllWithoutDates();
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void checkNominationSummaryWithDateAsParameter() throws ParseException {
        List<NominationDtoCounterRepeat> list = new ArrayList<>();
        NominationDtoCounterRepeat nominationDtoCounterRepeat = new NominationDtoCounterRepeat(1);
        nominationDtoCounterRepeat.setRepeat(1);
        list.add(nominationDtoCounterRepeat);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        Mockito.when(nominationService.counterRepeats(any(Date.class))).thenReturn(list);
        List<NominationDtoCounterRepeat> response = nominationController.nominationService.counterRepeats(myDate);
        assertNotNull(response);
        Assert.assertEquals(1,response.get(0).getRepeat());
        verify(nominationService).counterRepeats(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);


    }

}
