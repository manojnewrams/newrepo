package com.example.h5api.controller;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.dto.CampaignDtoIdDescription;
import com.example.h5api.service.CampaignService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CampaignControllerTest {
    @Mock
    private CampaignService campaignService;

    @Mock
    private CampaignDto campaignDto;

    @InjectMocks
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
        LocalDate myDate = LocalDate.of(2020,01,15);
        campaign.setCreateAt(myDate);
        campaignList.add(campaign);
        campaignDtoIdDescriptionLinkedList.add(campaignDtoIdDescription);
    }


    @Test
    public void findCampaignById() {
        final Integer id = 2;
        when(campaignService.findById(id)).thenReturn(campaignDto);

        CampaignDto result = campaignController.findById(id);

        assertNotNull(result);
        assertEquals(campaignDto, result);
        verify(campaignService).findById(id);
        verifyNoMoreInteractions(campaignService,campaignDto);
    }

    @Test
    public void findAllCampaigns() {
        List<CampaignDto> campaignList = singletonList(campaignDto);
        when(campaignService.findAll()).thenReturn(campaignList);

        List<CampaignDto> result = campaignController.list();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(campaignList.size(),result.size());
        verify(campaignService).findAll();
        verifyNoMoreInteractions(campaignService);
        verifyNoMoreInteractions(campaignDto);
    }

    @Test
    public void saveCampaign() {
        CampaignDto afterSaveDto = mock(CampaignDto.class);
        when(campaignService.save(campaignDto)).thenReturn(afterSaveDto);

        CampaignDto response = campaignController.save(campaignDto);

        assertNotNull(response);
        assertEquals(afterSaveDto, response);
        verify(campaignService).save(campaignDto);
        verifyNoMoreInteractions(campaignService,campaignDto,afterSaveDto);
    }

    @Test
    public void deleteCampaign() {
        final Integer id = 5;

        campaignController.delete(id);

        verify(campaignService).deleteById(id);
        verifyNoMoreInteractions(campaignService);
    }

    @Test
    public void enableCampaign() {
        final Integer id = 9;
        when(campaignService.enableCampaign(id)).thenReturn(campaignDto);

        CampaignDto response = campaignController.enableCampaign(id);

        assertNotNull(response);
        assertEquals(campaignDto,response);
        verify(campaignService).enableCampaign(id);
        verifyNoMoreInteractions(campaignService,campaignDto);
    }

    @Test
    public void disableCampaign() {
        when(campaignController.disableCampaign(Mockito.anyInt())).thenReturn(campaign);
        CampaignDto response = campaignController.disableCampaign(campaign.getId());
        assertNotNull(response);
        verify(campaignController).disableCampaign(Mockito.anyInt());
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void nominationSummary() {
        when(campaignController.nominationSummary()).thenReturn(campaignList);
       List<CampaignDto>  response = campaignController.nominationSummary();
        assertNotNull(response);
        verify(campaignController).nominationSummary();
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void nominationSummaryWithDate() throws ParseException {
        when(campaignController.nominationSummary(Mockito.any(LocalDate.class))).thenReturn(campaignList);
        LocalDate myDate = LocalDate.of(2020,01,15);
        List<CampaignDto>  response = campaignController.nominationSummary(myDate);
        assertNotNull(response);
        verify(campaignController).nominationSummary(Mockito.any(LocalDate.class));
        verifyNoMoreInteractions(campaignController);
    }

    @Test
    public void findAllCampaignIdName() {
        when(campaignController.findAllCampaignIdName()).thenReturn(campaignDtoIdDescriptionLinkedList);
        List <CampaignDtoIdDescription> response = campaignController.findAllCampaignIdName();
        assertNotNull(response);
        verify(campaignController).findAllCampaignIdName();
        verifyNoMoreInteractions(campaignController);
    }

}
