package com.IIE.Industrial_Innovation_Engine_server.tools;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordEncryptor {

    // 固定盐值（在实际应用中应使用随机盐，但根据要求需要固定盐实现可重复结果）
    private static final byte[] SALT = "fixedSecureSalt123!@#".getBytes();
    // 迭代次数（建议10万次以上）
    private static final int ITERATIONS = 100;
    // 生成的密钥长度（位）
    private static final int KEY_LENGTH = 512;

    /**
     * 加密密码（使用PBKDF2WithHmacSHA512算法）
     *
     * @param password 明文密码
     * @return Base64编码的加密结果
     */
    public static String encryptPassword(String password) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    SALT,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("加密失败", e);
        }
    }

    public static void main(String[] args) {
        String password = "passwd";
        String encrypted = encryptPassword(password);

        System.out.println("原始密码: " + password);
        System.out.println("加密结果: " + encrypted);

        // 验证示例
        String inputPassword = "passwd";
        String inputEncrypted = encryptPassword(inputPassword);
        System.out.println("验证结果: " + encrypted.equals(inputEncrypted));
    }
}
