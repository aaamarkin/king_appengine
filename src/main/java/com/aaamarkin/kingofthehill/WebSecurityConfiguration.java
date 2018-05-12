//package com.aaamarkin.kingofthehill;
//
//import com.aaamarkin.kingofthehill.daos.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import javax.servlet.ServletContext;
//
//@Configuration
//public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter
//{
//
//    @Autowired
//    ServletContext servletContext;
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Bean
//    UserDetailsService userDetailsService() {
//
//        UserDao userDao = (UserDao) servletContext.getAttribute("dao");
//
//        System.out.println("userDetailsService");
//        return (userExternalId) -> userDao
//                .getUserByExternalId(userExternalId)
//                .map(a -> new User( a.getExternalId(), a.getPassword(), true, true, true, true,
//                        AuthorityUtils.createAuthorityList("USER", "write")))
//                .orElseThrow(
//                        () -> new UsernameNotFoundException("could not find the user '"
//                                + userExternalId + "'"));
//
//    }
//}