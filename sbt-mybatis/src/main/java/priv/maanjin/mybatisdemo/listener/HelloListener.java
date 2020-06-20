package priv.maanjin.mybatisdemo.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
public class HelloListener {
	
	// Order注解要紧跟事件注解，如果放到类上面会失效
	@Order(3)
	@EventListener
	public void processHello(ApplicationReadyEvent event) {
		System.err.println("Hello, Application has been initialized!");
	}
	
}
