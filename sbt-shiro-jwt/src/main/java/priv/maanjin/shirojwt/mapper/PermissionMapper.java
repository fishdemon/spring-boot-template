package priv.maanjin.shirojwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import priv.maanjin.shirojwt.entity.SysPermission;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
*  sys_permission
* @author anjin.ma 2019-10-09
*/
@Repository
public interface PermissionMapper extends BaseMapper<SysPermission> {
	@Select("select A.* from sys_permission A "
			+ " join sys_user B on B.username=#{username} "
			+ " join sys_user_role C on C.user_id=B.id "
			+ " join sys_role_permission D on D.role_id=C.role_id and D.permission_id=A.id")
	List<SysPermission> getPermissionsByUsername(String username);
	
	List<SysPermission> getPermissionsByRoleName(String roleName);

}
