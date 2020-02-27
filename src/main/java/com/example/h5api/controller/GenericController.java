package com.example.h5api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
public interface GenericController<T> {
    @GetMapping("/list/api")
    List<T> list();

    @GetMapping("/{id}")
    T findById(@PathVariable Integer id);

    @PostMapping
    T save(@RequestBody T obj);

    @DeleteMapping
    void delete(@RequestParam(value = "id") Integer id);
}
