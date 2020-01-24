package com.example.h5api.dao;

import com.example.h5api.entity.Value;
import org.springframework.data.repository.CrudRepository;

public interface IValueDao extends CrudRepository<Value,Integer> {
}
