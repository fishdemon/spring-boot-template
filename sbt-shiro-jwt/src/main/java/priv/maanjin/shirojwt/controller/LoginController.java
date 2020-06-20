package priv.maanjin.shirojwt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.maanjin.shirojwt.config.permission.TokenCache;
import priv.maanjin.shirojwt.model.ApiReponse;
import priv.maanjin.shirojwt.util.JwtUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author anjin.ma
 * @description
 */

@RestController
@Api(value = "登录操作")
public class LoginController {

    @ApiOperation(value = "用户登录", notes = "登录--不进行拦截")
    @PostMapping("/login")
    public ApiReponse login(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     HttpServletResponse response) {
    	// 包装一个用户名密码 token
    	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    	// 获取 shiro 中的 Subject
    	Subject subject = SecurityUtils.getSubject();
    	// 登录认证
    	subject.login(token);
    	String jwtToken = JwtUtil.createToken(username);
    	response.setHeader("token", jwtToken);
    	TokenCache.put(username, jwtToken);
		return ApiReponse.ok();
    }
    
    @ApiOperation(value = "用户退出登录", notes = "退出登录")
    @GetMapping("/logout")
    public ApiReponse logout() {
    	Subject subject = SecurityUtils.getSubject();
    	if (null != subject ) {
    		PrincipalCollection principals = subject.getPrincipals();
    		String p = principals.toString();
    		TokenCache.del(p);
    	}
        subject.logout();
    	return ApiReponse.ok();
    }

    @ApiOperation(value = "无权限", notes = "无权限跳转的接口")
    @GetMapping(path = "/unauthorized/{message}")
    public ApiReponse unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return ApiReponse.fail().add("info",message);
    }
    
    @ApiOperation(value = "无权限测试", notes = "visitor 信息")
    @GetMapping(path = "/getVisitor")
    public ApiReponse getVisitor()  {
        return ApiReponse.ok().add("info","成功获得 visitor 信息！");
    }

    @ApiOperation(value = "operator测试", notes = "拥有 operator, admin 角色的用户可以访问下面的页面")
    @GetMapping("/getOperator")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "operator"})
    public ApiReponse getOperator() {
        return ApiReponse.ok().add("info","成功获得信息！");
    }

    @ApiOperation(value = "admin 权限测试", notes = "拥有 admin 权限可以访问该页面")
    @GetMapping("/getAdmin")
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ApiReponse getAdmin() {
        return ApiReponse.ok().add("info","成功获得 admin 信息！");
    }
}
