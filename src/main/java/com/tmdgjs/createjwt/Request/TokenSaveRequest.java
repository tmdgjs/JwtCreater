package com.tmdgjs.createjwt.Request;

import lombok.Getter;

@Getter
public class TokenSaveRequest {

    String token;

    Boolean isSecretKeyEncode; // button Type

}
