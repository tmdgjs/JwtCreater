package com.tmdgjs.createjwt.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
@RedisHash("Token")
public class Token implements Serializable {

    private static final Long serialVersionUID  = 1L;

    @Id
    String id;    // uuid
    String token;
    String time;
    Boolean isSecretKeyEncode;

    @Builder
    public Token(String id, String token, String time, Boolean isSecretKeyEncode){
        this.id = id;
        this.token = token;
        this.time = time;
        this.isSecretKeyEncode = isSecretKeyEncode;
    }

}
