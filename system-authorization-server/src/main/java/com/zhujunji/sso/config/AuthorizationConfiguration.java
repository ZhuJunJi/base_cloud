package com.zhujunji.sso.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
/**
 * ?????????
 * {@link AuthorizationServerEndpointsConfiguration#tokenEndpoint()}
 * {@link AuthorizationServerEndpointsConfiguration#authorizationEndpoint()}
 *
 * ?????? {@link ClientDetailsService}->{@link com.zhujunji.sso.service.SSOUserDetailService}
 * {@link AuthorizationServerEndpointsConfiguration#clientDetailsService}
 *
 * {@link TokenEndpoint#postAccessToken(Principal, Map)}
 * {@link TokenGranter#grant(String, TokenRequest)} TokenGranter ?????? {@link AuthorizationServerEndpointsConfigurer#tokenGranter()}
 *
 * ????????? TokenGranter ???????????????????????? grantType ???????????????????????????
 * {@link AuthorizationServerEndpointsConfigurer#tokenGranter()}
 *
 * {@link CompositeTokenGranter#grant(String, TokenRequest)}
 *      ?????? {@link CompositeTokenGranter}
 *      {@link AuthorizationServerEndpointsConfigurer#getDefaultTokenGranters()}
 *      {@link CompositeTokenGranter#CompositeTokenGranter(List)}
 * ??????grantType = "password"
 * {@link ResourceOwnerPasswordTokenGranter#grant(String, TokenRequest)}
 * {@link ResourceOwnerPasswordTokenGranter#getAccessToken(ClientDetails, TokenRequest)}
 * {@link ResourceOwnerPasswordTokenGranter#getOAuth2Authentication(ClientDetails, TokenRequest)}
 *
 * {@link ProviderManager#authenticate(Authentication)}
 *      ??????????????? {@link AuthorizationServerConfiguration#configure(AuthorizationServerEndpointsConfigurer)}
 *      {@link AuthorizationServerEndpointsConfigurer#authenticationManager(AuthenticationManager)}
 *
 *      ??????????????? {@link SecurityConfiguration#authenticationManagerBean()}
 *      {@link WebSecurityConfigurerAdapter#setApplicationContext(ApplicationContext)}
 *      {@link WebSecurityConfigurerAdapter.DefaultPasswordEncoderAuthenticationManagerBuilder#DefaultPasswordEncoderAuthenticationManagerBuilder(ObjectPostProcessor, PasswordEncoder)}
 *      {@link WebSecurityConfiguration#springSecurityFilterChain()}
 *      {@link WebSecurity#doBuild()}
 *      {@link AbstractConfiguredSecurityBuilder#doBuild()}
 *      {@link AbstractConfiguredSecurityBuilder#init()}
 *      {@link WebSecurityConfigurerAdapter#init(WebSecurity)}
 *      {@link WebSecurityConfigurerAdapter#getHttp()}
 *
 *      {@link WebSecurityConfigurerAdapter#authenticationManager()}
 *      ProviderManager parent
 *      {@link WebSecurityConfigurerAdapter.DefaultPasswordEncoderAuthenticationManagerBuilder#parentAuthenticationManager(AuthenticationManager)}
 *
 *      {@link WebSecurityConfigurerAdapter.AuthenticationManagerDelegator#authenticate(Authentication)}
 * ProviderManager parent#authenticate(Authentication)
 * {@link DaoAuthenticationProvider#authenticate(Authentication)}
 * {@link DaoAuthenticationProvider#additionalAuthenticationChecks(UserDetails, UsernamePasswordAuthenticationToken)}
 *
 * ????????????
 * {@link PasswordEncoder#matches(CharSequence, String)}
 *      ??????????????? {@link AuthorizationConfiguration#passwordEncoder()}
 *      {@link BCryptPasswordEncoder#matches(CharSequence, String)}
 *
 * ?????????????????????
 */
@Component
public class AuthorizationConfiguration {

    /**
     * {@link DaoAuthenticationProvider#additionalAuthenticationChecks(UserDetails, UsernamePasswordAuthenticationToken)}
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("123456");
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
