//package com.aaamarkin.kingofthehill;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.*;
//
//import javax.sql.DataSource;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Arrays;
//
//@Configuration
//@EnableAuthorizationServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Import(ServerSecurityConfig.class)
//public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {
//
////    @Autowired
////    @Qualifier("dataSource")
////    private DataSource dataSource;
//
//    String applicationName = "bookmarks";
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private PasswordEncoder oauthClientPasswordEncoder;
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore( accessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("123");
//        converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
//        return converter;
//    }
//
//    @Bean
//    public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
//        return new DelegatingJwtClaimsSetVerifier(Arrays.asList(
//                issuerClaimVerifier(), customJwtClaimVerifier()));
//    }
//
//    @Bean
//    public JwtClaimsSetVerifier issuerClaimVerifier() {
//        try {
//            return new IssuerClaimVerifier(new URL("http://localhost:8081"));
//        } catch (final MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public JwtClaimsSetVerifier customJwtClaimVerifier() {
//        return new CustomClaimVerifier();
//    }
//
//    @Bean
//    public TokenEnhancer tokenEnhancer() {
//        return new CustomTokenEnhancer();
//    }
//
//    @Bean
//    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
//        return new OAuth2AccessDeniedHandler();
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(oauthClientPasswordEncoder);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////        clients.jdbc(dataSource);
//        clients.inMemory()
//                .withClient("iOs-" + applicationName)
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .authorities("ROLE_USER")
//                .scopes("write")
//                .resourceIds(applicationName)
//                .secret("123456");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(
//                Arrays.asList(tokenEnhancer(), accessTokenConverter()));
//
//        endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
//    }
//}