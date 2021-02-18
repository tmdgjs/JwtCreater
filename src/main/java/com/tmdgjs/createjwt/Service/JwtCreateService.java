package com.tmdgjs.createjwt.Service;

import com.tmdgjs.createjwt.Domain.Header;
import com.tmdgjs.createjwt.Domain.JJwtToken;
import com.tmdgjs.createjwt.Request.JWTRequest;
import com.tmdgjs.createjwt.Request.TokenSaveRequest;
import com.tmdgjs.createjwt.Response.DefaultResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
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

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);

        return new String(encodedBytes);
    }

    public String convertPayload(Map<String, Object> payloads) {


        JSONObject payloadJson = new JSONObject();

        payloads.forEach((key, value) -> payloadJson.put(key, value));

        if(payloads.size() >= 1){

            String beforeEncodePayload = payloadJson.toJSONString().trim();
            byte[] targetBytes = beforeEncodePayload.getBytes();

            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(targetBytes);


            return new String(encodedBytes).replace("=","");
        }

        return "";

    }

    public DefaultResponse getSignature(JWTRequest jwtRequest, String headerPlusPayload) {

        try{
            if(jwtRequest.getSecretKey().equals("")){
                throw new NullPointerException("SecretKey가 존재하지 않습니다.");
            }

            String selectAlgorithm = selectAlgorithm(jwtRequest.getAlgo());

            return DefaultResponse.builder()
                                  .code(HttpStatus.OK.value())
                                  .data(encodeSignature(selectAlgorithm, jwtRequest.getSecretKey(), headerPlusPayload))
                                  .build();

        } catch (NullPointerException npe){
            npe.printStackTrace();
            return new DefaultResponse(HttpStatus.BAD_REQUEST.value(), npe.getMessage());
        }
    }

    public String selectAlgorithm(String algorithm){

        String selectAlgorithm = "";

        switch (algorithm){
            case "HS256" :
                selectAlgorithm = SignatureAlgorithm.HS256.getJcaName();
                break;
            case "HS384":
                selectAlgorithm = SignatureAlgorithm.HS384.getJcaName();
                break;
            case "HS512":
                selectAlgorithm = SignatureAlgorithm.HS512.getJcaName();
                break;
        }

        if(selectAlgorithm == null){
            throw new NullPointerException("알고리즘이 선택되지 않았습니다.");
        }

        return selectAlgorithm;
    }

    public String encodeSignature(String algorithm, String secretKey, String headerPlusPayload) {

        Mac mac = null;
        try {
            mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secretKey.getBytes(), algorithm));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
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

    public DefaultResponse createJJwtToken(JWTRequest jwtRequest) {

        try{
            if(jwtRequest.getSecretKey().equals("")){
                throw new NullPointerException("SecretKey가 존재하지 않습니다.");
            } /*else if (jwtRequest.getSecretKey().length() < 4 ){
                throw new IllegalArgumentException("SecretKey의 길이는 4자리 이상이여야 합니다.");
            }*/

            SignatureAlgorithm selectAlgorithm = null;

            switch (jwtRequest.getAlgo()){
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
            Map<String, Object> objHeader = createJJwtTokenHeader(jwtRequest.getAlgo());

            Map<String, Object> objPayload = jwtRequest.getData();

            String strJJwt = Jwts.builder()
                    .setHeader(objHeader)
                    .setClaims(objPayload)
                    .signWith(selectAlgorithm, jwtRequest.getSecretKey().getBytes())
                    .compact();

            return DefaultResponse.builder().code(200).data(strJJwt).build();
        } catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
            return new DefaultResponse(400, e.getMessage());
        }
    }

    public Map<String, Object> createJJwtTokenHeader(String algorithm){
        Map<String, Object> objHeader = new HashMap<>();

        objHeader.put("typ", "JWT");
        objHeader.put("alg", algorithm);

        return objHeader;
    }

}
