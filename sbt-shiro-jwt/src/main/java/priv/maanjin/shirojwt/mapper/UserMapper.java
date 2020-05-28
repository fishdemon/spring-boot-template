package priv.maanjin.shirojwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import priv.maanjin.shirojwt.entity.SysUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
*  sys_user
* @author 大狼狗 2019-10-08
*/
@Repository
public interface UserMapper extends BaseMapper<SysUser> {
	
	/**
     * 获得密码
     */
	@Select("SELECT password FROM sys_user WHERE username = #{username}")
    String getPassword(String username);

    /**
     * 修改密码
     */
	@Update("update sys_user set password=#{newPassword} where username=#{username}")
    int updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);

    /**
     * 获得存在的用户
     */
	@Select("SELECT username FROM sys_user")
    List<String> getUser();

    /**
     * 封号
     */
	@Update("update sys_user set state=-1 where username=#{username}")
    void banUser(String username);

    /**
     * 检查用户状态
     */
	@Select("SELECT state FROM sys_user where username=#{username}")
    int checkUserBanStatus(String username);
	
	/**
     * 获得角色权限
     */
	@Select("SELECT password FROM sys_user WHERE username = #{username}")
    String getRole(String username);

    /**
     * 获得用户角色默认的权限
     */
    String getRolePermission(String username);

    /**
     * 获得用户的权限
     */
    String getPermission(String username);
    
}
