package com.tmdgjs.createjwt.Service;

import com.tmdgjs.createjwt.Domain.Header;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JwtCreateService {


    public String convertHeader(Header header){


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("alg", header.getAlg());
        jsonObject.put("typ", header.getType());

        String beforeEncodeHeader = jsonObject.toJSONString().trim();
        byte[] targetBytes = beforeEncodeHeader.getBytes();

        Base64.Encoder encoder = Base64.getEncoder(); byte[] encodedBytes = encoder.encode(targetBytes);

        return new String(encodedBytes);
    }

}
