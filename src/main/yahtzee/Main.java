package main.yahtzee;

import java.util.List;
import java.util.Scanner;

import main.yahtzee.models.User;
import main.yahtzee.utils.FileUserRepository;

public class Main {
    public static void main(String[] args) {
        User currentUser = getCurrentUser(); // Verifica se há algum usuário logado

        if (currentUser == null) {
            // Se não houver usuário logado, solicita o login ou cadastro
            currentUser = loginOrRegister();
        }

        // Exemplo de como utilizar o usuário logado (currentUser)
        if (currentUser != null) {
            System.out.println("Usuário logado: " + currentUser.getUsername());
        } else {
            System.out.println("Nenhum usuário logado.");
        }
    }

    private static User getCurrentUser() {
        // Implementação simplificada para verificar se há um usuário logado.
        // Aqui você pode implementar a lógica real para verificar se há um usuário logado.
        // Pode ser verificado se existe uma sessão de usuário ativa, por exemplo.
        // Neste exemplo, estamos apenas verificando se há usuários no arquivo.

        List<User> users = FileUserRepository.getAllUsers();
        if (!users.isEmpty()) {
            // Retorna o primeiro usuário como usuário logado (poderia ser uma lógica mais complexa)
            return users.get(0);
        }
        return null;
    }

    private static User loginOrRegister() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Nenhum usuário logado. Deseja fazer login (L) ou cadastrar um novo usuário (C)?");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("L")) {
                // Solicitar informações de login
                System.out.println("Digite seu nome de usuário:");
                String username = scanner.nextLine();

                System.out.println("Digite sua senha:");
                String password = scanner.nextLine();

                // Verificar se as credenciais estão corretas
                List<User> users = FileUserRepository.getAllUsers();
                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPasswordHash().equals(user.getHash(password))) {
                        System.out.println("Login bem-sucedido!");
                        return user;
                    }
                }
                System.out.println("Usuário ou senha incorretos. Tente novamente.");

            } else if (choice.equals("C")) {
                // Cadastrar um novo usuário
                System.out.println("Digite um nome de usuário para o cadastro:");
                String newUsername = scanner.nextLine();

                System.out.println("Digite a senha para o novo usuário:");
                String newPassword = scanner.nextLine();

                // Adicionar o novo usuário
                FileUserRepository.addUser(new User(newUsername, newPassword));
                System.out.println("Usuário cadastrado com sucesso!");

                return FileUserRepository.getAllUsers().stream()
                        .filter(u -> u.getUsername().equals(newUsername))
                        .findFirst()
                        .orElse(null);
            } else {
                System.out.println("Escolha inválida. Por favor, digite 'L' para login ou 'C' para cadastro.");
            }
        }
    }
}
