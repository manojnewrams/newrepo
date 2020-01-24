package com.example.h5api.controller;


import com.example.h5api.dto.NominationDtoCounterRepeat;
import com.example.h5api.dto.WinnerDto;
import com.example.h5api.entity.Winner;
import com.example.h5api.service.NominationService;
import com.example.h5api.service.WinnerService;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WinnerController.class)
public class WinnerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    WinnerService winnerService;

    @MockBean
    NominationService nominationService;

    private WinnerDto winner;
    private String json;

    @Before
    public void setup() throws JsonProcessingException {
        winner = new WinnerDto();
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(winner);
    }

    @Test
    public void findWinnerById() throws Exception {
        Mockito.when(winnerService.findById(Mockito.anyInt())).thenReturn(winner);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/winner/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void findAllWinners() throws Exception {
        List<WinnerDto> winnerList = new LinkedList<>();
        winnerList.add(winner);
        Mockito.when(winnerService.findAll()).thenReturn(winnerList);
        mvc.perform(MockMvcRequestBuilders.get("/winner/list/api"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(".[0].id").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void saveWinner() throws Exception {
        Mockito.when(winnerService.save(any(WinnerDto.class))).thenReturn(winner);
        mvc.perform(MockMvcRequestBuilders.post("/winner/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .exists())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteWinner() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/winner/")
                .param("id", "3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    public void findWinnerByCampaignId() throws Exception {
        List<WinnerDto> winnerList = new LinkedList<>();
        winnerList.add(winner);
        Mockito.when(winnerService.findWinnerByCampaignId(Mockito.anyInt())).thenReturn(winnerList);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/winner/findByCampaignId/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").exists());
    }

    @Test
    public void findIfWinnersHasRepeatsNomination() throws Exception {
        List<NominationDtoCounterRepeat> list = new ArrayList<>();
        Mockito.when(nominationService.counterRepeats()).thenReturn(list);
        mvc.perform(
                MockMvcRequestBuilders
                .get("/winner/hasRepeat/")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void findIfWinnersHasRepeatsNominationWithDateAsParameter() throws Exception {
        List<NominationDtoCounterRepeat> list = new ArrayList<>();
        Mockito.when(nominationService.counterRepeats()).thenReturn(list);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/winner/hasRepeat/2020-02-01")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
