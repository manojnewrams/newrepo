package com.example.h5api.repository;

import com.example.h5api.dto.ValueDtoIdName;
import com.example.h5api.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value,Integer> {
    @Query(value = "select new com.example.h5api.dto.ValueDtoIdName(v.id,v.name) from Value v")
        List<ValueDtoIdName> findIdAndValue();



}


