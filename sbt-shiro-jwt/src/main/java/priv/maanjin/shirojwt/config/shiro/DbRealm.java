package priv.maanjin.shirojwt.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import priv.maanjin.shirojwt.service.UserService;

@Component
public class DbRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 必须重写此方法，不然会报错 匹配 Realm 和 AuthenticationToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();

		// 根据 username 从数据库中获取 user 信息 或者 password
		// 如果 有存储 salt 相关的信息也需要拿到
		String password = userService.getPassword(username);

		// 判断用户是否存在
		if (null == password) {
			// 不存在就报错，返回相关信息
			throw new AuthenticationException("该用户不存在!");
		}

		// 存在, 将数据库中的信息包装成 AuthenticationInfo
		AuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
		return info;
	}

}
