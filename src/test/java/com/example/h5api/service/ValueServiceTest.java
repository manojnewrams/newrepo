package com.example.h5api.service;

import com.example.h5api.dto.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ValueServiceTest {

    @Mock
    ValueService valueService;

    private ValueDto valueDto;
    private ValueDtoIdName valueDtoIdName;
    private ValueDtoWithoutDates valueDtoWithoutDates;
    private List<ValueDto> valueDtoList = new ArrayList<>();
    private List<ValueDtoIdName> valueDtoIdNameList = new ArrayList<>();
    private List<ValueDtoWithoutDates> valueDtoWithoutDatesList = new ArrayList<>();

    @Before
    public void setup() {
        valueDto = new ValueDto();
        valueDtoIdName = new ValueDtoIdName();
        valueDtoWithoutDates = new ValueDtoWithoutDates();
        valueDto.setId(1);
        valueDtoIdName.setId(1);
        valueDtoWithoutDates.setId(1);
        valueDtoList.add(valueDto);
        valueDtoIdNameList.add(valueDtoIdName);
        valueDtoWithoutDatesList.add(valueDtoWithoutDates);
    }

    @Test
    public void findAll() {
        Mockito.when(valueService.findAll()).thenReturn(valueDtoList);
        List<ValueDto> responseList = valueService.findAll();
        assertNotNull(responseList);
        assertEquals(1, responseList.get(0).getId());
        verify(valueService).findAll();
        verifyNoMoreInteractions(valueService);
    }


    @Test
    public void findById() {
        Mockito.when(valueService.findById(Mockito.anyInt())).thenReturn(valueDto);
        ValueDto response = valueService.findById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
        verify(valueService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void save() {
        Mockito.when(valueService.save(Mockito.any(ValueDto.class))).thenReturn(valueDto);
        ValueDto response = valueService.save(valueDto);
        assertNotNull(response);
        assertEquals(1, response.getId());
        verify(valueService).save(Mockito.any(ValueDto.class));
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void deleteById() {
        valueService.deleteById(valueDto.getId());
        verify(valueService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void existById() {
        Mockito.when(valueService.existById(Mockito.anyInt())).thenReturn(true);
        Boolean response = valueService.existById(1);
        assertNotNull(response);
        Assert.assertEquals(true, response);
        verify(valueService).existById(Mockito.anyInt());
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void findAllValueIdName() {
        Mockito.when(valueService.findAllValueIdName()).thenReturn(valueDtoIdNameList);
        List<ValueDtoIdName> responseList = valueService.findAllValueIdName();
        assertNotNull(responseList);
        assertEquals(1, responseList.get(0).getId());
        verify(valueService).findAllValueIdName();
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void findAllWithoutDates() {
        Mockito.when(valueService.findAllWithoutDates()).thenReturn(valueDtoWithoutDatesList);
        List<ValueDtoWithoutDates> responseList = valueService.findAllWithoutDates();
        assertNotNull(responseList);
        assertEquals(1, responseList.get(0).getId());
        verify(valueService).findAllWithoutDates();
        verifyNoMoreInteractions(valueService);
    }
}