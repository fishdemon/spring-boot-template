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

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论（文章及其他）
 * @author Anjin.Ma
 *
 */
@Entity
@Table(name = "tb_comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false, length = 11, columnDefinition = "int(11) AUTO_INCREMENT")
    private Integer id;
	
	@Column(nullable = false, updatable = false, length = 11)
    private Integer userId;
	
	@Column(nullable = false, updatable = false, length = 11)
    private Integer articleId;
	
	@Column(nullable = false, length = 255, columnDefinition = "varchar(255) default ''")
    private String content;
	
	@Column(nullable = false, length = 1, columnDefinition = "tinyint(1) default 1")
    private Boolean enabled;

	@CreationTimestamp
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp comment '创建时间'")
    private Date createTime;   
    
	@Transient
	private SysUser user;
	
    @Transient
    private List<CommentReply> replies;
    
}