package com.example.h5api.service;

import com.example.h5api.dto.UserDto;
import com.example.h5api.dto.UserDtoIdName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UserAppServiceTest {

    @Mock
    private UserAppService userAppService;

    private UserDto userDto;
    private UserDto userDto2;
    private UserDtoIdName userDtoIdName;
    private List<UserDto> userDtoList = new ArrayList<>();
    private List<UserDtoIdName> userDtoIdNameList = new ArrayList<>();

    @Before
    public void setup() {
        userDto = new UserDto();
        userDto.setId(1);
        userDto2 = new UserDto();
        userDto2.setId(2);
        userDtoIdName = new UserDtoIdName();
        userDtoIdName.setId(1);
        userDtoIdName.setName("Sebastian");
        userDtoList.add(userDto);
        userDtoList.add(userDto2);
        userDtoIdNameList.add(userDtoIdName);
    }

    @Test
    public void findAll() {
        Mockito.when(userAppService.findAll()).thenReturn(userDtoList);
        List<UserDto> responseList = userAppService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(userAppService).findAll();
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void findAllNotDeleted() {
        Mockito.when(userAppService.findAllNotDeleted()).thenReturn(userDtoList);
        List<UserDto> responseList = userAppService.findAllNotDeleted();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(userAppService).findAllNotDeleted();
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void findById() {
        Mockito.when(userAppService.findById(Mockito.anyInt())).thenReturn(userDto);
        UserDto response = userAppService.findById(1);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(userAppService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void save() {
        Mockito.when(userAppService.save(Mockito.any(UserDto.class))).thenReturn(userDto);
        UserDto response = userAppService.save(userDto);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(userAppService).save(Mockito.any(UserDto.class));
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void deleteById() {
        userAppService.deleteById(userDto.getId());
        verify(userAppService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void existById() {
        Mockito.when(userAppService.existById(Mockito.anyInt())).thenReturn(true);
        Boolean response = userAppService.existById(1);
        assertNotNull(response);
        Assert.assertEquals(true, response);
        verify(userAppService).existById(Mockito.anyInt());
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void findAllUserIdName() {
        Mockito.when(userAppService.findAllUserIdName()).thenReturn(userDtoIdNameList);
        List<UserDtoIdName> responseList = userAppService.findAllUserIdName();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(userAppService).findAllUserIdName();
        verifyNoMoreInteractions(userAppService);
    }

    @Test
    public void findAllAvailableCandidates() {
        Mockito.when(userAppService.findAllAvailableCandidates(Mockito.anyInt())).thenReturn(userDtoIdNameList);
        List<UserDtoIdName> responseList = userAppService.findAllAvailableCandidates(2);
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.size());
        verify(userAppService).findAllAvailableCandidates(Mockito.anyInt());
        verifyNoMoreInteractions(userAppService);
    }

}