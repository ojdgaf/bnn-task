package com.tasks.bnn.config;

import com.tasks.bnn.config.jwt.ActiveDirectoryJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ActiveDirectoryJwtFilter activeDirectoryJwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                .addFilterAfter(activeDirectoryJwtFilter, CorsFilter.class)
                .cors().and().csrf().disable();
    }
}
