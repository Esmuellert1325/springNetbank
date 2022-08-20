package com.app.netbank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
        httpSec
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/index").hasAnyRole("USER", "ADMIN")
                .antMatchers("/transactions").hasRole("USER")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/transfer").hasRole("USER")
                .antMatchers("/transError").hasRole("USER")
                .antMatchers("/transSuccess").hasRole("USER")
                .antMatchers("/transCancel").hasAnyRole("USER", "ADMIN")
                .antMatchers("/adminTransactions").hasRole("ADMIN")
                .antMatchers("/userNotFound").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email").permitAll().defaultSuccessUrl("/index", true)
                .and()
                .logout().logoutSuccessUrl("/login?logout").permitAll();
    }
}
