package priv.maanjin.mybatisdemo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false, length = 11, columnDefinition = "int(11) AUTO_INCREMENT")
    private Integer id;
	
	@Column(nullable = false, length = 20, columnDefinition = "varchar(30) default ''")
    private String username;
     
    @Column(nullable = false, length = 50, columnDefinition = "varchar(50) default ''")
    private String password;
    
    @Column(nullable = false, length = 20, columnDefinition = "varchar(30) default ''")
    private String email;
    
    @Column(nullable = false, length = 20, columnDefinition = "varchar(30) default ''")
    private String phone;
    
    @Column(nullable = false, length = 20, columnDefinition = "varchar(30) default ''")
    private String qqNum;
    
    @Column(nullable = false, length = 1, columnDefinition = "tinyint(1) default 1")
    private Boolean enabled;
    
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp comment '创建时间'")
    private Date createTime;
    
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp comment '最后更新时间'")
    private Date lastUpdateTime;
    
    @Transient
    private List<Comment> comments;
   
}