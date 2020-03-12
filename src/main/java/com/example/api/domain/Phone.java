package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Phone {

    @JsonProperty(value = "number")
    private Integer number;

    @JsonProperty(value = "citycode")
    private String cityCode;

    @JsonProperty(value = "countrycode")
    private String countryCode;

}
