package com.rguzman.techstore.data.preferences;

import com.rguzman.techstore.domain.model.User;

public interface UserPrefs {

  String USER_NAME = "techstore.user.prefs.USER_NAME";
  String USER_LAST_NAME = "techstore.user.prefs.USER_LAST_NAME";
  String USER_JOB = "techstore.user.prefs.USER_JOB";
  String USER_EMAIL = "techstore.user.prefs.USER_EMAIL";
  String USER_GITHUB = "techstore.user.prefs.USER_GITHUB";
  String USER_TOKEN = "techstore.user.prefs.USER_TOKEN";
  String USER_TOKEN_EXPIRES_TIME = "techstore.user.prefs.USER_TOKEN_EXPIRES_TIME";


  void saveUser(User user);

  User getUser();

  void clean();
}
