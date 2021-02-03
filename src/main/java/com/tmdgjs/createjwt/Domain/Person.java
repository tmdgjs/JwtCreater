package com.tmdgjs.createjwt.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@RedisHash("people")
@ToString(exclude = {"id"})
public class Person implements Serializable {

    private static final long serialVersionUID = 1370692830319429806L;


    @Builder
    public Person(String id, String firstname, String lastname, Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    @Id
    String id;
    String firstname;
    String lastname;
    Address address;


}
