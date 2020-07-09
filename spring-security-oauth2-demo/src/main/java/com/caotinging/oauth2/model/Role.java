package com.caotinging.oauth2.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @program: simple-demo
 * @description:
 * @author: CaoTing
 * @date: 2020/7/9
 */
@Data
public class Role implements GrantedAuthority {

    private String roleName;

    private String roleCode;

    @Override
    public String getAuthority() {
        return roleCode;
    }

    public Role() {
    }

    public Role(String roleName, String roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }
}
