package com.example.h5api.exceptions;

import com.example.h5api.entity.Campaign;

public class CampaignIsClosedException extends RuntimeException {
    public CampaignIsClosedException(){
        super("Something is wrong in nomination, Campaign is not found or closed");
    }
}
