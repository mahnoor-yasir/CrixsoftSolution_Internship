package com.elm;

import com.elm.model.User;

public class AppContext {
    private static AppContext instance;
    private User currentUser;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}
