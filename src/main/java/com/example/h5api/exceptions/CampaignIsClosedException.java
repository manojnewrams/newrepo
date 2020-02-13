package com.example.h5api.exceptions;

import com.example.h5api.entity.Campaign;

public class CampaignIsClosedException extends RuntimeException {
    public CampaignIsClosedException(){
        super("No campaign available for this quarter");
    }
}
