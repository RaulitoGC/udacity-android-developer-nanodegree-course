package com.rguzman.techstore.data.preferences;

import android.content.SharedPreferences;

import com.rguzman.techstore.domain.model.User;

import javax.inject.Inject;

import timber.log.Timber;

public class UserPrefsImpl implements UserPrefs {

  private final SharedPreferences preferences;

  @Inject
  public UserPrefsImpl(SharedPreferences preferences) {
    this.preferences = preferences;
  }

  @Override
  public void saveUser(User user) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(USER_NAME, user.getName());
    editor.putString(USER_LAST_NAME, user.getLastName());
    editor.putString(USER_TOKEN, user.getToken());
    editor.putString(USER_EMAIL, user.getEmail());
    editor.putString(USER_JOB, user.getJob());
    editor.putString(USER_GITHUB, user.getGithub());
    editor.putLong(USER_TOKEN_EXPIRES_TIME, user.getExpires());
    editor.apply();
  }

  @Override
  public User getUser() {
    User user = new User();
    String name = preferences.getString(USER_NAME, "");
    String lastName = preferences.getString(USER_LAST_NAME, "");
    String token = preferences.getString(USER_TOKEN, "");
    String email = preferences.getString(USER_EMAIL, "");
    String job = preferences.getString(USER_JOB, "");
    String github = preferences.getString(USER_GITHUB, "");
    long expires = preferences.getLong(USER_TOKEN_EXPIRES_TIME, 0L);
    user.setName(name);
    user.setLastName(lastName);
    user.setToken(token);
    user.setEmail(email);
    user.setJob(job);
    user.setGithub(github);
    user.setExpires(expires);
    return user;
  }

  @Override
  public void clean() {
    preferences.edit().clear().apply();
  }
}
