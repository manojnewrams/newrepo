package com.example.h5api.controller;

import com.example.h5api.dto.ValueDto;
import com.example.h5api.dto.ValueDtoWithoutDates;
import com.example.h5api.service.ValueService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
public class ValueControllerTest {

    @Mock
    ValueService valueService;

    @InjectMocks
    private ValueController valueController;

    private ValueDto value;
    private ValueDtoWithoutDates valueDtoWithoutDate;

    @Before
    public void setup() {
        value = new ValueDto();
        valueDtoWithoutDate = new ValueDtoWithoutDates();
        valueDtoWithoutDate.setId(1);
        valueDtoWithoutDate.setDescription("Play");
        valueDtoWithoutDate.setName("Play");
        value.setId(1);
    }

    @Test
    public void findValueById() {
        Mockito.when(valueService.findById(Mockito.anyInt())).thenReturn(value);
        ValueDto valueEntity = valueController.findById(value.getId());
        assertNotNull(valueEntity);
        Assert.assertEquals(1, valueEntity.getId());
        verify(valueService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void findAllValues() {
        List<ValueDto> valueList = new LinkedList<>();
        valueList.add(value);
        Mockito.when(valueService.findAll()).thenReturn(valueList);
        List<ValueDto> responseList = valueController.valueService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(valueService).findAll();
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void saveValue() {
        Mockito.when(valueService.save(any(ValueDto.class))).thenReturn(value);
        ValueDto response = valueController.valueService.save(value);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(valueService).save(Mockito.any(ValueDto.class));
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void deleteValue() {
        valueController.valueService.deleteById(value.getId());
        verify(valueService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(valueService);
    }

    @Test
    public void listAllWithoutDateValues() {
        List<ValueDtoWithoutDates> valueList = new LinkedList<>();
        valueList.add(valueDtoWithoutDate);
        Mockito.when(valueService.findAllWithoutDates()).thenReturn(valueList);
        List<ValueDtoWithoutDates> responseList = valueController.valueService.findAllWithoutDates();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(valueService).findAllWithoutDates();
        verifyNoMoreInteractions(valueService);
    }
}