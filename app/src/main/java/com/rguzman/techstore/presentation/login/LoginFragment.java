package com.rguzman.techstore.presentation.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.rguzman.techstore.R;
import com.rguzman.techstore.presentation.analytics.Analytics;
import com.rguzman.techstore.presentation.category.CategoryListActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

public class LoginFragment extends DaggerFragment {

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

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(Objects.requireNonNull(getContext()));
        MobileAds.initialize(getContext(), getString(R.string.add_mob_ID));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        this.loginViewModel.getStatus().observe(this, this::handleStatus);
        this.loginViewModel.getUser().observe(this, user -> {
            Analytics.loginEvent(firebaseAnalytics, user);
            startActivity(CategoryListActivity.getCallingIntent(getContext()));
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClick() {
        hideKeyboardFrom(Objects.requireNonNull(getContext()), loginButton);
        loginButton.setEnabled(false);
        String email = Objects.requireNonNull(emailInput.getText()).toString();
        String password = Objects.requireNonNull(passwordInput.getText()).toString();
        loginViewModel.login(email, password);
        
    }

    private void handleStatus(LoginStatus userStatus) {
        switch (userStatus) {
            case GENERIC_ERROR:
                showError(getString(R.string.message_exception_generic));
                break;
            case NETWORK_CONNECTION:
                showError(getString(R.string.message_exception_network_connection));
                break;
            case HIDE_LOADING:
                hideLoading();
                break;
            case SHOW_LOADING:
                showLoading();
                break;
            case VALID_INPUTS:
                setValidInputs();
                break;
            case INVALID_EMAIL:
                showMessageEmailInvalid();
                break;
            case INVALID_PASSWORD:
                showMessagePasswordError();
                break;
        }
    }

    public void showError(String message) {
        loginButton.setEnabled(true);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    private void showMessageEmailInvalid() {

        hideLoading();
        loginButton.setEnabled(true);
        emailInput.setError(getString(R.string.message_exception_valid_email));
    }

    private void showMessagePasswordError() {
        hideLoading();
        loginButton.setEnabled(true);
        passwordInput.setError(getString(R.string.message_exception_valid_password));
    }

    private void setValidInputs() {
        emailInput.setError(null);
        passwordInput.setError(null);
    }

    private void showLoading() {
        progressBarContainer.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBarContainer.setVisibility(View.GONE);
    }

    private void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
