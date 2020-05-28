package priv.maanjin.mybatisdemo.listener;

import org.springframework.context.ApplicationListener;

import priv.maanjin.mybatisdemo.event.UserRegisterEvent;

/**
 *  Listener, 监听事件秉并处理
 * @author Anjin.Ma
 *
 */
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {

	@Override
	public void onApplicationEvent(UserRegisterEvent event) {
		// 获取事件中的userId
		String userId = event.getUserId();
		
		// 这里只做一些 demo
		// 由于spring 的 event 是同步的，建议不耗时的操作
		// 若是耗时操作，可使用 线程异步 或 中间件MQ
		System.err.println("新用户已注册," + userId);
		System.err.println("发送邮件给 user " + userId);
	}

}
