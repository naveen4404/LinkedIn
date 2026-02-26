package com.linkedin.linkedin.features.authentication.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Encoder {

    public String encode(String rawString){

        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(rawString.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error encoding string",e);
        }
    }

    public boolean match(String rawString, String encodeString){
        return encode(rawString).equals(encodeString);
    }

}
