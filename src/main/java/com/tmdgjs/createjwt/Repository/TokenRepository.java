package com.tmdgjs.createjwt.Repository;

import com.tmdgjs.createjwt.Domain.Person;
import com.tmdgjs.createjwt.Domain.Token;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenRepository extends CrudRepository<Token, String> {
}
