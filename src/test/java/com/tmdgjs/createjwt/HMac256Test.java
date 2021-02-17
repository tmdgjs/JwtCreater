package com.tmdgjs.createjwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HMac256Test {

    // hash 알고리즘 선택
    private static final String ALGOLISM = "HmacSHA256";
    // hash 암호화 key
    private static final String key = "nsHc6458";


    @Test
    public void hget() {

        String message = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG1kZ2pzIiwibHYiOjIyNSwiZXhwIjoxNTAyMzQzMTQzNH0=";

        try {
            // hash 알고리즘과 암호화 key 적용
            Mac hasher = Mac.getInstance(ALGOLISM);
            hasher.init(new SecretKeySpec(key.getBytes(), ALGOLISM));

            // messages를 암호화 적용 후 byte 배열 형태의 결과 리턴
            byte[] hash = hasher.doFinal(message.getBytes());
            System.out.println( byteToString(hash));
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
        }
    }

    // byte[]의 값을 16진수 형태의 문자로 변환하는 함수
    private static String byteToString(byte[] hash) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            int d = hash[i];
            d += (d < 0)? 256 : 0;
            if (d < 16) {
                buffer.append("0");
            }
            buffer.append(Integer.toString(d, 16));
        }
        return buffer.toString();
    }

}
