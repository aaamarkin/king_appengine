package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;
import com.google.cloud.datastore.Key;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User createUser(User user);

    Optional<User> getUserByExternalId(String userExternalId);

    User getUserById(Long id);

    Optional<Key> getUserKeyByExternalId(String userExternalId);

    List<User> readUsers(String userLogin);

    void updateUser(User user);

    void deleteUser(Long userId);

    Result<User> listUsers(String startCursorString);

}
