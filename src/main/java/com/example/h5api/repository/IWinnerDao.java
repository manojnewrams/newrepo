package com.example.h5api.repository;

import com.example.h5api.entity.Winner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IWinnerDao extends CrudRepository<Winner, Integer> {
    @Query(value = "SELECT * FROM Winner as winner WHERE campaign_id=?1 ORDER BY value_id ASC LIMIT 5", nativeQuery = true)
    List<Winner> findWinnerByCampaignId(Integer id);

    @Query(value = "SELECT max(campaign_id) as lastCampaign FROM winner",nativeQuery = true)
    Integer findLastCampaignId();


}
