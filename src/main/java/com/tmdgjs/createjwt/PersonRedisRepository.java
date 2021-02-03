package com.tmdgjs.createjwt;

import com.tmdgjs.createjwt.Domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, String> {
}
