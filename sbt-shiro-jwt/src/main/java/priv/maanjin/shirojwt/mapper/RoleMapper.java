package priv.maanjin.shirojwt.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import priv.maanjin.shirojwt.entity.SysRole;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
*  sys_role
* @author anjin.ma 2019-10-09
*/
@Repository
public interface RoleMapper extends BaseMapper<SysRole> {
	
	@Select("select A.* from sys_role A "
			+ "join sys_user B on B.username=#{username} "
			+ "join sys_user_role C on C.user_id=B.id and C.role_id=A.id ")
	List<SysRole> getRolesByUserName(String username);

}
