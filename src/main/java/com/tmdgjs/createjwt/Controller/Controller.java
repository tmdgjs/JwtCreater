package com.tmdgjs.createjwt.Controller;

import com.tmdgjs.createjwt.Domain.Address;
import com.tmdgjs.createjwt.Domain.Header;
import com.tmdgjs.createjwt.Domain.JJwtToken;
import com.tmdgjs.createjwt.Domain.Person;
import com.tmdgjs.createjwt.Response.DefaultResponse;
import com.tmdgjs.createjwt.Service.GetSetService;
import com.tmdgjs.createjwt.Service.JwtCreateService;
import com.tmdgjs.createjwt.Service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/v1/getPayload")
    public ResponseEntity<String> getPayload(){

        Map<String, Object> payloads = new HashMap<>();

        payloads.put("name", "dfdsafdsafdsafjdsalkfjdsajf;dsa");
        payloads.put("lv", 225);
        payloads.put("exp", 15023431434L);
        payloads.put("char", "flame");

        String convertedPayload = jwtCreateService.convertPayload(payloads);


        return new ResponseEntity<>(convertedPayload, HttpStatus.OK);


    }

    @GetMapping("/v1/getSignature")
    public ResponseEntity<String> getSignature(){

        String algorithm = "HS256";

        String abc = jwtCreateService.convertHeader(Header.builder().alg(algorithm).build());
        System.out.println(getPayload().getBody());

        String headerPlusPayload = abc + "." + getPayload().getBody();

        /*String signature = jwtCreateService.getSingnature(abc, headerPlusPayload);

        String jwtToken = headerPlusPayload + "." + signature;
*/
        String jwtToken = "";
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    /*@PostMapping("/v2/jwts")
    public ResponseEntity<Object> getJJwtToken(@RequestBody JJwtToken jjwtToken){

        DefaultResponse objResponse = jwtCreateService.createJJwtToken(jjwtToken);

        if(objResponse.getCode() == 400)
            return new ResponseEntity<>(objResponse.getMsg(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(objResponse.getData(), HttpStatus.OK);
    }*/



}
