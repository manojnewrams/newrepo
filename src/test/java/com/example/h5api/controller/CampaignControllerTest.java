package com.example.h5api.controller;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.ValueDto;
import com.example.h5api.entity.Campaign;
import com.example.h5api.service.CampaignService;
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
public class CampaignControllerTest {

    @Mock
    CampaignService campaignService;

    @InjectMocks
    private CampaignController campaignController;

    private CampaignDto campaign;
    private String json;

    @Before
    public void setup() throws JsonProcessingException {
        campaign = new CampaignDto();
        campaign.setId(1);
       // campaign.setStatus(true);
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(campaign);
    }


    @Test
    public void findCampaignById() throws Exception {
        Mockito.when(campaignService.findById(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto campaignEntity = campaignController.findById(campaign.getId());
        assertNotNull(campaignEntity);
        Assert.assertEquals(1,campaignEntity.getId());
        verify(campaignService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void findAllCampaigns() throws Exception {
        List<CampaignDto> campaignList = new LinkedList<>();
        campaignList.add(campaign);
        Mockito.when(campaignService.findAll()).thenReturn(campaignList);
        List<CampaignDto> responseList = campaignController.campaignService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(campaignService).findAll();
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void saveCampaign() throws Exception {
        Mockito.when(campaignService.save(any(CampaignDto.class))).thenReturn(campaign);
        CampaignDto response = campaignController.campaignService.save(campaign);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(campaignService).save(Mockito.any(CampaignDto.class));
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void deleteCampaign() throws Exception {
        campaignController.campaignService.deleteById(campaign.getId());
        verify(campaignService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void enableCampaign() throws Exception {
        Mockito.when(campaignService.enableCampaign(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto response = campaignController.enableCampaign(campaign.getId());
        assertNotNull(response);
        verify(campaignService).enableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void disableCampaign() throws Exception {
        Mockito.when(campaignService.disableCampaign(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto response = campaignController.disableCampaign(campaign.getId());
        assertNotNull(response);
        verify(campaignService).disableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignService);
    }

}
