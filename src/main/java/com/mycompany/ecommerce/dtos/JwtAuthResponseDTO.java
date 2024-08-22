package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtAuthResponseDTO {

    @JsonProperty
    private String token;

    @JsonProperty
    private Integer maxTime;

    public JwtAuthResponseDTO(String token, Integer maxTime) {
        this.token = token;
        this.maxTime = maxTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }
}
