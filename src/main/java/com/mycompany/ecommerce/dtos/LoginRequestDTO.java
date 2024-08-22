package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestDTO {

    @JsonProperty
    private String doc;

    @JsonProperty
    private String password;

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
