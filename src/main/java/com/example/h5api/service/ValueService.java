package com.example.h5api.service;

import com.example.h5api.builders.Transformer;
import com.example.h5api.dao.IValueDao;
import com.example.h5api.dto.*;
import com.example.h5api.entity.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValueService extends Transformer implements IGenericService<ValueDto> {

    @Autowired
    private IValueDao valueDao;

    @Override
    @Transactional(readOnly = true)
    public List<ValueDto> findAll() {
        List<Value> valueList = new ArrayList<>();
        valueDao.findAll().forEach(valueList::add);
        List<ValueDto> valueListAsDto = valueList.stream()
                .map(this::transformFromValueToValueDto).collect(Collectors.toList());
        return valueListAsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ValueDto findById(Integer id) {
        Value value = valueDao.findById(id).orElse(null);
        return transformFromValueToValueDto(value);
    }

    @Override
    @Transactional
    public ValueDto save(ValueDto valueDto) {
        valueDao.save(transformFromValueDtoToValue(valueDto));
        return valueDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Value value = valueDao.findById(id).orElse(null);
        if (value != null) {
            value.setDeleteAt(new Date());
            valueDao.save(value);
        }
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return valueDao.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<ValueDtoIdName> findAllValueIdName() {
        List<Value> valueList = new ArrayList<>();
        valueDao.findAll().forEach(valueList::add);
        List<ValueDtoIdName> valueListAsDto = valueList.stream()
                .map(this::transformFromValueToValueDtoIdName).collect(Collectors.toList());
        return valueListAsDto;
    }

    @Transactional(readOnly = true)
    public List<ValueDtoWithoutDates> findAllWithoutDates() {
        List<Value> valueList = new ArrayList<>();
        valueDao.findAll().forEach(valueList::add);
        List<ValueDtoWithoutDates> valueListAsDto = valueList.stream()
                .map(this::transformFromValueToValueDtoWithoutDates).collect(Collectors.toList());
        return valueListAsDto;
    }
}
