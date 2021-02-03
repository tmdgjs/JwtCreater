package com.tmdgjs.createjwt.Domain;

import lombok.Getter;

@Getter
public class Address {

    private String address;

    public Address(String address){
        this.address = address;
    }

}
