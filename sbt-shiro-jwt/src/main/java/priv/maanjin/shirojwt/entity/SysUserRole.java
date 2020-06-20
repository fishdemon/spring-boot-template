package priv.maanjin.shirojwt.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*  sys_user_role
* @author anjin.ma 2019-10-09
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 角色id
    */
    private Integer roleId;

}
