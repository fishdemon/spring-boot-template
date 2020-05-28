package priv.maanjin.mybatisdemo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import priv.maanjin.mybatisdemo.dao.CommentMapper;
import priv.maanjin.mybatisdemo.dao.CommentReplyMapper;
import priv.maanjin.mybatisdemo.dao.UserMapper;
import priv.maanjin.mybatisdemo.model.Comment;
import priv.maanjin.mybatisdemo.model.CommentReply;
import priv.maanjin.mybatisdemo.model.SysUser;

@Order(2)
@Component
public class DbListener implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentReplyMapper commentReplyMapper;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		SysUser user = SysUser.builder().username("allen").password("123456").email("test@qq.com").build();
		userMapper.add(user);
		
		// comment for user
		Comment comment1 = Comment.builder().userId(user.getId()).articleId(1).content("This is comment1").build();
		commentMapper.add(comment1);
		Comment comment2 = Comment.builder().userId(user.getId()).articleId(1).content("This is comment2").build();
		commentMapper.add(comment2);
		Comment comment3 = Comment.builder().userId(user.getId()).articleId(2).content("This is comment3").build();
		commentMapper.add(comment3);
		Comment comment4 = Comment.builder().userId(user.getId()).articleId(3).content("This is comment4").build();
		commentMapper.add(comment4);
		
		// replies for comment1
		CommentReply reply1 = CommentReply.builder().userId(user.getId()).commentId(comment1.getId()).content("This is reply1").build();
		commentReplyMapper.add(reply1);
		CommentReply reply2 = CommentReply.builder().userId(user.getId()).commentId(comment1.getId()).content("This is reply2").build();
		commentReplyMapper.add(reply2);
		CommentReply reply3 = CommentReply.builder().userId(user.getId()).commentId(comment1.getId()).content("This is reply3").build();
		commentReplyMapper.add(reply3);
		
		System.err.println("database has been initialized!");
	}

}
