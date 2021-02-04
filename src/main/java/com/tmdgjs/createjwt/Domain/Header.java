package com.tmdgjs.createjwt.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class Header {

    String alg;
    String type = "JWT";

    @Builder
    public Header(String alg){
        this.alg = alg;
    }

}
