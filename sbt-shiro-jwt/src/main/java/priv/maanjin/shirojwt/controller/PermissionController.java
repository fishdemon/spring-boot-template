package priv.maanjin.shirojwt.controller;

import priv.maanjin.shirojwt.entity.SysPermission;
import priv.maanjin.shirojwt.exception.ResourceNotFoundException;
import priv.maanjin.shirojwt.mapper.PermissionMapper;
import priv.maanjin.shirojwt.model.ApiReponse;
import priv.maanjin.shirojwt.model.ApiReturnUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* sys_permission
* @author anjin.ma 2019-10-09
*/
@RestController
@RequestMapping("/b/permissions")
public class PermissionController {

    @Autowired
    private PermissionMapper sysPermissionMapper;

    /**
	 * 更新
	 */
	@PutMapping("/{id}")
	@RequiresPermissions("b:permission:update")
	public ApiReponse update(@PathVariable("id") Integer id, String description) {
		SysPermission sysRoleTemp = sysPermissionMapper.selectOne(new QueryWrapper<SysPermission>().eq("id", id));
		if (sysRoleTemp == null) {
			throw new ResourceNotFoundException();
		}

		SysPermission sysPermission = new SysPermission();
		sysPermission.setId(id);
		sysPermissionMapper.updateById(sysPermission);

		return ApiReponse.ok();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/{id}")
	@RequiresPermissions("b:permission:delete")
	public ApiReponse delete(@PathVariable("id") Integer id) {
		SysPermission sysRoleTemp = sysPermissionMapper.selectOne(new QueryWrapper<SysPermission>().eq("id", id));
		if (sysRoleTemp == null) {
			throw new ResourceNotFoundException();
		}

		sysPermissionMapper.deleteById(id);

		return ApiReponse.ok();
	}

	/**
	 * 查询
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("b:permission:view")
	public Object get(@PathVariable("id") Integer id) {
		SysPermission sysPermission = sysPermissionMapper.selectOne(new QueryWrapper<SysPermission>().eq("id", id));
		if (sysPermission != null) {
			return ApiReturnUtil.success(sysPermission);
		} else {
			return ApiReturnUtil.error("没有找到该对象");
		}
	}

	/**
	 * 分页查询
	 */
	@GetMapping()
	@RequiresPermissions("b:permission:view")
	public Object list(SysPermission sysPermission, @RequestParam(required = false, defaultValue = "0") int pageNumber,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		// 分页构造器
		Page<SysPermission> page = new Page<SysPermission>(pageNumber, pageSize);
		// 条件构造器
		QueryWrapper<SysPermission> queryWrapperw = new QueryWrapper<SysPermission>(sysPermission);
		// 执行分页
		IPage<SysPermission> pageList = sysPermissionMapper.selectPage(page, queryWrapperw);
		// 返回结果
		return ApiReturnUtil.success(pageList);
	}

}
