package com.example.h5api.service;

import java.util.List;

public interface GenericService<T> {

    List<T> findAll();

    T findById(Integer id);

    T save(T t);

    void deleteById(Integer id);

    Boolean existById(Integer id);
}
