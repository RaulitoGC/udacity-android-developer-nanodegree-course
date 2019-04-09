package com.rguzman.techstore.presentation.analytics;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rguzman.techstore.domain.model.User;

public class Analytics {

    public static void loginEvent(FirebaseAnalytics firebaseAnalytics, User user) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user.getToken());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, user.getName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }
}
