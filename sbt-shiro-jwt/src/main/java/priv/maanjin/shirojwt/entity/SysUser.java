package priv.maanjin.shirojwt.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * sys_user
 * 
 * @author anjin.ma
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 用户昵称
	 */
	private String nickname;
	
	// 用户头像
	private String avatar;
	
	/**
	 * 用户邮件
	 */
	private String email;
	
	/**
	 * 用户手机号
	 */
	private String phoneNo;
	
	/**
	 * 用户状态：0: 正常状态，1：用户锁定
	 */
	private Integer state;
	
	/**
	 * 是否以被逻辑删除
	 * -1 已删除 0 正常
	 */
	@TableLogic
	private Integer deleted;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

}