package com.fishdemon.sbt.springevent.event;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class UserRegisterEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;

	public UserRegisterEvent(Object source, String userId) {
		super(source);
		this.userId = userId;
	}

}
