package com.tmdgjs.createjwt.Service;

import com.tmdgjs.createjwt.Domain.Token;
import com.tmdgjs.createjwt.Repository.TokenRepository;
import com.tmdgjs.createjwt.Request.TokenSaveRequest;
import com.tmdgjs.createjwt.Response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtInfoService {

    @Autowired
    private TokenRepository tokenRepository;

    public DefaultResponse saveJwt(TokenSaveRequest tokenSaveRequest) {

        Integer tokenId = getJwtListCount();

        Token insertToken = Token.builder()
                           .id(String.valueOf(tokenId + 1))
                           .token(tokenSaveRequest.getToken())
                           .time(getToday())
                           .isSecretKeyEncode(tokenSaveRequest.getIsSecretKeyEncode())
                           .build();

        Token savedToken = tokenRepository.save(insertToken);

        return DefaultResponse.builder().code(200).data(savedToken).build();
    }

    private Integer getJwtListCount() {

        List<Token> objTokenList = (List<Token>) tokenRepository.findAll();

        return objTokenList.size();

    }

    public String getToday(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        return now.format(dateTimeFormatter);
    }

    public DefaultResponse getJwtList() {
        List<Token> objTokenList = (List<Token>) tokenRepository.findAll();

        Collections.reverse(objTokenList);
        return  DefaultResponse.builder().code(200).data(objTokenList).build();


    }

}
