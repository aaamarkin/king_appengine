package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;

import java.util.List;

public interface UserDao {

    Long createUser(User user);

    User readUser(Long userId);

    List<User> readUsers(String userLogin);

    void updateUser(User user);

    void deleteUser(Long userId);

    Result<User> listUsers(String startCursorString);

}
