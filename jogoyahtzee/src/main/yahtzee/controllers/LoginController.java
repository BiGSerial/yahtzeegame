package main.yahtzee.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import main.yahtzee.models.User;
import main.yahtzee.utils.FileUserRepository;

public class LoginController {
    public static boolean authenticate(String username, String password) {
        List<User> users = FileUserRepository.getAllUsers();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                // Realizar o hash da senha inserida pelo usuário
                String hashedPassword = hashPassword(password);

                // Comparar o hash gerado com o hash salvo
                if (hashedPassword.equals(user.getPasswordHash())) {
                    return true; // Senha correta
                } else {
                    return false; // Senha incorreta
                }
            }
        }

        return false; // Usuário não encontrado
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Converter bytes para representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
