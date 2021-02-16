package com.tmdgjs.createjwt.Service;

import com.tmdgjs.createjwt.Domain.Header;
import com.tmdgjs.createjwt.Domain.JJwtToken;
import com.tmdgjs.createjwt.Exception.JwtException;
import com.tmdgjs.createjwt.Response.DefaultResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
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


            return new String(encodedBytes).replace("=","");
        }

        return "";

    }

    public String getSingnature(String abc, String headerPlusPayload) {

            return getSIGN(headerPlusPayload);

    }

    public String getSIGN(String headerPlusPayload) {

        String sharedSecret = "test";
        String algorithm = "HmacSHA512";

        Mac mac = null;
        try {
            mac = Mac.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(new SecretKeySpec(sharedSecret.getBytes(), algorithm));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] signatureBytes = mac.doFinal(headerPlusPayload.getBytes());

        String signature = Base64.getEncoder().encodeToString(signatureBytes);

        signature = strReplace(signature, "=","");
        signature = strReplace(signature, "+","-");
        signature = strReplace(signature, "/","_");

        return signature;

    }

    public String strReplace(String beforeString, CharSequence target, CharSequence replacement){
        return beforeString.replace(target, replacement);
    }

    public DefaultResponse createJJwtToken(JJwtToken jjwtToken) {

        try{
            SignatureAlgorithm selectAlgorithm = null;

            switch (jjwtToken.getAlgorithm()){
                case "HS256" :
                    selectAlgorithm = SignatureAlgorithm.HS256;
                    break;
                case "HS384":
                    selectAlgorithm = SignatureAlgorithm.HS384;
                    break;
                case "HS512":
                    selectAlgorithm = SignatureAlgorithm.HS512;
                    break;
            }

            if(selectAlgorithm == null){
                throw new NullPointerException("알고리즘이 선택되지 않았습니다.");
            }

            // Header
            Map<String, Object> objHeader = createJJwtTokenHeader(jjwtToken.getAlgorithm());

            Map<String, Object> objPayload = jjwtToken.getData();

            String strJJwt = Jwts.builder()
                    .setHeader(objHeader)
                    .setClaims(objPayload)
                    .signWith(selectAlgorithm, jjwtToken.getSecretKey())
                    .compact();

            return new DefaultResponse(200, strJJwt);
        } catch (NullPointerException npe){
            npe.printStackTrace();
            return new DefaultResponse(400, npe.getMessage());
        }


    }

    public Map<String, Object> createJJwtTokenHeader(String algorithm){
        Map<String, Object> objHeader = new HashMap<>();

        objHeader.put("typ", "JWT");
        objHeader.put("alg", algorithm);

        return objHeader;
    }
}
