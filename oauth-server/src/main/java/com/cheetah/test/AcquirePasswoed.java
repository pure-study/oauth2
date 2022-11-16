package com.cheetah.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 根据明文获取密文
 */
public class AcquirePasswoed {

    public static void main(String[] args) {
        String password = "123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        System.out.println(encode);
    }

}
