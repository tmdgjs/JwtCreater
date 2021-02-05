package com.tmdgjs.createjwt.Service;

import com.tmdgjs.createjwt.Domain.Header;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

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

    public String convertPayload(Map<String, Object> payloads) {


        JSONObject payloadJson = new JSONObject();

        payloads.forEach((key, value) -> payloadJson.put(key, value));

        if(payloads.size() >= 0){

            String beforeEncodePayload = payloadJson.toJSONString().trim();
            byte[] targetBytes = beforeEncodePayload.getBytes();

            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(targetBytes);


            return new String(encodedBytes);
        }

        return "";

    }
}
