package com.rguzman.techstore.data.repository.user.datasource.disk;

import com.rguzman.techstore.domain.model.User;

public interface UserDiskDataSource {

    void saveUser(User user);
}
