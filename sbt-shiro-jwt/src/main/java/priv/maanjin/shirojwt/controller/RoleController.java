package priv.maanjin.shirojwt.controller;

import priv.maanjin.shirojwt.entity.SysRole;
import priv.maanjin.shirojwt.exception.ResourceNotFoundException;
import priv.maanjin.shirojwt.mapper.RoleMapper;
import priv.maanjin.shirojwt.model.ApiReponse;
import priv.maanjin.shirojwt.model.ApiReturnUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * sys_role
 * 
 * @author anjin.ma
 * @Date 2019-10-09
 */
@RestController
@RequestMapping("/b/roles")
public class RoleController {

	@Autowired
	private RoleMapper sysRoleMapper;

	/**
	 * 更新
	 */
	@PutMapping("/{id}")
	@RequiresPermissions("b:role:update")
	public ApiReponse update(@PathVariable("id") Integer id, String description) {
		SysRole sysRoleTemp = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", id));
		if (sysRoleTemp == null) {
			throw new ResourceNotFoundException();
		}

		SysRole sysRole = new SysRole();
		sysRole.setId(id);
		sysRole.setDescription(description);
		sysRoleMapper.updateById(sysRole);

		return ApiReponse.ok();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("b:role:delete")
	public ApiReponse delete(@PathVariable("id") Integer id) {
		SysRole sysRoleTemp = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", id));
		if (sysRoleTemp == null) {
			throw new ResourceNotFoundException();
		}

		sysRoleMapper.deleteById(id);

		return ApiReponse.ok();
	}

	/**
	 * 查询
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("b:role:view")
	public Object get(@PathVariable("id") Integer id) {
		SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", id));
		if (sysRole != null) {
			return ApiReturnUtil.success(sysRole);
		} else {
			return ApiReturnUtil.error("没有找到该对象");
		}
	}

	/**
	 * 分页查询
	 */
	@GetMapping()
	@RequiresPermissions("b:role:view")
	public Object list(SysRole sysRole, @RequestParam(required = false, defaultValue = "0") int pageNumber,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		// 分页构造器
		Page<SysRole> page = new Page<SysRole>(pageNumber, pageSize);
		// 条件构造器
		QueryWrapper<SysRole> queryWrapperw = new QueryWrapper<SysRole>(sysRole);
		// 执行分页
		IPage<SysRole> pageList = sysRoleMapper.selectPage(page, queryWrapperw);
		// 返回结果
		return ApiReturnUtil.success(pageList);
	}
	
	
	public ApiReponse addPermission() {
		
		return null;
	}
	
	
	public ApiReponse removePermission() {
		
		return null;
	}

}
