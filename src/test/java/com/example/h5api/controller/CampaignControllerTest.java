package com.example.h5api.controller;

import com.example.h5api.dto.CampaignDto;
import com.example.h5api.entity.Campaign;
import com.example.h5api.service.CampaignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CampaignService campaignService;

    private CampaignDto campaign;
    private String json;

    @Before
    public void setup() throws JsonProcessingException {
        campaign = new CampaignDto();
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(campaign);
    }


    @Test
    public void findCampaignById() throws Exception {
        Mockito.when(campaignService.findById(Mockito.anyInt())).thenReturn(campaign);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/campaign/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void findAllCampaigns() throws Exception {
        List<CampaignDto> campaignList = new LinkedList<>();
        campaignList.add(campaign);
        Mockito.when(campaignService.findAll()).thenReturn(campaignList);
        mvc.perform(MockMvcRequestBuilders.get("/campaign/list/api"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(".[0].id").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void saveCampaign() throws Exception {
        Mockito.when(campaignService.save(any(CampaignDto.class))).thenReturn(campaign);
        mvc.perform(MockMvcRequestBuilders.post("/campaign/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .exists())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCampaign() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/campaign/")
                .param("id", "3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    public void enableCampaign() throws Exception {
        Mockito.when(campaignService.enableCampaign(Mockito.anyInt())).thenReturn(campaign);
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/campaign/enable/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void disableCampaign() throws Exception {
        Mockito.when(campaignService.disableCampaign(Mockito.anyInt())).thenReturn(campaign);
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/campaign/disable/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

}
