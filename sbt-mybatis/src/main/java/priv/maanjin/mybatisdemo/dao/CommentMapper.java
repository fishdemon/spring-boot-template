package priv.maanjin.mybatisdemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import priv.maanjin.mybatisdemo.model.Comment;

public interface CommentMapper {
	
	// 多表查询
//	@Select("select A.id, A.user_id, A.content, A.create_time, C.nickname, D.url as userImageUrl from tb_comment A "
//			+ "join tb_article_comment B on B.comment_id = A.id "
//			+ "left join sys_user C on C.id = A.user_id "
//			+ "left join tb_image D on D.id = C.image_id "
//			+ "where A.enabled=1 and B.enabled=1 and C.enabled=1 and D.enabled=1 and B.article_id=#{articleId}")
//	List<Comment> selectArticleCommentByPage (@Param("articleId")int articleId);
	
	@Insert("insert into tb_comment (user_id, article_id, content) values (#{userId}, #{articleId}, #{content})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	int add(Comment comment);
	
	// 普通查询
	@Select("select * from tb_comment where enabled=1 and user_id=#{userId}")
	List<Comment> findByUserId(int userId);
	
	// 多重嵌套查询（one to one , one to many）
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
		@Result(column="user_id", property="user", one=@One(select="priv.maanjin.mybatisdemo.dao.UserMapper.findById")),
		@Result(column="id", property="replies", many=@Many(select="priv.maanjin.mybatisdemo.dao.CommentReplyMapper.findByCommentId"))
	})
	@Select("select * from tb_comment where enabled=1 and article_id=#{articleId}")
	List<Comment> findCommentsByArticleId(int articleId);
}