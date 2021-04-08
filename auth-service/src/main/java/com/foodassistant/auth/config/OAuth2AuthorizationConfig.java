package com.foodassistant.auth.config;

import com.foodassistant.auth.service.security.ApplicationUserDetailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore = new InMemoryTokenStore();

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;

    private final ApplicationUserDetailService userDetailsService;

    private final Environment env;

    public OAuth2AuthorizationConfig(AuthenticationManager authenticationManager, ApplicationUserDetailService userDetailsService, Environment env) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.env = env;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO persist clients details
        clients.inMemory()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("ui")
                .and()
                .withClient("account-service")
                .secret(env.getProperty("ACCOUNT_SERVICE_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
                .and()
                .withClient("statistics-service")
                .secret(env.getProperty("STATISTICS_SERVICE_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
                .and()
                .withClient("notification-service")
                .secret(env.getProperty("NOTIFICATION_SERVICE_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}
