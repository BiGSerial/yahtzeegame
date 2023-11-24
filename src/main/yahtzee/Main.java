package main.yahtzee;

import java.util.List;
import java.util.Scanner;

import main.yahtzee.models.User;
import main.yahtzee.utils.FileUserRepository;
import main.yahtzee.controllers.Session;;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (Session.isSessionActive()) {
                System.out.println("Usuário logado: " + Session.getCurrentUser().getUsername());
                displayMenu();
            } else {
                System.out.println("Nenhum usuário logado.");
                loginOrRegister();
            }

            System.out.println("Deseja continuar (S/N)?");
            String choice = scanner.nextLine().toUpperCase();
            if (!choice.equals("S")) {
                System.out.println("Saindo...");
                break;
            }
        }
    }

    private static void loginOrRegister() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Deseja fazer login (L) ou cadastrar um novo usuário (C)?");
            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("L")) {
                User user = loginUser();
                if (user != null) {
                    Session.setCurrentUser(user);
                    break;
                }
            } else if (choice.equals("C")) {
                registerUser();
            } else {
                System.out.println("Escolha inválida. Por favor, digite 'L' para login ou 'C' para cadastro.");
            }
        }
    }

    private static User loginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu nome de usuário:");
        String username = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String password = scanner.nextLine();

        List<User> users = FileUserRepository.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPasswordHash().equals(user.getHash(password))) {
                System.out.println("Login bem-sucedido!");
                return user;
            }
        }
        System.out.println("Usuário ou senha incorretos. Tente novamente.");
        return null;
    }

    private static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite um nome de usuário para o cadastro:");
        String newUsername = scanner.nextLine();

        System.out.println("Digite a senha para o novo usuário:");
        String newPassword = scanner.nextLine();

        FileUserRepository.addUser(new User(newUsername, newPassword));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n== MENU ==\n1. Opção 1\n2. Opção 2\n3. Deslogar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (choice) {
                case 1:
                    // Lógica para a opção 1
                    System.out.println("Você escolheu a opção 1.");
                    break;
                case 2:
                    // Lógica para a opção 2
                    System.out.println("Você escolheu a opção 2.");
                    break;
                case 3:
                    Session.clearSession();
                    System.out.println("Deslogando usuário...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
