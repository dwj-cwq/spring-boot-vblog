package com.dwj.vblogold.shiro;


import com.dwj.vblogold.entity.UserEntity;
import com.dwj.vblogold.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public class MyRealm extends AuthenticatingRealm {

    @Resource
    @Lazy
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();

        UserEntity user = userService.queryUserByName(username);

        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        String password = user.getUserPassword()  ;

        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
