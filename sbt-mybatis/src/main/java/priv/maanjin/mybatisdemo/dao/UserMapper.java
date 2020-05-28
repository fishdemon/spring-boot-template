package priv.maanjin.mybatisdemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.FetchType;

import priv.maanjin.mybatisdemo.model.SysUser;

public interface UserMapper {
	
	String TABLE_NAME = "sys_user";
	
	// 普通插入
	@Insert("insert into sys_user (username, password, email) values (#{username}, #{password}, #{email})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	int add(SysUser user);
	
	// 普通删除
	@Delete("delete from sys_user where id=#{id}")
	int del(int id);
	
	// 假删更新
	@Update("update sys_user set enabled=0 where enabled=1 and id=#{id}")
	int disable(int id);
	
	// 普通更新
	@Update("update sys_user set password=#{password} where enabled=1 and id=#{id}")
	int updatePassword(@Param("id") int id, @Param("password") String password);
	
	// 普通查询
	@Select("select * from sys_user where enabled=1 and id=#{id}")
    SysUser findById(int id);
	
    @Select("select * from sys_user where enabled=1 and username = #{username} and password=#{password}")
    SysUser findByUsernameAndPwd(@Param("username") String username, @Param("password") String password);
    
    // 模糊查询
    @Select("select * from sys_user where enabled=1 and username like concat('%', #{username}, '%')")
    List<SysUser> findLikeUsername(String username);
    
    // 选择性查询, 动态SQL
    @Select({
    	"<script>"
    	," select * from sys_user where enabled=1"
    	," <if test=\" null != id \"> and id=#{id} </if>"
    	," <if test=\" null != username \"> and username=#{username} </if>"
    	," <if test=\" null != email \"> and username=#{email} </if>"
    	," </script>"
	})
    List<SysUser> findBySelective(SysUser user);
    
    @SelectProvider(type=UserMapperProvider.class, method="findBySelective1")
    List<SysUser> findBySelective1(SysUser user);
    
    @SelectProvider(type=UserMapperProvider.class, method="findBySelective2")
    List<SysUser> findBySelective2(SysUser user);
    
    class UserMapperProvider {
    	public String findBySelective1(SysUser user) {
    		StringBuilder sb = new StringBuilder("select * from sys_user where enabled=1 ");
    		if (null != user) {
    			if (null != user.getId()) {
        			sb.append("and id=#{id}");
        		}
        		if (null != user.getUsername()) {
        			sb.append("and username=#{username}");
        		}
        		if (null != user.getEmail()) {
        			sb.append("and email=#{email}");
        		}
    		}
    		return sb.toString();
    	}
    	
    	public String findBySelective2(SysUser user) {
    		return new SQL() {{
    			SELECT("*");
    			FROM("sys_user");
    			WHERE("enabled=1");
    			if (null != user) {
    				if (null != user.getId()) {
            			WHERE("id=#{id}");
            		}
            		if (null != user.getUsername()) {
            			WHERE("username=#{username}");
            		}
            		if (null != user.getEmail()) {
            			WHERE("email=#{email}");
            		}
    			}
    		}}.toString();
    	}
    }
    
    // 嵌套查询 one To Many 
    @Results(
    	@Result(column="id", property="comments", many = @Many(fetchType=FetchType.EAGER, select="priv.maanjin.mybatisdemo.dao.CommentMapper.findByUserId"))
    )
    @Select("select * from sys_user where enabled=1 and id=#{id}")
    SysUser findUserComments(@Param("id") int id);
    
}