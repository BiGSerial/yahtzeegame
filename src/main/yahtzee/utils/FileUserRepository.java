package main.yahtzee.utils;

import main.yahtzee.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUserRepository {
    private static final String USERS_FILE_PATH = "users.txt";

    static {
        File file = new File(USERS_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void addUser(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE_PATH, true))) {
            writer.println(user.getUsername() + "," + user.getHash(user.getPasswordHash()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean changePassword(String username, String newPassword) {
        List<User> users = getAllUsers();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword); // Atualiza a senha usando o método em User

                writeUsersToFile(users); // Atualiza o arquivo com a nova lista
                return true; // Senha alterada com sucesso
            }
        }

        return false; // Usuário não encontrado
    }

    private static void writeUsersToFile(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE_PATH))) {
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getPasswordHash());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
