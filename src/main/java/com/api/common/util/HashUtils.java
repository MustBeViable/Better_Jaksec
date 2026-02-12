package com.api.common.util;

import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

public class HashUtils {

    private static final BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

    public HashUtils(){
    }

    public static String hash(String hashable){
        return Password.hash(hashable).with(bcrypt).getResult();
    }

    public static boolean check(String plainText, String hashed){
        return Password.check(plainText,hashed).with(bcrypt);
    }
}
