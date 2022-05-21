package com.aqua.anroid.policynoticeapp;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SHA512_Hash_Incode {

    public String SHA512_Hash_Incode(String pwd){
        try{
            String hexSapassword = pwd;
            String mixPassword = hexSapassword;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.reset();
            messageDigest.update(mixPassword.getBytes("utf8"));
            String enPassword = String.format("%0128x",new BigInteger(1, messageDigest.digest()));

            return enPassword;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}