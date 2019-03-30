package com.rguzman.techstore.presentation.login;

import android.os.Bundle;

import com.rguzman.techstore.R;

import androidx.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
