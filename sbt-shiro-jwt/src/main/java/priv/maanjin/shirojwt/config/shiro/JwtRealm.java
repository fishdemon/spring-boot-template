package priv.maanjin.shirojwt.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import priv.maanjin.shirojwt.config.permission.TokenCache;
import priv.maanjin.shirojwt.entity.SysRole;
import priv.maanjin.shirojwt.mapper.PermissionMapper;
import priv.maanjin.shirojwt.mapper.RoleMapper;
import priv.maanjin.shirojwt.service.UserService;
import priv.maanjin.shirojwt.util.JwtUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author anjin.ma
 * @date 2019/7/23
 * @description  自定义 Realm,实现 Shiro 安全认证
 */
@Component
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        System.out.println("token.getPrincipal:" + authenticationToken.getPrincipal());
        System.out.println("token.getCredentials:" + authenticationToken.getCredentials());
        String token = (String) authenticationToken.getCredentials();
        // 解密获得 username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null || !JwtUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }

        /* 以下数据库查询可根据实际情况，可以不必再次查询，这里我两次查询会很耗资源
         * 我这里增加两次查询是因为考虑到数据库管理员可能自行更改数据库中的用户信息
         */
        String password = userService.getPassword(username);
        if (password == null) {
            throw new AuthenticationException("该用户不存在！");
        }
        int ban = userService.checkUserBanStatus(username);
        if (ban == 1) {
            throw new AuthenticationException("该用户已被封号！");
        }
        
        String cacheToken = TokenCache.get(username);
        if (null == cacheToken) {
        	throw new AuthenticationException("token不存在或已过期，请重新登录！");
        }
        return new SimpleAuthenticationInfo(cacheToken, cacheToken, getName());
    }

    /**
     * 授权
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        String username = JwtUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        //获得该用户角色
        Set<String> roleSet = new HashSet<>();
        List<SysRole> roles = roleMapper.getRolesByUserName(username);
        roles.forEach(ele -> roleSet.add(ele.getRole()));
        
        //每个用户可以设置新的权限
        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        Set<String> permissionSet = new HashSet<>();
        permissionMapper.getPermissionsByUsername(username).forEach(ele -> permissionSet.add(ele.getPermission()));
        
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }
}