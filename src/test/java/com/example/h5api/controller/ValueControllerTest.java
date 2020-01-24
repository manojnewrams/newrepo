package com.example.h5api.controller;


import com.example.h5api.dto.ValueDto;
import com.example.h5api.entity.Value;
import com.example.h5api.service.ValueService;
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
@WebMvcTest(ValueController.class)
public class ValueControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ValueService valueService;

    private ValueDto value;
    private String json;

    @Before
    public void setup() throws JsonProcessingException {
        value = new ValueDto();
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(value);
    }

    @Test
    public void findValueById() throws Exception {
        Mockito.when(valueService.findById(Mockito.anyInt())).thenReturn(value);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/value/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void findAllValues() throws Exception {
        List<ValueDto> valueList = new LinkedList<>();
        valueList.add(value);
        Mockito.when(valueService.findAll()).thenReturn(valueList);
        mvc.perform(MockMvcRequestBuilders.get("/value/list/api"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(".[0].id").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void saveValue() throws Exception {
        Mockito.when(valueService.save(any(ValueDto.class))).thenReturn(value);
        mvc.perform(MockMvcRequestBuilders.post("/value/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .exists())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteValue() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/value/")
                .param("id", "3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }


}
