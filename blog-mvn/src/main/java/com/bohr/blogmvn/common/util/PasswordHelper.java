package com.bohr.blogmvn.common.util;

import com.bohr.blogmvn.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 *
 * 用户加解密
 * MD5
 * 随机生辰salt
 * @author Bohr Fu
 * @date 2019/12/10 16:38
 * @Version 1.0
 */
public class PasswordHelper {


    /**
     * shiro的随机数生成器
     */
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public static final String algorithmName = "md5";

    public static final int hashIterations = 2;

    public static void encryptPassword(User user){

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);

    }
}
