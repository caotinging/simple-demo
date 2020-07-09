package com.caotinging.oauth2.config;

import com.caotinging.oauth2.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @program: bank-door-control
 * @description: oauth2配置文件
 * @author: CaoTing
 * @date: 2020/6/17
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService detailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore redisTokenStore;

/*    @Autowired
    private DataSource dataSource;*/

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        /*
         * redis token 方式
         */
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(detailsService)
                .tokenStore(redisTokenStore);
    }

    /*
     * 客户端授权使用内存存储方式（属于硬编码）
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("order-client")
                .secret(passwordEncoder.encode("order-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")// 授权类型
                .accessTokenValiditySeconds(3600)// token的有效期
                .scopes("all")// 用来限制客户端访问的权限,在换取的 token 的时候会带上 scope 参数，只有在 scopes 定义内的，才可以正常换取 token。
                .and()
                .withClient("user-client")
                .secret(passwordEncoder.encode("user-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(3600).scopes("all");
    }

    /**
     * 使用数据库存储客户端授权信息的方式；
     *//*
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsServiceBuilder jcsb = clients.jdbc(dataSource);
        jcsb.passwordEncoder(passwordEncoder);
    }*/

    /**
     * 这个方法限制客户端访问认证接口的权限。
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()// 允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401。
                .checkTokenAccess("isAuthenticated()")// 允许已授权用户访问 checkToken 接口
                .tokenKeyAccess("isAuthenticated()");// 允许已授权用户访问 获取token接口

    }

    /*@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(redisTokenStore);
        return defaultTokenServices;
    }*/
}
