package com.tmdgjs.createjwt.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
public class DefaultResponse {

    int code;

    String msg;

    Object data;


    public DefaultResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public DefaultResponse(int code, String msg){ // exception
        this.code = code;
        this.msg = msg;
    }

}
