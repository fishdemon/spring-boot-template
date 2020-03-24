package com.fishdemon.sbt.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String getTest() {
        return "succeed to get response";
    }

    /**
     * 页面路由，当使用GET请求访问/login接口，会自动跳转到`/templates/login.html`页面
     *
     * @return login登录页面路由地址
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 页面路由，当使用GET请求访问/login接口，会自动跳转到`/templates/index.html`页面
     *
     * @return index首页面路由地址
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
