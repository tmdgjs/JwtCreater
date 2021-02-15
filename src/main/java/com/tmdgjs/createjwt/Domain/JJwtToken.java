package com.tmdgjs.createjwt.Domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class JJwtToken {

    String algorithm;

    Map<String, Object> data;

    String secretKey;

}
