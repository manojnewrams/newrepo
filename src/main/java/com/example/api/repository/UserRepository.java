package com.example.api.repository;


import com.example.api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    @Query(value = "SELECT U FROM Users U where U.username= ?1")
    public Users findUser(String username);

}
