package com.example.h5api.service;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.NominationDtoCounterRepeat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {

    @Mock
    private CampaignService campaignService;

    private CampaignDto campaignDto;
    private List<CampaignDto> campaignList = new ArrayList<>();

    @Before
    public void setup() {
        campaignDto = new CampaignDto();
        campaignDto.setId(1);
        campaignList.add(campaignDto);

    }

    @Test
    public void findAll() {
        Mockito.when(campaignService.findAll()).thenReturn(campaignList);
        List<CampaignDto> responseList = campaignService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(campaignService).findAll();
        verifyNoMoreInteractions(campaignService);
    }


    @Test
    public void findById() {
        Mockito.when(campaignService.findById(Mockito.anyInt())).thenReturn(campaignDto);
        CampaignDto response = campaignService.findById(1);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(campaignService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void save(){
        Mockito.when(campaignService.save(Mockito.any(CampaignDto.class))).thenReturn(campaignDto);
        CampaignDto response = campaignService.save(campaignDto);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(campaignService).save(Mockito.any(CampaignDto.class));
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void deleteById(){
        campaignService.deleteById(campaignDto.getId());
        verify(campaignService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void enableCampaign(){
        Mockito.when(campaignService.enableCampaign(Mockito.anyInt())).thenReturn(campaignDto);
        CampaignDto response = campaignService.enableCampaign(campaignDto.getId());
        assertNotNull(response);
        verify(campaignService).enableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void disableCampaign(){
        Mockito.when(campaignService.disableCampaign(Mockito.anyInt())).thenReturn(campaignDto);
        CampaignDto response = campaignService.disableCampaign(campaignDto.getId());
        assertNotNull(response);
        verify(campaignService).disableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void getCampaignByDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        Mockito.when(campaignService.getCampaignByDate(any(Date.class))).thenReturn(campaignList);
        List<CampaignDto> response = campaignService.getCampaignByDate(myDate);
        assertNotNull(response);
        verify(campaignService).getCampaignByDate(Mockito.any(Date.class));
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void getCampaignByDateNow(){
        Mockito.when(campaignService.getCampaignByDate(any(Date.class))).thenReturn(campaignList);
        List<CampaignDto> response = campaignService.getCampaignByDate(new Date());
        assertNotNull(response);
        verify(campaignService).getCampaignByDate(Mockito.any(Date.class));
        verifyNoMoreInteractions(campaignService);
    }
}
