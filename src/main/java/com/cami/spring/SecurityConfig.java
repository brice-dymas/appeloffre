/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.spring;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select username,password, enabled from users where enabled=true and username=?")
                .authoritiesByUsernameQuery(
                        "select user_username, role from user_roles where user_username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/appeloffre/new").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/appeloffre/**/edit**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/appeloffre/**").authenticated()
                .antMatchers("/banque/new").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/banque/**/edit**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/banque/**").hasAnyRole("ADMIN", "COMMERCIAL")
                .antMatchers("/filiale/new").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/filiale/**/edit**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/filiale/**").hasAnyRole("ADMIN", "COMMERCIAL")
                .antMatchers("/typemateriel/new").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/typemateriel/**/edit**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/typemateriel/").hasAnyRole("ADMIN", "COMMERCIAL")
                .antMatchers("/typecaution/new").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/typecaution/**/edit**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/typecaution/**").authenticated()
                .antMatchers("/materiel/new").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/materiel/**/edit/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/materiel/**").hasAnyRole("ADMIN", "COMMERCIAL")
                .antMatchers("/caution/**").authenticated()
                .antMatchers("/user/**/edit").authenticated()
                .antMatchers("/user/**/editSimpleUser").authenticated()
                .antMatchers("/user/**/update").authenticated()
                .antMatchers("/user/**/**/updateSimpleUser").authenticated()
                .antMatchers("/user/**/show").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/index.html").authenticated()
                .antMatchers("/").authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password").and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .csrf();

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
