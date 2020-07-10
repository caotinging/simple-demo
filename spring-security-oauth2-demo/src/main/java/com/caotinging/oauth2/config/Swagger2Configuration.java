package com.caotinging.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

/**
 * @program: bank-door-control
 * @description:
 * @author: CaoTing
 * @date: 2020/7/6
 */
@Configuration
@EnableSwagger2()
public class Swagger2Configuration {

    @Value("${config.swagger.enable}")
    private boolean swaggerEnable = false;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                /*.select()
                .apis(RequestHandlerSelectors.basePackage("com.caotinging.oauth2"))
                .paths(PathSelectors.any())
                .build()*/
                .enable(swaggerEnable)
                .groupName("Swagger")
                .securitySchemes(Collections.singletonList(securitySchemes()))
                .securityContexts(Collections.singletonList(securityContexts()));
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("simple-demo-oauth2")
                // 描述
                .description("simple-demo-oauth2 API文档")
                // 作者信息
                .contact(new Contact("caotinging", "https://www.cnblogs.com/tinging/", "caotinging@outlook.com"))
                // 版本
                .version("版本号: 1.0.0")
                .build();
    }

    /**
     * 认证方式使用密码模式
     */
    private SecurityScheme securitySchemes() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("/oauthdemo/oauth/token");

        return new OAuthBuilder()
                .name("Authorization")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    /**
     * 设置 swagger2 认证的安全上下文
     */
    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * 允许认证的scope
     */
    private AuthorizationScope[] scopes() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[4];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");
        authorizationScopes[3] = new AuthorizationScope("all", "all");
        return authorizationScopes;
    }
}
