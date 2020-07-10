package com.caotinging.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: bank-door-control
 * @description:
 * @author: CaoTing
 * @date: 2020/6/17
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密工具类，它可以实现不可逆的加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 为了实现 OAuth2 的 password 模式必须要指定的授权管理 Bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 允许匿名访问所有接口 主要是 oauth 接口
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/static/css/**",
                "/static/js/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/webjars/**",
                "**/favicon.ico",
                "/index");
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().mvcMatchers("/swagger-ui.html/**",
                "/configuration/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/static/css/**",
                "/static/js/**",
                "/webjars/**");
    }
}
