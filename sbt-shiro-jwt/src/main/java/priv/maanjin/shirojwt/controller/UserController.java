package priv.maanjin.shirojwt.controller;

import priv.maanjin.shirojwt.constant.ApplicationConstant;
import priv.maanjin.shirojwt.entity.SysUser;
import priv.maanjin.shirojwt.exception.ResourceNotFoundException;
import priv.maanjin.shirojwt.mapper.UserMapper;
import priv.maanjin.shirojwt.model.ApiReponse;
import priv.maanjin.shirojwt.util.JwtUtil;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* sys_user
* @author Anjin.Ma
* @date 2019-10-08
*/
@RestController
@RequestMapping("/b/users")
public class UserController {

    @Autowired
    private UserMapper sysUserMapper;

    /**
    * 编辑
    */
    @PutMapping("/{id}")
    @RequiresPermissions("b:user:update")
    public ApiReponse update(@PathVariable("id") Integer id, SysUser sysUser){
    	SysUser temp = sysUserMapper.selectById(id);
    	if (temp == sysUserMapper.selectById(id)) {
    		throw new ResourceNotFoundException();
    	}
    	
    	String username = JwtUtil.getUsernameFromCurrentSubject();
    	if (temp.getUsername() != username && username != ApplicationConstant.ADMIN_USERNAME) {
    		throw new AuthorizationException("无权修改其他用户的信息");
    	}
    	
    	sysUser.setId(id);
    	sysUserMapper.updateById(sysUser);
        return ApiReponse.ok();
    }

    /**
    * 删除
    */
    @PostMapping("/{id}")
    @RequiresPermissions("b:user:delete")
    public ApiReponse delete(@PathVariable("id") Integer id){
        SysUser temp = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id",id));
        if(temp == null){
        	throw new ResourceNotFoundException();
        }
        
        String username = JwtUtil.getUsernameFromCurrentSubject();
    	if (temp.getUsername() != username && username != ApplicationConstant.ADMIN_USERNAME) {
    		throw new AuthorizationException("无权删除其他用户的信息");
    	}
        
        sysUserMapper.deleteById(id);
        return ApiReponse.ok();
    }

    /**
    * 查询
    */
    @GetMapping("/{id}")
    @RequiresPermissions("b:user:view")
    public ApiReponse get(@PathVariable("id") Integer id){
        SysUser temp = sysUserMapper.selectById(id);
        if(temp == null){
        	throw new ResourceNotFoundException();
        }
        
        String username = JwtUtil.getUsernameFromCurrentSubject();
    	if (temp.getUsername() != username && username != ApplicationConstant.ADMIN_USERNAME) {
    		throw new AuthorizationException("无权查看其他用户的信息");
    	}
        
        return ApiReponse.okContent(temp);
    }

    /**
    * 分页查询
    */
    @GetMapping()
    @RequiresPermissions("b:user:list:view")
    public ApiReponse list(SysUser sysUser,
                        @RequestParam(required = false, defaultValue = "1") int cp,
                        @RequestParam(required = false, defaultValue = "10") int ps) {
    	
    	String username = JwtUtil.getUsernameFromCurrentSubject();
    	if (username != ApplicationConstant.ADMIN_USERNAME) {
    		throw new AuthorizationException("无权查看用户信息");
    	}
    	
        //分页构造器
        Page<SysUser> page = new Page<SysUser>(cp, ps);
        //条件构造器
        QueryWrapper<SysUser> queryWrapperw = new QueryWrapper<SysUser>(sysUser);
        //执行分页
        IPage<SysUser> pageList = sysUserMapper.selectPage(page, queryWrapperw);
        //返回结果
        return ApiReponse.okPage(pageList);
    }
    
    @PostMapping("/{userId}/roles/{roleId}")
    @RequiresPermissions("b:user:role:add")
    public ApiReponse addRole() {
    	// 禁止给自身增加角色
    	// 可以给下级增加角色
		return null;
    	
    }
    
    @DeleteMapping("/{userId}/roles/{roleId}")
    @RequiresPermissions("b:user:role:remove")
    public ApiReponse removeRole() {
    	
    	// 可以给自身删除角色
		return null;
    }

}
