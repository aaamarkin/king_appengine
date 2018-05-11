//package com.aaamarkin.kingofthehill;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import java.util.Map;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    private static final String RESOURCE_ID = "resource-server-rest-api";
//    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
//    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
//    private static final String SECURED_PATTERN = "/secured/**";
//
//    @Autowired
//    private CustomAccessTokenConverter customAccessTokenConverter;
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setAccessTokenConverter(customAccessTokenConverter);
//        return converter;
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
//                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
//                .anyRequest().access(SECURED_READ_SCOPE);
//    }
//
//    public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
//        OAuth2AuthenticationDetails details
//                = (OAuth2AuthenticationDetails) auth.getDetails();
//        OAuth2AccessToken accessToken = tokenStore()
//                .readAccessToken(details.getTokenValue());
//        return accessToken.getAdditionalInformation();
//    }
//}