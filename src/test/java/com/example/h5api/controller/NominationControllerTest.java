package com.example.h5api.controller;


import com.example.h5api.dto.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
public class NominationControllerTest {

    @Mock
    private NominationController nominationController;

    private NominationDto nomination;
    private NominationDtoWithoutDates nominationDtoWithoutDates;
    private ValueDtoCountId valueDtoCountId;
    private NominationDtoCounterValueIdUserId nominationDtoCounterValueIdUserId;
    private NominationDtoAdmin nominationDtoAdmin;
    private List<NominationDtoDisplayData> nominationList;


    @Before
    public void setup() {
        nomination = new NominationDto();
        valueDtoCountId = new ValueDtoCountId();
        valueDtoCountId.setValueId(1);
        valueDtoCountId.setCounter(3);
        nominationDtoWithoutDates = new NominationDtoWithoutDates(1, 1, new UserDtoIdName(1, "Seba"), "play a lot", new ValueDtoIdName(1, "play"));
        nominationDtoCounterValueIdUserId = new NominationDtoCounterValueIdUserId(1, 1, 1);
        nominationList = new ArrayList<>();
        nominationDtoAdmin = new NominationDtoAdmin(1, "admin", nominationList);
        nomination.setId(1);
    }

    @Test
    public void findNominationById() {
        Mockito.when(nominationController.findById(Mockito.anyInt())).thenReturn(nomination);
        NominationDto nominationEntity = nominationController.findById(nomination.getId());
        assertNotNull(nominationEntity);
        Assert.assertEquals(1, nominationEntity.getId());
        verify(nominationController).findById(Mockito.anyInt());
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void findAllNominations() {
        List<NominationDto> nominationList = new LinkedList<>();
        nominationList.add(nomination);
        Mockito.when(nominationController.list()).thenReturn(nominationList);
        List<NominationDto> responseList = nominationController.list();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationController).list();
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void saveNomination() {
        Mockito.when(nominationController.save(any(NominationDto.class))).thenReturn(nomination);
        NominationDto response = nominationController.save(nomination);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(nominationController).save(Mockito.any(NominationDto.class));
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void deleteWinner() {
        nominationController.delete(nomination.getId());
        verify(nominationController).delete(Mockito.anyInt());
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void checkNominationSummaryWithoutDateAsParameter() {
        List<ValueDtoCountId> list = new ArrayList<>();
        list.add(valueDtoCountId);
        Mockito.when(nominationController.nominationSummary()).thenReturn(list);
        List<ValueDtoCountId> responseList = nominationController.nominationSummary();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getValueId());
        verify(nominationController).nominationSummary();
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void checkNominationSummaryWithDateAsParameter() throws ParseException {
        List<ValueDtoCountId> list = new ArrayList<>();
        NominationDtoCounterRepeat nominationDtoCounterRepeat = new NominationDtoCounterRepeat(1);
        nominationDtoCounterRepeat.setRepeat(1);
        list.add(valueDtoCountId);
        LocalDate myDate = LocalDate.of(2020,01,15);
        Mockito.when(nominationController.nominationSummary(any(LocalDate.class))).thenReturn(list);
        List<ValueDtoCountId> response = nominationController.nominationSummary(myDate);
        assertNotNull(response);
        Assert.assertEquals(1, response.get(0).getValueId());
        verify(nominationController).nominationSummary(Mockito.any(LocalDate.class));
        verifyNoMoreInteractions(nominationController);


    }

    @Test
    public void checkWinnersOfQuarterWithOutDateAsParameter() {
        List<NominationDtoCounterValueIdUserId> list = new ArrayList<>();
        list.add(nominationDtoCounterValueIdUserId);
        Mockito.when(nominationController.winnersOfQuarter()).thenReturn(list);
        List<NominationDtoCounterValueIdUserId> responseList = nominationController.winnersOfQuarter();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getValueId());
        verify(nominationController).winnersOfQuarter();
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void checkWinnersOfQuarterWithDateAsParameter() throws ParseException {
        List<NominationDtoCounterValueIdUserId> list = new ArrayList<>();
        list.add(nominationDtoCounterValueIdUserId);
        LocalDate myDate = LocalDate.of(2020,01,15);
        Mockito.when(nominationController.winnersOfQuarter(Mockito.any(LocalDate.class))).thenReturn(list);
        List<NominationDtoCounterValueIdUserId> responseList = nominationController.winnersOfQuarter(myDate);
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getValueId());
        verify(nominationController).winnersOfQuarter(Mockito.any(LocalDate.class));
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void checkListWithoutDates() {
        List<NominationDtoWithoutDates> list = new ArrayList<>();
        list.add(nominationDtoWithoutDates);
        Mockito.when(nominationController.listWithoutDates()).thenReturn(list);
        List<NominationDtoWithoutDates> responseList = nominationController.listWithoutDates();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationController).listWithoutDates();
        verifyNoMoreInteractions(nominationController);
    }

    @Test
    public void checkListAdmin() {
        List<NominationDtoAdmin> list = new ArrayList<>();
        list.add(nominationDtoAdmin);
        Mockito.when(nominationController.listAdmin()).thenReturn(list);
        List<NominationDtoAdmin> responseList = nominationController.listAdmin();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getValueId());
        verify(nominationController).listAdmin();
        verifyNoMoreInteractions(nominationController);
    }

}
