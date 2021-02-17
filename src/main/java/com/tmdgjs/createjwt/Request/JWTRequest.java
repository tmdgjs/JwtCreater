package com.tmdgjs.createjwt.Request;

import lombok.Getter;

import java.util.Map;

@Getter
public class JWTRequest {

    String algo;

    Map<String, Object> data;

    String secretKey;

}
