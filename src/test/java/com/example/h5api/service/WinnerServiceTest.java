package com.example.h5api.service;

import com.example.h5api.dto.WinnerDto;
import com.example.h5api.dto.WinnerDtoWithoutDates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class WinnerServiceTest {
    @Mock
    WinnerService winnerService;

    private List<WinnerDto> winnerDtoList = new ArrayList<>();
    private WinnerDto winnerDto;
    private List<WinnerDtoWithoutDates> winnerDtoWithOutDateList = new ArrayList<>();
    private WinnerDtoWithoutDates winnerDtoWithoutDates;

    @Before
    public void setup() {
        winnerDto = new WinnerDto();
        winnerDtoWithoutDates = new WinnerDtoWithoutDates();
        winnerDtoWithoutDates.setId(1);
        winnerDto.setId(1);
        winnerDtoList.add(winnerDto);
        winnerDtoWithOutDateList.add(winnerDtoWithoutDates);

    }

    @Test
    public void findAll() {
        Mockito.when(winnerService.findAll()).thenReturn(winnerDtoList);
        List<WinnerDto> responseList = winnerService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(winnerService).findAll();
        verifyNoMoreInteractions(winnerService);
    }


    @Test
    public void findById() {
        Mockito.when(winnerService.findById(Mockito.anyInt())).thenReturn(winnerDto);
        WinnerDto response = winnerService.findById(1);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(winnerService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void save() {
        Mockito.when(winnerService.save(Mockito.any(WinnerDto.class))).thenReturn(winnerDto);
        WinnerDto response = winnerService.save(winnerDto);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(winnerService).save(Mockito.any(WinnerDto.class));
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void deleteById() {
        winnerService.deleteById(winnerDto.getId());
        verify(winnerService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void existById() {
        Mockito.when(winnerService.existById(Mockito.anyInt())).thenReturn(true);
        Boolean response = winnerService.existById(1);
        assertNotNull(response);
        Assert.assertEquals(true, response);
        verify(winnerService).existById(Mockito.anyInt());
        verifyNoMoreInteractions(winnerService);
    }

//    @Test
//    public void findWinnerByCampaignId() {
//        Mockito.when(winnerService.findWinnerByCampaignId(Mockito.anyInt())).thenReturn(winnerDtoList);
//        List<WinnerDto> response = winnerService.findWinnerByCampaignId(1);
//        assertNotNull(response);
//        Assert.assertSame(winnerDtoList, response);
//        verify(winnerService).findWinnerByCampaignId(Mockito.anyInt());
//        verifyNoMoreInteractions(winnerService);
//    }

    @Test
    public void findWinnersFromLastCampaignWithoutDates() {
        Mockito.when(winnerService.findWinnersFromLastCampaignWithoutDates()).thenReturn(winnerDtoWithOutDateList);
        List<WinnerDtoWithoutDates> response = winnerService.findWinnersFromLastCampaignWithoutDates();
        assertNotNull(response);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(1, response.get(0).getId());
        verify(winnerService).findWinnersFromLastCampaignWithoutDates();
        verifyNoMoreInteractions(winnerService);
    }

    @Test
    public void findAllWithoutDates() {
        Mockito.when(winnerService.findAllWithoutDates()).thenReturn(winnerDtoWithOutDateList);
        List<WinnerDtoWithoutDates> response = winnerService.findAllWithoutDates();
        assertNotNull(response);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(1, response.get(0).getId());
        verify(winnerService).findAllWithoutDates();
        verifyNoMoreInteractions(winnerService);
    }
}