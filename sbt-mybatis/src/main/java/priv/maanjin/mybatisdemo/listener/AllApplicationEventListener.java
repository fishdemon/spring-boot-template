package priv.maanjin.mybatisdemo.listener;

import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.web.reactive.context.ReactiveWebServerInitializedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AllApplicationEventListener {
	
	// application context event
	@EventListener
	public void contextStartedEvent(ContextStartedEvent event) {
		System.err.println("Hello, ContextStartedEvent");
	}
	
	@EventListener
	public void contextStoppedEvent(ContextStoppedEvent event) {
		System.err.println("Hello, ContextStoppedEvent");
	}
	
	@EventListener
	public void contextClosedEvent(ContextClosedEvent event) {
		System.err.println("Hello, ContextClosedEvent");
	}
	
	@EventListener
	public void contextRefreshedEvent(ContextRefreshedEvent event) {
		System.err.println("Hello, ContextRefreshedEvent");
	}
	
	
	@EventListener
	public void applicationFailedEvent(ApplicationFailedEvent event) {
		System.err.println("Hello, ApplicationFailedEvent");
	}
	
	
	@EventListener
	public void applicationReady(ApplicationReadyEvent event) {
		System.err.println("Hello, ApplicationReadyEvent");
	}
	
	@EventListener
	public void applicationStartedEvent(ApplicationStartedEvent event) {
		System.err.println("Hello, ApplicationStartedEvent");
	}	
	
	// web server
	@EventListener
	public void reactiveWebServerInitializedEvent(ReactiveWebServerInitializedEvent event) {
		System.err.println("Hello, ReactiveWebServerInitializedEvent");
	}
	
	@EventListener
	public void servletWebServerInitializedEvent(ServletWebServerInitializedEvent event) {
		System.err.println("Hello, ServletWebServerInitializedEvent");
	}
	
	// other
	@EventListener
	public void dataSourceSchemaCreatedEvent(DataSourceSchemaCreatedEvent event) {
		System.err.println("Hello, DataSourceSchemaCreatedEvent");
	}
	
}
