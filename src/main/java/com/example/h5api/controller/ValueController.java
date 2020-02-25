package com.example.h5api.controller;

import com.example.h5api.dto.ValueDto;
import com.example.h5api.dto.ValueDtoWithoutDates;
import com.example.h5api.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("value")
public class ValueController implements GenericController<ValueDto> {


    final
    ValueService valueService;

    @Autowired
    public ValueController(ValueService valueService) {
        this.valueService = valueService;
    }

    @Override
    public List<ValueDto> list() {
        return valueService.findAll();
    }

    @Override
    public ValueDto findById(Integer id) {
        return valueService.findById(id);
    }

    @Override
    public ValueDto save(ValueDto obj) {
        return valueService.save(obj);
    }

    @Override
    public void delete(Integer id) {
        valueService.deleteById(id);
    }

    @GetMapping("/list")
    public List<ValueDtoWithoutDates> listAll() {
        return valueService.findAllWithoutDates();
    }
}
