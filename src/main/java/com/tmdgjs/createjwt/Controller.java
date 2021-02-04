package com.tmdgjs.createjwt;

import com.tmdgjs.createjwt.Domain.Address;
import com.tmdgjs.createjwt.Domain.Header;
import com.tmdgjs.createjwt.Domain.Person;
import com.tmdgjs.createjwt.Service.GetSetService;
import com.tmdgjs.createjwt.Service.JwtCreateService;
import com.tmdgjs.createjwt.Service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private GetSetService getSetService;

    @Autowired
    private Services services;

    @Autowired
    private JwtCreateService jwtCreateService;


    @GetMapping("/v1/get")
    public ResponseEntity<Object> getRedisInfo(){

        return new ResponseEntity<>(getSetService.test(), HttpStatus.OK);
    }

    @GetMapping("/v1/list")
    public ResponseEntity<Object> getRedisList(){

        return new ResponseEntity<>(services.getList(), HttpStatus.OK);
    }

    @GetMapping("/v1/set")
    public ResponseEntity<Object> getRedisSet(){

        return new ResponseEntity<>(services.getSet(), HttpStatus.OK);
    }

    @GetMapping("/v1/save")
    public ResponseEntity<Object> getSave(){

        Address address = new Address("서울특별시 서울시청");
        Person person1 = Person.builder().id(null).firstname("HAN").lastname("seungheon").address(address).build();
        return new ResponseEntity<>(services.saves(person1), HttpStatus.OK);
    }

    @GetMapping("/v1/getHeader")
    public ResponseEntity<Object> getHeader(@RequestParam(value = "alg") String algorithm){

        String abc = jwtCreateService.convertHeader(Header.builder().alg(algorithm).build());

        return new ResponseEntity<>(abc, HttpStatus.OK);
    }



}
