package com.tmdgjs.createjwt.Exception;

public class JwtException extends RuntimeException{


    public JwtException(String message){
        super(message);
    }

    public JwtException(String message, Throwable throwable){
        super(message,throwable);
    }


}
