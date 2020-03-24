package com.fishdemon.sbt.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义 PasswordEncoder
 * 使用了Spring Security-5.x版本，需要手动提供一个PasswordEncoder实现类类，进行密码校验
 * 否则会报错：java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
 */
@Component
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword.toString());
    }

}
