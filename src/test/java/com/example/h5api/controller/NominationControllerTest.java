package com.example.h5api.controller;


import com.example.h5api.dto.NominationDto;
import com.example.h5api.dto.NominationDtoCounterRepeat;
import com.example.h5api.dto.ValueDtoCountId;
import com.example.h5api.entity.Nomination;
import com.example.h5api.service.NominationService;
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
@WebMvcTest(NominationController.class)
public class NominationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NominationService nominationService;

    private NominationDto nomination;
    private String json;

    @Before
    public void setup() throws JsonProcessingException {
        nomination = new NominationDto();
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(nomination);
    }

    @Test
    public void findNominationById() throws Exception {
        Mockito.when(nominationService.findById(Mockito.anyInt())).thenReturn(nomination);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/nomination/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void findAllNominations() throws Exception {
        List<NominationDto> nominationList = new LinkedList<>();
        nominationList.add(nomination);
        Mockito.when(nominationService.findAll()).thenReturn(nominationList);
        mvc.perform(MockMvcRequestBuilders.get("/nomination/list/api"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(".[0].id").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void saveNomination() throws Exception {
        Mockito.when(nominationService.save(any(NominationDto.class))).thenReturn(nomination);
        mvc.perform(MockMvcRequestBuilders.post("/nomination/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .exists())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteWinner() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/nomination/")
                .param("id", "3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    public void checkNominationSummaryWithoutDateAsParameter() throws Exception {
        List<ValueDtoCountId> list = new ArrayList<>();
        Mockito.when(nominationService.nominationSummary()).thenReturn(list);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/nomination/summary/")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void checkNominationSummaryWithDateAsParameter() throws Exception {
        List<ValueDtoCountId> list = new ArrayList<>();
        Mockito.when(nominationService.nominationSummary()).thenReturn(list);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/nomination/summary/2020-02-01")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}
