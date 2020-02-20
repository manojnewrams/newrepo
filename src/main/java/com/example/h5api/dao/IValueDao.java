package com.example.h5api.dao;

import com.example.h5api.dto.ValueDtoIdName;
import com.example.h5api.entity.UserApp;
import com.example.h5api.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IValueDao extends JpaRepository<Value,Integer> {
    @Query(value = "select new com.example.h5api.dto.ValueDtoIdName(v.id,v.name) from Value v")
        List<ValueDtoIdName> findIdAndValue();



}


