package com.example.h5api.controller;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.CampaignDtoIdDescription;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(SpringRunner.class)
public class CampaignControllerTest {

    @Mock
    private CampaignController campaignController;

    private  CampaignDtoIdDescription campaignDtoIdDescription;
    private CampaignDto campaign;
    private List <CampaignDtoIdDescription> campaignDtoIdDescriptionLinkedList= new LinkedList<>();
    private List<CampaignDto> campaignList = new LinkedList<>();
    @Before

    public void setup() throws ParseException {
        campaign = new CampaignDto();
        campaignDtoIdDescription = new CampaignDtoIdDescription(1,"First Quarter");
        campaign.setId(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        campaign.setCreateAt(myDate);
        campaignList.add(campaign);
        campaignDtoIdDescriptionLinkedList.add(campaignDtoIdDescription);
    }


    @Test
    public void findCampaignById() {
        Mockito.when(campaignController.findById(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto campaignEntity = campaignController.findById(campaign.getId());
        assertNotNull(campaignEntity);
        Assert.assertEquals(1, campaignEntity.getId());
        verify(campaignController).findById(Mockito.anyInt());
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void findAllCampaigns() {
        List<CampaignDto> campaignList = new LinkedList<>();
        campaignList.add(campaign);
        Mockito.when(campaignController.list()).thenReturn(campaignList);
        List<CampaignDto> responseList = campaignController.list();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(campaignController).list();
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void saveCampaign() {
        Mockito.when(campaignController.save(any(CampaignDto.class))).thenReturn(campaign);
        CampaignDto response = campaignController.save(campaign);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(campaignController).save(Mockito.any(CampaignDto.class));
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void deleteCampaign() {
        campaignController.delete(campaign.getId());
        verify(campaignController).delete(Mockito.anyInt());
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void enableCampaign() {
        Mockito.when(campaignController.enableCampaign(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto response = campaignController.enableCampaign(campaign.getId());
        assertNotNull(response);
        verify(campaignController).enableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void disableCampaign() {
        Mockito.when(campaignController.disableCampaign(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto response = campaignController.disableCampaign(campaign.getId());
        assertNotNull(response);
        verify(campaignController).disableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void nominationSummary() {
        Mockito.when(campaignController.nominationSummary()).thenReturn(campaignList);
       List<CampaignDto>  response = campaignController.nominationSummary();
        assertNotNull(response);
        verify(campaignController).nominationSummary();
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void nominationSummaryWithDate() throws ParseException {
        Mockito.when(campaignController.nominationSummary(Mockito.any(Date.class))).thenReturn(campaignList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        List<CampaignDto>  response = campaignController.nominationSummary(myDate);
        assertNotNull(response);
        verify(campaignController).nominationSummary(Mockito.any(Date.class));
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void findAllCampaignIdName() {
        Mockito.when(campaignController.findAllCampaignIdName()).thenReturn(campaignDtoIdDescriptionLinkedList);
        List <CampaignDtoIdDescription> response = campaignController.findAllCampaignIdName();
        assertNotNull(response);
        verify(campaignController).findAllCampaignIdName();
        verifyNoMoreInteractions(campaignController);
    }

}
