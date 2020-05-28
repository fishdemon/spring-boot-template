package priv.maanjin.shirojwt.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * sys_role
 * 
 * @author anjin.ma 2019-10-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 角色标识程序中判断使用，如 admin
	 */
	private String role;

	/**
	 * 角色描述，ui界面显示使用
	 */
	private String description;
	
	/**
	 * 是否以被逻辑删除
	 * -1 已删除 0 正常
	 */
	@TableLogic
	private Integer deleted;

	/**
	 * create_time
	 */
	private Date createTime;
	
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;	

}