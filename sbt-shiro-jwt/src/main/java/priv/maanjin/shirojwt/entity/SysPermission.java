package priv.maanjin.shirojwt.entity;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*  sys_permission
* @author anjin.ma 2019-10-09
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    private Integer id;

    /**
    * 父编号，本权限可能是该父编号权限的子权限
    */
    private Integer parentId;

    /**
    * 权限字符串，menu例子：role:*，button例子：role:create，role:update，role:delete，role:view
    */
    private String permission;

    /**
    * 资源类型，[menu|button]
    */
    private String resourceType;

    /**
    * 资源路径 如：/userinfo/list
    */
    private String url;

    /**
    * 权限名称
    */
    private String name;
    
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