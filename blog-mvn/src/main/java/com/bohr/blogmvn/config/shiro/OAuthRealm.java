package com.bohr.blogmvn.config.shiro;

import com.bohr.blogmvn.common.entity.Base;
import com.bohr.blogmvn.entity.User;
import com.bohr.blogmvn.entity.UserStatus;
import com.bohr.blogmvn.service.UserService;
import io.netty.util.concurrent.BlockingOperationException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Shiro域对象 用于授权 及认证
 * 获取及验证权限信息
 * @author Bohr Fu
 * @date 2019/12/11 12:02
 * @Version 1.0
 */
public class OAuthRealm extends AuthorizingRealm {

    @Resource
    UserService userService;


    /**
     * 授权(验证权限时调用)
     * 获取用户权限集合
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo simpleAuorizationInfo = new SimpleAuthorizationInfo();

        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是下面SimpleAuthenticationInfo构造的时候传入的参数决定取到的结果
        //User user  = (User)principals.getPrimaryPrincipal();
        // 实际应用 这里需要根据用户查角色 根据角色查权限
//        for(SysRole role:user.getRoleList()){
//            authorizationInfo.addRole(role.getRole());
//            for(SysPermission p:role.getPermissions()){
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }

        String account = (String) principals.getPrimaryPrincipal();
        User user = userService.getUserByAccount(account);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();
        //简单处理   只有admin一个角色
        if (user.getAdmin()) {
            roles.add(Base.ROLE_ADMIN);
        }
        authorizationInfo.setRoles(roles);

        return authorizationInfo;
    }

    /**
     * 认证(登录时调用)
     * 验证用户登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 获取用户
        String userName = (String) token.getPrincipal();
        // 这里可以根据情况加缓存 如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行findByUserName方法
        //User user = userService.findByUserName(userName);
        User user = userService.getUserByAccount(userName);
        // 校验用户存在 状态 。。。。
        if (ObjectUtils.isEmpty(user)){
            throw new UnknownAccountException();
        }

//        if (UserStatus.blocked.equals(userByAccount.getStatus())){
//            throw new BlockedAccountException();
//        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                //比对的是用户名，直接传入用户名也可以，但是在上面授权部分就需要自己重新从数据库里取用户取权限
                user.getAccount(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );


        return simpleAuthenticationInfo;
    }
}
