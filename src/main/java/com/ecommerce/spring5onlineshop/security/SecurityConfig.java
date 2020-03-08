package com.ecommerce.spring5onlineshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.authorities-query}")
    private String authoritiesQuery;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/product/new", "/product/{\\d+}/update")
                        .access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/", "/index")
                        .access("permitAll")
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/product/showAll")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2-console/**")
                .and()
                    .headers()
                        .frameOptions()
                            .sameOrigin();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(usersQuery)
                    .authoritiesByUsernameQuery(authoritiesQuery)
                    .passwordEncoder(encoder());
    }
}
