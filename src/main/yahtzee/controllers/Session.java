package main.yahtzee.controllers;

import main.yahtzee.models.User;

public class Session {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }

    public static boolean isSessionActive() {
        return currentUser != null;
    }
}
