package priv.maanjin.mybatisdemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import priv.maanjin.mybatisdemo.model.CommentReply;


public interface CommentReplyMapper {
	
	@Insert("insert into tb_comment_reply (user_id, comment_id, content) values (#{userId}, #{commentId}, #{content})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	int add(CommentReply reply);
	
	@Results(
		@Result(column="user_id", property="user", one=@One(select="priv.maanjin.mybatisdemo.dao.UserMapper.findById"))
	)
	@Select("select * from tb_comment_reply where enabled=1 and comment_id=#{commentId}")
	List<CommentReply> findByCommentId(int commentId);
   
}