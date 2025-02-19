package ru.astongroup.usermanagement.utils.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {

    private static String salt = BCrypt.gensalt();

    public static String createPasswordHash(String password) {

        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPasswordHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
