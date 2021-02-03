package com.tmdgjs.createjwt;

import com.tmdgjs.createjwt.Domain.Address;
import com.tmdgjs.createjwt.Domain.Person;
import com.tmdgjs.createjwt.Service.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryTestRedis {

    @Autowired
    private PersonRedisRepository redisRepository;

    Services userService;

    @BeforeEach
    public void init() {

        userService = new Services(redisRepository);

    }


    @Test
    public void basicSave() {

        // given
        Address address = new Address("서울특별시 서울시청");
            Person person = new Person("qwer", "한", "헌", address);
        /*PersonRedisRepository repo = Mockito.mock(PersonRedisRepository.class);
        Mockito.when(repo.save(person));

        Services userService = new Services(repo);*/

        // when
        Person savedPerson = userService.saves(person);

        System.out.println(savedPerson.toString());

        // then
        //Optional<Person> findPerson = redisRepository.findById(savedPerson.getId());

        assertEquals(savedPerson.getFirstname(),"한");

/*
        assertEquals(findPerson.get().getFirstname(), person.getFirstname());*/
    }


}
