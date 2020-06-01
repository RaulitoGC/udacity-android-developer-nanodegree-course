package com.rguzman.techstore.data.repository.user.datasource.disk;

import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.User;

import javax.inject.Inject;

import timber.log.Timber;

public class UserDiskDataSourceImpl implements UserDiskDataSource {

    private final UserPrefs userPrefs;

    @Inject
    public UserDiskDataSourceImpl(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    @Override
    public void saveUser(User user) {
        userPrefs.saveUser(user);
    }

    @Override
    public void logout() {
        userPrefs.clean();
    }
}
