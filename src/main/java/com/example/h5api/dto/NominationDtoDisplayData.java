package com.example.h5api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NominationDtoDisplayData {
    private String candidateName;
    private int candidateId;
    private int count;
    private List<UserDtoIdName> nominators;

    public NominationDtoDisplayData(String candidateName,int candidateId,int count, List<UserDtoIdName> userDtoIdNameList){
        this.candidateName = candidateName;
        this.candidateId = candidateId;
        this.count = count;
        this.nominators = userDtoIdNameList;
    }


}

