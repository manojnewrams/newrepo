package com.example.h5api.service;

import com.example.h5api.dto.ValueDto;
import com.example.h5api.dto.ValueDtoIdName;
import com.example.h5api.dto.ValueDtoWithoutDates;
import com.example.h5api.entity.Value;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import com.example.h5api.repository.ValueRepository;
import com.example.h5api.utils.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValueService implements GenericService<ValueDto> {

    private final ValueRepository valueDao;

    private final ValueUtil valueUtil;

    @Autowired
    public ValueService(ValueRepository valueDao, ValueUtil valueUtil) {
        this.valueDao = valueDao;
        this.valueUtil = valueUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueDto> findAll() {
        List<Value> all = valueDao.findAll();
        if (all.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return all.stream()
                .map(valueUtil::transformFromValueToValueDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ValueDto findById(Integer id) {
        Value value = valueDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return valueUtil.transformFromValueToValueDto(value);
    }

    @Override
    @Transactional
    public ValueDto save(ValueDto valueDto) {
        valueDao.save(valueUtil.transformFromValueDtoToValue(valueDto));
        return valueDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Value value = valueDao.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        value.setDeleteAt(new Date());
        valueDao.save(value);

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
        if (valueList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return valueList.stream()
                .map(valueUtil::transformFromValueToValueDtoIdName).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ValueDtoWithoutDates> findAllWithoutDates() {
        List<Value> valueList = new ArrayList<>();
        valueDao.findAll().forEach(valueList::add);
        if (valueList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return valueList.stream()
                .map(valueUtil::transformFromValueToValueDtoWithoutDates).collect(Collectors.toList());
    }
}
