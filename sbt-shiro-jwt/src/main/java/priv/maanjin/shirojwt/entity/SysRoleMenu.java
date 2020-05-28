package priv.maanjin.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRoleMenu {
	
	 /**
	    * 主键
	    */
	 private Integer id;
	 
	 private Integer roleId;
	 
	 private Integer menuId;
	
}
