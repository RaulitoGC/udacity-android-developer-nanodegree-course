package com.rguzman.techstore.presentation.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.presentation.category.CategoryListActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

public class LoginFragment extends DaggerFragment implements LoginView {

    @BindView(R.id.input_email)
    AppCompatEditText emailInput;

    @BindView(R.id.input_password)
    AppCompatEditText passwordInput;

    @BindView(R.id.btn_login)
    AppCompatButton loginButton;

    @BindView(R.id.progressContainer)
    View progressBarContainer;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel loginViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        this.loginViewModel.setView(this);
        this.loginViewModel.init();
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClick() {
        hideKeyboardFrom(context(), loginButton);
        loginButton.setEnabled(false);
        String email = Objects.requireNonNull(emailInput.getText()).toString();
        String password = Objects.requireNonNull(passwordInput.getText()).toString();
        loginViewModel.login(email, password);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showError(String message) {
        loginButton.setEnabled(true);
        Toast.makeText(context(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess(User user) {
        startActivity(CategoryListActivity.getCallingIntent(context()));
    }

    @Override
    public void showMessageEmailInvalid() {
        loginButton.setEnabled(true);
        emailInput.setError(getString(R.string.message_exception_valid_email));
    }

    @Override
    public void showMessagePasswordError() {
        loginButton.setEnabled(true);
        passwordInput.setError(getString(R.string.message_exception_valid_password));
    }

    @Override
    public void setValidInputs() {
        emailInput.setError(null);
        passwordInput.setError(null);
    }

    @Override
    public void showLoading() {
        progressBarContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBarContainer.setVisibility(View.GONE);
    }

    private void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
