package com.example.h5api.controllers;

import com.example.h5api.controller.UserAppController;
import com.example.h5api.dto.UserDto;
import com.example.h5api.service.UserAppService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UserAppControllerTest {
    @InjectMocks
    private UserAppController userAppController;

    @Mock
    private UserAppService userAppService;

    private UserDto userapp;

    @Before
    public void setup(){
        userapp = new  UserDto();
        userapp.setId(1);
    }

    @Test
    public void list(){
        List<UserDto> userList = new ArrayList<>();
        userList.add(userapp);
        Mockito.when(userAppService.findAll()).thenReturn(userList);
        List<UserDto> response = userAppController.list();
        Assert.assertNotNull(response);
        Assert.assertEquals(userList.get(0).getId(), response.get(0).getId());
        Mockito.verify(userAppService).findAll();
        Mockito.verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void findById() {
        Mockito.when(userAppService.findById(Mockito.anyInt())).thenReturn(userapp);
        UserDto response = userAppController.findById(1);
        Assert.assertEquals(userapp.getId(),response.getId());
        verify(userAppService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void save(){
        Mockito.when(userAppService.save(Mockito.any(UserDto.class))).thenReturn(userapp);
        UserDto response = userAppController.save(userapp);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getId(), userapp.getId());
        Mockito.verify(userAppService).save(Mockito.any(UserDto.class));
        Mockito.verifyNoMoreInteractions(userAppService);
    }

}
