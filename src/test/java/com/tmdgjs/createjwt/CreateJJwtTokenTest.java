package com.tmdgjs.createjwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Optional;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreateJJwtTokenTest {


    static String SECRET_KEY = "test";

    @Test
    public void jjwtTest(){



        //given

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512; // hamc sha 256

        //byte[] secretKeyByte = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signkey = new SecretKeySpec(SECRET_KEY.getBytes(), signatureAlgorithm.getJcaName());

        //when


        String strJJwtToken = Jwts.builder()
                .setSubject("qqqqqq")
                .signWith(signatureAlgorithm, signkey)
                .compact();



        //then

        System.out.println(strJJwtToken);
        //assertEquals(a,b);

    }
}
