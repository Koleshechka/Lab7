package client;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import commands.*;

public class Logging {
    public static LogCommand logging(Scanner scanner) {
        String username = "";
        String password = "";

        while(true) {
            System.out.println("Введите имя пользователя.");
            username = ReadCommand.readForAdd(scanner);
            if (username.equals("")) {
                System.out.println("Вы ничего не ввели. Попробуйте еще раз.");
            } else break;;
        }

        while(true) {
            System.out.println("Введите пароль.");
            password = ReadCommand.readForAdd(scanner);
            if (password.equals("")) {
                System.out.println("Вы ничего не ввели. Попробуйте еще раз.");
            } else break;
        }

        return new LogCommand(new User(username, encryptPassword(password)));
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            //BigInteger encryptPassword = new BigInteger(1, hash);
            String encryptPassword = (new BigInteger(1, hash)).toString(16);
            while (encryptPassword.length() < 32) {
                encryptPassword = "0" + encryptPassword;
            }
            return encryptPassword;
        }catch (NoSuchAlgorithmException e) {
            System.out.println("Нет такого алгоритма");
        }return null;
    }
}
