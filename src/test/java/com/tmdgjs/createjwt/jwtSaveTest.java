package com.tmdgjs.createjwt;

import com.tmdgjs.createjwt.Domain.Address;
import com.tmdgjs.createjwt.Domain.Person;
import com.tmdgjs.createjwt.Domain.Token;
import com.tmdgjs.createjwt.Repository.TokenRepository;
import com.tmdgjs.createjwt.Service.Services;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class jwtSaveTest {

    @Autowired
    private TokenRepository tokenRepository;

    Services userService;



    @BeforeEach
    public void init() {

        userService = new Services(tokenRepository);

    }


    @Test
    public void jwtSave(){

        String uuid = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        String nowString = now.format(dateTimeFormatter);   // 결과 : 2016년 4월 2일 오전 1시 4분

        Token token = new Token(uuid, "qwereqwrqewrqewrwqerqwerwqerwqerqw" , nowString, false);

        /*PersonRedisRepository repo = Mockito.mock(PersonRedisRepository.class);
        Mockito.when(repo.save(person));

        Services userService = new Services(repo);*/

        // when
        Token savedPerson = userService.saves(token);

        System.out.println(savedPerson.toString());

        // then
        //Optional<Person> findPerson = redisRepository.findById(savedPerson.getId());




    }
}
