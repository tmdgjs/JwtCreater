package com.tmdgjs.createjwt.Service;

import com.tmdgjs.createjwt.Domain.Address;
import com.tmdgjs.createjwt.Domain.Person;
import com.tmdgjs.createjwt.PersonRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class Services {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private PersonRedisRepository personRedisRepository;


    public Services( PersonRedisRepository personRedisRepository) {
        this.personRedisRepository = personRedisRepository;
    }


    public Object getList(){
        final String key = "tmdgjs1";
        final ListOperations<String, Object> stringStringListOperations = redisTemplate.opsForList();

        stringStringListOperations.rightPush(key, "A");
        stringStringListOperations.rightPush(key, "B");
        stringStringListOperations.rightPush(key, "C");
        stringStringListOperations.rightPush(key, "D");
        stringStringListOperations.rightPush(key, "D");

        stringStringListOperations.rightPushAll(key,"E","F","G");

        String str1 = (String) stringStringListOperations.index(key, 1);

        System.out.println(str1);

        final Long size = stringStringListOperations.size(key);

        System.out.println("size = " + size);

        final List<Object> ResultRange = stringStringListOperations.range(key, 0, size);

        System.out.println("ResultRange = " + Arrays.toString(ResultRange.toArray()));

        return ResultRange;
    }

    public Object getSet() {

        final String key = "tmdgjsSet";

        SetOperations<String, Object> stringObjectSetOperations = redisTemplate.opsForSet();

        stringObjectSetOperations.add(key, "H");
        stringObjectSetOperations.add(key, "e");
        stringObjectSetOperations.add(key, "1");
        stringObjectSetOperations.add(key, "2");
        stringObjectSetOperations.add(key, "3"); stringObjectSetOperations.add(key, "e");

        stringObjectSetOperations.members(key);
        Set<Object> objList1 = stringObjectSetOperations.members(key);

        System.out.println(objList1);
        final Long lngSize = stringObjectSetOperations.size(key);

        System.out.println(lngSize);

        Cursor<Object> cursor = stringObjectSetOperations.scan(key, ScanOptions.scanOptions().match("*").count(3).build());

        while(cursor.hasNext()) {
            System.out.println("cursor = " + cursor.next());
        }

        /*stringObjectSetOperations.remove(key, "H", "1");


        stringObjectSetOperations.members(key);

        Set<Object> objList2 = stringObjectSetOperations.members(key);

        System.out.println(objList2);*/



        return null;
    }

    public Object SortedSet(){

        final String key = "qwerasf";

        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add(key, "H", 1);
        zSetOperations.add(key, "e", 5);
        zSetOperations.add(key, "H", 10);
        zSetOperations.add(key, "b", 199);
        zSetOperations.add(key, "a", 1000);
        zSetOperations.add(key, "c", 10009);

        Set<Object> range = zSetOperations.range(key, 0, 6);

        System.out.println(Arrays.toString(range.toArray()));

        Long size = zSetOperations.size(key);

        System.out.println(size);


        Set<Object> range2 = zSetOperations.rangeByScore(key, 0, 1000);

        System.out.println(Arrays.toString(range2.toArray()));


        return null;
    }

    public Object Hash(){


        final String key = "hashQwer";

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        hashOperations.put(key, "qwer", "alphabet");
        hashOperations.put(key, "qwer", "alphabet2");
        hashOperations.put(key, "a", "11");
        hashOperations.put(key, "b", "22");

        Object hello = hashOperations.get(key, "qwer"); // hello2

        System.out.println("hello = " + hello);


        Long size = hashOperations.size(key);

        System.out.println("size = " + size);


        Map<Object, Object> entries = hashOperations.entries(key);
        System.out.println(entries);

        return entries;
    }

    public Person saves(Person person){


        return personRedisRepository.save(person);
    }
}
