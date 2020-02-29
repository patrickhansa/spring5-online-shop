package com.ecommerce.spring5onlineshop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/product/new", "/product/{\\d+}/update")
                        .access("hasRole('ROLE_USER')")
                    .antMatchers("/", "/index")
                        .access("permitAll")
                .and()
                    .formLogin()
                        .loginPage("/login")
                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2-console/**")
                .and()
                    .headers()
                        .frameOptions()
                            .sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .inMemoryAuthentication()
                .withUser("buzz")
                .password("{noop}infinity")
                .authorities("ROLE_USER")
                .and()
                .withUser("woody")
                .password("{noop}bullseye")
                .authorities("ROLE_USER");
    }
}
