package com.example.h5api.repository;

import com.example.h5api.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    @Query(value = "SELECT * FROM campaign as c WHERE ?1 BETWEEN c.date_from AND c.date_to", nativeQuery = true)
    List<Campaign> getCampaignByDate(Date date);

    @Query(value = "SELECT * FROM campaign as c WHERE ?1 BETWEEN c.date_from AND c.date_to AND c.status='true'", nativeQuery = true)
    List<Campaign> getCampaignByDateNow(Date date);

    @Query(value = "SELECT * FROM CAMPAIGN WHERE status = 'true' ORDER BY date_to DESC", nativeQuery = true)
    List<Campaign> getAllCampaignOrderByDateTo();

}
