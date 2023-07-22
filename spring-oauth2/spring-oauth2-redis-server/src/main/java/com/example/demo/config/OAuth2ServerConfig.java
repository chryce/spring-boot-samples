package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class OAuth2ServerConfig {

  private static final String DEMO_RESOURCE_ID = "api";

  @Configuration
  @EnableResourceServer
  public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
      resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .requestMatchers()
          .antMatchers("/api/**");
    }

  }

  @Configuration
  @EnableAuthorizationServer
  public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints
          .tokenStore(tokenStore())
          .authenticationManager(authenticationManager)
          .userDetailsService(userDetailsService)
          .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
      endpoints.reuseRefreshTokens(true);
    }

    @Bean
    public TokenStore tokenStore() {
      RedisTokenStore redis = new RedisTokenStore(connectionFactory);
      return redis;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
      //配置两个客户端,一个用于password认证一个用于client认证
      clients.inMemory().withClient("client_1")
          .resourceIds(DEMO_RESOURCE_ID)
          .authorizedGrantTypes("client_credentials", "refresh_token")
          .scopes("select")
          .authorities("oauth2")
          .secret(finalSecret)

          .and().withClient("client_2")
          .resourceIds(DEMO_RESOURCE_ID)
          .authorizedGrantTypes("password", "refresh_token")
          .scopes("select")
          .authorities("oauth2")
          .secret(finalSecret);

    }

    /**
     * Note：允许表单认证
     *
     * @param oauthServer
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
      oauthServer.allowFormAuthenticationForClients();
    }
  }
}


