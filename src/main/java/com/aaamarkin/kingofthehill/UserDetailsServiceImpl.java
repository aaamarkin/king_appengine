package com.aaamarkin.kingofthehill;

import com.aaamarkin.kingofthehill.daos.UserDao;
import com.aaamarkin.kingofthehill.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ServletContext servletContext;

    @Override
//    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String externalId) throws UsernameNotFoundException {

        UserDao userDao = (UserDao) servletContext.getAttribute("dao");

        Optional<User> userOpt = userDao.getUserByExternalId(externalId);

        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            User user = new User.Builder()
                    .externalId(externalId)
                    .password(externalId)
                    .creationDate((String) servletContext.getAttribute("publishedDate"))
                    .build();

            user = userDao.createUser(user);
            return user;
        }
//        throw new UsernameNotFoundException("could not find the user '"
//                + username + "'");
    }
}
