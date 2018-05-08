package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;
import com.google.cloud.datastore.Key;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Long createUser(User user);

    Optional<User> readUser(String userExternalId);

    Optional<Key> readUserKey(String userExternalId);

    List<User> readUsers(String userLogin);

    void updateUser(User user);

    void deleteUser(Long userId);

    Result<User> listUsers(String startCursorString);

}
