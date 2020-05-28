package priv.maanjin.shirojwt.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
*  sys_role_permission
* @author anjin.ma 2019-10-09
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;

    /**
    * 角色id
    */
    private Integer roleId;

    /**
    * 权限id
    */
    private Integer permissionId;

}
