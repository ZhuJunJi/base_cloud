package com.zhujunji.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerWebApplication.class, args);
    }

    @Bean
    public ResourceServerTokenServices resourceServerTokenServices() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId("user-microservice-web");
        tokenService.setClientSecret("123456");
        tokenService.setCheckTokenEndpointUrl("http://127.0.0.1:9098/oauth/check_token");
        return tokenService;
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Change the ROLE_ prefix
        return new GrantedAuthorityDefaults("USER_");
    }
}
