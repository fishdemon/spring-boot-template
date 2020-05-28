package com.fishdemon.sbt.springevent;

import com.fishdemon.sbt.springevent.event.UserRegisterEvent;
import com.fishdemon.sbt.springevent.listener.UserRegisterListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * 演示 spring 提供的事件机制
 * 包括启动过程中的各种事件及自定义事件
 *
 * 作用：
 * 1. 可以在 spring 启动的各个阶段加入一些功能（数据库初始化，外部服务初始化）
 * 2. 可以自定义事件，在特定的时刻触发一些功能
 *
 * @author Anjin.Ma
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);

        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners((ApplicationListener<ApplicationStartingEvent>) event -> {
            System.err.println("Hello, ApplicationStartingEvent");
        });

        app.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            System.err.println("Hello, ApplicationEnvironmentPreparedEvent");
        });

        app.addListeners((ApplicationListener<ApplicationContextInitializedEvent>) event -> {
            System.err.println("Hello, ApplicationContextInitializedEvent");
        });

        app.addListeners((ApplicationListener<ApplicationPreparedEvent>) event -> {
            System.err.println("Hello, ApplicationPreparedEvent");
        });

        app.addListeners((ApplicationListener<ContextStartedEvent>) event -> {
            System.err.println("Hello, ContextStartedEvent");
        });

        app.addListeners((ApplicationListener<ContextStoppedEvent>) event -> {
            System.err.println("Hello, ContextStoppedEvent");
        });

        app.addListeners((ApplicationListener<ContextClosedEvent>) event -> {
            System.err.println("Hello, ContextClosedEvent");
        });

        ConfigurableApplicationContext context = app.run(args);

        context.addApplicationListener(new UserRegisterListener());
        context.publishEvent(new UserRegisterEvent(context, "allen"));

    }

}
