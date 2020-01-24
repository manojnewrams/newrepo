package com.example.h5api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface IController<T> {
    @GetMapping("/list/api")
    List<T> list();

    @GetMapping("/{id}")
    T findById(@PathVariable Integer id);

    @PostMapping
    T save(@RequestBody T obj);

    @DeleteMapping
    void delete(@RequestParam(value = "id") Integer id);
}
