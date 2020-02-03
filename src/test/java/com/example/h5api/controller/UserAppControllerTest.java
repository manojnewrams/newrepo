package com.example.h5api.controller;

import com.example.h5api.dto.UserDto;
import com.example.h5api.service.UserAppService;
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
@WebMvcTest(UserAppController.class)
public class UserAppControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    UserAppService userAppService;

    private UserDto user = new UserDto();
    private String json;
    private String jsonList;
    private ArrayList<UserDto> userList = new ArrayList<>();

    @Before
    public void setUp() throws JsonProcessingException {
        user.setName("Camilo");
        user.setCompany("Nisum");
        user.setPassword("SDasd!$a21");
        user.setStatus(true);
        user.setEmail("test@nisum.com");
        user.setRole(false);
        user.setId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(user);
        userList.add(user);
        jsonList = objectMapper.writeValueAsString(userList);
    }

    @Test
    public void findUserById() throws Exception {
        Mockito.when(userAppService.findById(Mockito.anyInt())).thenReturn(user);
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/user/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());

    }

    @Test
    public void findAllUsers() throws Exception {
        List<UserDto> userList = new LinkedList<>();
        userList.add(user);
        Mockito.when(userAppService.findAll()).thenReturn(userList);
        mvc.perform(MockMvcRequestBuilders
                .get("/user/list/api"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void saveUser() throws Exception {
        Mockito.when(userAppService.save(any(UserDto.class))).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.post("/user/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .exists())
                .andExpect(status().isOk());
    }

    @Test
    public void saveList() throws Exception {
        Mockito.when(userAppService.saveList(any())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.post("/user/list")
                .content(jsonList)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$")
                        .isBoolean())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/user/")
                .param("id", "3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
                .andExpect(status().isOk());
    }
}
