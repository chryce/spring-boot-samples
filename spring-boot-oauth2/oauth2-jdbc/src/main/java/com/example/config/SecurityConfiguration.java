package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
   *
   * @return
   * @throws Exception
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    AuthenticationManager manager = super.authenticationManagerBean();
    return manager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .requestMatchers().anyRequest()
        .and()
        .authorizeRequests()
        .antMatchers("/oauth/**").permitAll();
  }
}
