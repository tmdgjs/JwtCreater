package com.tmdgjs.createjwt.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class DefaultResponse {

    int code;

    String msg;

    Object data;

    @Builder
    public DefaultResponse(int code, Object data) {
        this.code = code;
        this.data = data;
        this.msg = "";
    }

    public DefaultResponse(int code, String msg){ // exception
        this.code = code;
        this.msg = msg;
    }

}
