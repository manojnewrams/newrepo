package com.example.h5api.controller;


import com.example.h5api.dto.NominationDtoCounterRepeat;
import com.example.h5api.dto.WinnerDto;
import com.example.h5api.service.NominationService;
import com.example.h5api.service.WinnerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
public class WinnerControllerTest {

    @Mock
    WinnerService winnerService;

    @Mock
    NominationService nominationService;

    @InjectMocks
    WinnerController winnerController;

    private WinnerDto winner;

    @Before
    public void setup() {
        winner = new WinnerDto();
        winner.setId(1);

    }

    @Test
    public void findWinnerById() throws Exception {
        Mockito.when(winnerService.findById(Mockito.anyInt())).thenReturn(winner);
        WinnerDto response = winnerController.findById(winner.getId());
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(winnerService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void findAllWinners() throws Exception {
        List<WinnerDto> winnerList = new LinkedList<>();
        winnerList.add(winner);
        Mockito.when(winnerService.findAll()).thenReturn(winnerList);
        List<WinnerDto> responseList = winnerController.winnerService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(winnerService).findAll();
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void saveWinner() throws Exception {
        Mockito.when(winnerService.save(any(WinnerDto.class))).thenReturn(winner);
        WinnerDto response = winnerController.winnerService.save(winner);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(winnerService).save(Mockito.any(WinnerDto.class));
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void deleteWinner() throws Exception {
        winnerController.winnerService.deleteById(winner.getId());
        verify(winnerService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(winnerService);
    }

//    @Test
//    public void findWinnerByCampaignId() throws Exception {
//        List<WinnerDto> winnerList = new LinkedList<>();
//        winnerList.add(winner);
//        Mockito.when(winnerService.findWinnerByCampaignId(Mockito.anyInt())).thenReturn(winnerList);
//        List<WinnerDto> response = winnerController.winnerService.findWinnerByCampaignId(winner.getId());
//        assertNotNull(response);
//        Assert.assertEquals(1,response.get(0).getId());
//        verify(winnerService).findWinnerByCampaignId(Mockito.anyInt());
//        verifyNoMoreInteractions(winnerService);
//    }

    @Test
    public void findIfWinnersHasRepeatsNomination() throws Exception {
        List<NominationDtoCounterRepeat> list = new ArrayList<>();
        NominationDtoCounterRepeat nominationDtoCounterRepeat = new NominationDtoCounterRepeat(1);
        nominationDtoCounterRepeat.setRepeat(1);
        list.add(nominationDtoCounterRepeat);
        Mockito.when(nominationService.counterRepeats()).thenReturn(list);
        List<NominationDtoCounterRepeat> response = winnerController.nominationService.counterRepeats();
        assertNotNull(response);
        Assert.assertEquals(1,response.get(0).getRepeat());
        verify(nominationService).counterRepeats();
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void findIfWinnersHasRepeatsNominationWithDateAsParameter() throws Exception {
        List<NominationDtoCounterRepeat> list = new ArrayList<>();
        NominationDtoCounterRepeat nominationDtoCounterRepeat = new NominationDtoCounterRepeat(1);
        nominationDtoCounterRepeat.setRepeat(1);
        list.add(nominationDtoCounterRepeat);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        Mockito.when(nominationService.counterRepeats(any(Date.class))).thenReturn(list);
        List<NominationDtoCounterRepeat> response = winnerController.nominationService.counterRepeats(myDate);
        assertNotNull(response);
        Assert.assertEquals(1,response.get(0).getRepeat());
        verify(nominationService).counterRepeats(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);

    }
}
