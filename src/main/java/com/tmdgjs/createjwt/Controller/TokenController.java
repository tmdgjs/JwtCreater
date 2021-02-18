package com.tmdgjs.createjwt.Controller;

import com.tmdgjs.createjwt.Domain.Header;
import com.tmdgjs.createjwt.Request.JWTRequest;
import com.tmdgjs.createjwt.Request.TokenSaveRequest;
import com.tmdgjs.createjwt.Response.DefaultResponse;
import com.tmdgjs.createjwt.Service.JwtCreateService;
import com.tmdgjs.createjwt.Service.JwtInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwts")
public class TokenController {

    private static final int STATUS_CODE_OK = HttpStatus.OK.value();
    private static final int STATUS_CODE_BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

    @Autowired
    private JwtCreateService jwtCreateService;

    @Autowired
    private JwtInfoService jwtInfoService;

    // Jwt 생성
    @PostMapping
    public ResponseEntity<DefaultResponse> makeJwtCreate1(@RequestBody JWTRequest jwtRequest){

        // header 암호화
        String encryptHeader = this.jwtCreateService.convertHeader(Header.builder()
                                                                         .alg(jwtRequest.getAlgo())
                                                                         .build());

        //payload 암호화
        String convertedPayload = jwtCreateService.convertPayload(jwtRequest.getData());

        //Signature
        String headerPlusPayload = encryptHeader + "." + convertedPayload;
        DefaultResponse resSignature = jwtCreateService.getSignature(jwtRequest, headerPlusPayload);

        if(resSignature.getCode() != STATUS_CODE_OK){
            return new ResponseEntity<>(resSignature, HttpStatus.BAD_REQUEST);
        }

        String jwtToken = headerPlusPayload + "." + resSignature.getData();

        return new ResponseEntity<>(DefaultResponse.builder()
                                                   .code(STATUS_CODE_OK)
                                                   .data(jwtToken)
                                                   .build(), HttpStatus.OK);
    }

    // Jwt 저장
    @PostMapping("/stores")// /jwts
    public ResponseEntity<DefaultResponse> saveJwt(@RequestBody TokenSaveRequest tokenSaveRequest){

        DefaultResponse objResponse = jwtInfoService.saveJwt(tokenSaveRequest);

        return new ResponseEntity<>(objResponse, HttpStatus.OK);

    }

    // 저장된 Jwt 리스트
    @GetMapping // jwts
    public ResponseEntity<DefaultResponse> getJwtList(){

        DefaultResponse objResponse = jwtInfoService.getJwtList();

        return new ResponseEntity<>(objResponse, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getJwtInfo(@PathVariable("id") String id){

        id = id.replace("item","");

        DefaultResponse objResponse = jwtInfoService.getJwtInfo(id);

        if(objResponse.getCode() == STATUS_CODE_BAD_REQUEST)
            return new ResponseEntity<>(objResponse, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(objResponse, HttpStatus.OK);

    }

    /*// Jwt 생성 2
    @PostMapping("/v2")
    public ResponseEntity<DefaultResponse> makeJwtCreate2(@RequestBody JWTRequest jwtRequest){
        DefaultResponse objResponse = jwtCreateService.createJJwtToken(jwtRequest);

        if(objResponse.getCode() == STATUS_CODE_BAD_REQUEST)
            return new ResponseEntity<>(objResponse, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new TokenResponse(STATUS_CODE_OK, objResponse.getData(), true), HttpStatus.OK);
    }*/


}
