package com.example.h5api.service;

import com.example.h5api.utils.Transformer;
import com.example.h5api.repository.ValueRepository;
import com.example.h5api.dto.*;
import com.example.h5api.entity.Value;
import com.example.h5api.exceptions.GenericEmptyListException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ValueServiceTest {
    @Mock
    private ValueRepository valueDao;

    @Mock
    private Transformer transformer;

    @Mock
    private Value value;

    @Mock
    private ValueDto valueDto2;

    @InjectMocks
    private ValueService valueService;

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
        List<Value> findAll= Collections.singletonList(value);
        Mockito.when(valueDao.findAll()).thenReturn(findAll);
        Mockito.when(transformer.transformFromValueToValueDto(value)).thenReturn(valueDto2);

        List<ValueDto> responseList = valueService.findAll();

        assertNotNull(responseList);
        assertEquals(findAll.size(), responseList.size());
        for (ValueDto dto : responseList) {
            assertEquals(valueDto2,dto);
        }
        verify(valueDao).findAll();
        verify(transformer,times(findAll.size())).transformFromValueToValueDto(value);
        verifyNoMoreInteractions(valueDao, transformer, value, valueDto2);
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

    @Test(expected = GenericEmptyListException.class )
    public void shouldFindAllWhenTheListIsEmpty() {
        List<Value> findAll= Collections.emptyList();
        Mockito.when(valueDao.findAll()).thenReturn(findAll);

        try{
            valueService.findAll();
        }catch (GenericEmptyListException exception){
            verify(valueDao).findAll();
            verifyNoMoreInteractions(valueDao, value);
            throw exception;
        }

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