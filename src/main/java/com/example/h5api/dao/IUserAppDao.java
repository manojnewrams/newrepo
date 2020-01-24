package com.example.h5api.dao;

import com.example.h5api.entity.UserApp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserAppDao extends CrudRepository<UserApp, Integer> {
    @Query(value = "SELECT user FROM UserApp as user WHERE user.deleteAt is NULL")
    public List<UserApp> findAllNotDeleted();

    UserApp findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT MAX(user.id) as LastId FROM UserApp as user")
    Integer getLastId();
}
