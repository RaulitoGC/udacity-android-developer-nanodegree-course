package com.rguzman.techstore.presentation.login;

import androidx.lifecycle.Observer;

import com.rguzman.techstore.domain.model.User;

public class UserObserver implements Observer<User> {

    interface Listener {
        void onChanged(User user);
    }

    private Listener listener;

    public UserObserver(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onChanged(User user) {
        listener.onChanged(user);
    }
}
