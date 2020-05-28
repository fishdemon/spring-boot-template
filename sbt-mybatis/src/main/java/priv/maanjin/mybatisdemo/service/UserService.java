package priv.maanjin.mybatisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import priv.maanjin.mybatisdemo.event.UserRegisterEvent;

@Service
public class UserService {
	
	@Autowired
	private ApplicationContext context;

	public boolean registerUser() {
		UserRegisterEvent event = new UserRegisterEvent(this, "allen");
		context.publishEvent(event);
		System.err.println("UserRegisterEvent published!");
		return true;
	}
	
}
