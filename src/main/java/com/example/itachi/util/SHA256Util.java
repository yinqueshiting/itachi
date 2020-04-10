package com.example.itachi.util;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @description: SHA256加密
 * @create: 2019-10-17 17:53
 **/
public class SHA256Util {
//*         私有构造器 *

    private SHA256Util(){};

    /**
     *
     * @return 生成密码需要的盐
     */
    public static String getSalt(){
        return RandomStringUtils.randomAlphabetic(16);
    }
    //对密码+盐进行哈希运算
    public static String sha256(String salt,String pwdPlain) {
        return Hashing.sha256().newHasher().putString(salt + pwdPlain, Charsets.UTF_8).hash().toString();
    }

    public static void main(String[] args) {
        String salt = getSalt();
        System.out.println(salt);
        System.out.println(sha256(salt,"123456"));
    }

}

