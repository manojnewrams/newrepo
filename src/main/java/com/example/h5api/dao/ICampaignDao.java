package com.example.h5api.dao;

import com.example.h5api.entity.Campaign;
import com.example.h5api.entity.Winner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ICampaignDao extends CrudRepository<Campaign,Integer> {

    @Query(value = "SELECT * FROM campaign as c WHERE ?1 BETWEEN c.date_from AND c.date_to" ,nativeQuery = true)
    List<Campaign> getCampaignByDate(Date date);


    @Query(value = "SELECT * FROM campaign as c WHERE ?1 BETWEEN c.date_from AND c.date_to AND c.status='true'" ,nativeQuery = true)
    List<Campaign> getCampaignByDateNow(Date date);



}
